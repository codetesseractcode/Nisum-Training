package com.nisum.assignment2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class LdapSecurityConfig {

    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource("ldap://localhost:8390/dc=springframework,dc=org");
    }

    @Bean
    public LdapAuthenticationProvider ldapAuthenticationProvider() {
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(
                "ou=people", "uid={0}", contextSource());

        BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource());
        bindAuthenticator.setUserSearch(userSearch);

        DefaultLdapAuthoritiesPopulator authoritiesPopulator =
                new DefaultLdapAuthoritiesPopulator(contextSource(), "ou=groups");
        authoritiesPopulator.setGroupRoleAttribute("cn");
        authoritiesPopulator.setGroupSearchFilter("member={0}");

        return new LdapAuthenticationProvider(bindAuthenticator, authoritiesPopulator);
    }

    @Bean
    @Order(2)
    public SecurityFilterChain ldapSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/ldap/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/ldap/public/**").permitAll()
                        .requestMatchers("/api/ldap/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/ldap/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(ldapAuthenticationProvider())
                .httpBasic(httpBasic -> {})
                .csrf(csrf -> csrf.disable())
                .build();
    }
}
