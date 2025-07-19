package com.nisum.library.library_api.dto;

import java.util.List;

/**
 * Data Transfer Object for JWT authentication responses.
 * Contains token and user information for successful authentication.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;

    /**
     * Default constructor.
     */
    public JwtResponse() {}

    /**
     * Constructor with parameters.
     *
     * @param accessToken the JWT access token
     * @param id the user ID
     * @param username the username
     * @param email the email
     * @param firstName the first name
     * @param lastName the last name
     * @param roles the user roles
     */
    public JwtResponse(String accessToken, Long id, String username, String email,
                      String firstName, String lastName, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
