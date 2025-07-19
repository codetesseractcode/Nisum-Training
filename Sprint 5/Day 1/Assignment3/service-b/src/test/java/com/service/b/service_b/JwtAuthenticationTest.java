package com.service.b.service_b;

import com.service.b.service_b.util.JwtTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
public class JwtAuthenticationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtTestUtil jwtTestUtil;

    private MockMvc mockMvc;

    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testAccessProtectedEndpointWithValidRoleService() throws Exception {
        setup();
        
        // Generate JWT token with ROLE_SERVICE
        String token = jwtTestUtil.generateToken("testuser", List.of("ROLE_SERVICE"));
        
        mockMvc.perform(get("/api/protected-data")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Access granted"))
                .andExpect(jsonPath("$.data").value("Here is your secure business logic result."));
    }

    @Test
    public void testAccessProtectedEndpointWithInvalidRole() throws Exception {
        setup();
        
        // Generate JWT token with wrong role
        String token = jwtTestUtil.generateToken("testuser", List.of("ROLE_USER"));
        
        mockMvc.perform(get("/api/protected-data")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAccessProtectedEndpointWithoutToken() throws Exception {
        setup();
        
        mockMvc.perform(get("/api/protected-data"))
                .andExpect(status().isForbidden()); // Fixed typo: andExpect instead of andExpected
    }
}
