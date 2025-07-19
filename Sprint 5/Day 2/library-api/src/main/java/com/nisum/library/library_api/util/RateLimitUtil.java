package com.nisum.library.library_api.util;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rate limiting utility using Token Bucket algorithm.
 * Implements API rate limiting to prevent abuse and ensure fair usage.
 *
 * @author Nisum Library Team
 * @version 1.0
 * @since 2025-07-19
 */
@Component
public class RateLimitUtil {

    private final long requestsPerMinute;
    private final long burstCapacity;
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public RateLimitUtil(@Value("${app.rate-limit.requests-per-minute:60}") long requestsPerMinute,
                        @Value("${app.rate-limit.burst-capacity:100}") long burstCapacity) {
        this.requestsPerMinute = requestsPerMinute;
        this.burstCapacity = burstCapacity;
    }

    /**
     * Checks rate limit for the given request.
     *
     * @param request the HTTP request
     * @throws ResponseStatusException if rate limit is exceeded
     */
    public void checkRateLimit(HttpServletRequest request) {
        String clientId = getClientIdentifier(request);
        Bucket bucket = buckets.computeIfAbsent(clientId, this::createBucket);

        if (!bucket.tryConsume(1)) {
            throw new ResponseStatusException(
                HttpStatus.TOO_MANY_REQUESTS,
                "Rate limit exceeded. Please try again later."
            );
        }
    }

    /**
     * Creates a new bucket for rate limiting.
     *
     * @param clientId the client identifier
     * @return configured bucket
     */
    private Bucket createBucket(String clientId) {
        Bandwidth limit = Bandwidth.classic(burstCapacity,
            Refill.intervally(requestsPerMinute, Duration.ofMinutes(1)));
        return Bucket4j.builder()
            .addLimit(limit)
            .build();
    }

    /**
     * Gets client identifier for rate limiting.
     * Uses IP address as the primary identifier.
     *
     * @param request the HTTP request
     * @return client identifier
     */
    private String getClientIdentifier(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}
