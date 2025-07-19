package com.nisum.library.library_api.controller;

import com.nisum.library.library_api.dto.JwtResponse;
import com.nisum.library.library_api.dto.LoginRequest;
import com.nisum.library.library_api.dto.SignupRequest;
import com.nisum.library.library_api.model.User;
import com.nisum.library.library_api.security.JwtTokenUtil;
import com.nisum.library.library_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST Controller for authentication operations.
 * Implements secure authentication with JWT tokens and proper validation.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,
                         UserService userService,
                         JwtTokenUtil jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Authenticates user and returns JWT token.
     * Implements secure login with proper password verification.
     *
     * @param loginRequest the login credentials
     * @return JWT response with user details and token
     */
    @PostMapping("/signin")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Authenticate user credentials
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                roles));
    }

    /**
     * Registers a new user account.
     * Implements secure registration with password encryption and validation.
     *
     * @param signUpRequest the registration details
     * @return success message
     */
    @PostMapping("/signup")
    @Operation(summary = "User registration", description = "Register a new user account")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        User user = userService.registerUser(signUpRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        response.put("userId", user.getId());
        response.put("username", user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Validates JWT token.
     *
     * @param token the JWT token to validate
     * @return validation result
     */
    @PostMapping("/validate")
    @Operation(summary = "Validate JWT token", description = "Validate the provided JWT token")
    public ResponseEntity<?> validateToken(@RequestParam String token) {

        boolean isValid = jwtUtils.validateJwtToken(token);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);

        if (isValid) {
            String username = jwtUtils.getUsernameFromJwtToken(token);
            response.put("username", username);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Refreshes JWT token.
     *
     * @param token the current JWT token
     * @return new JWT token
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token", description = "Generate a new JWT token")
    public ResponseEntity<?> refreshToken(@RequestParam String token) {

        if (jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUsernameFromJwtToken(token);
            String newToken = jwtUtils.generateTokenFromUsername(username);

            Map<String, String> response = new HashMap<>();
            response.put("token", newToken);
            response.put("type", "Bearer");

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest()
                .body(Map.of("error", "Invalid token"));
    }
}
