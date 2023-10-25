package com.lab2.filters;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Decorator Design Pattern: Attach additional
 * responsibilities to an object dynamically, without
 * altering its structure (class signature)
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
    private final StringWriter output;
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new StringWriter();
    }

    @Override
    public PrintWriter getWriter() {
        // Hide the original writer
        return new PrintWriter(output);
    }
    public String getCapturedContent() {
        return output.toString();
    }
}
