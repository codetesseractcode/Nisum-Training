package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            String url = req.getRequestURL().toString();
            System.out.println("Request URL: " + url);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}