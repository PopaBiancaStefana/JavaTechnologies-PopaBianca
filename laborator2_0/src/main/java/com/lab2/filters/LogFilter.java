package com.lab2.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Date;

public class LogFilter implements Filter{
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        // Find the IP of the request
        String ipAddress = request.getRemoteAddr();

        try {
            System.out.println("Filter found IP: " + ipAddress + ", Request logged at: " + new Date());
            chain.doFilter(req, res);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
