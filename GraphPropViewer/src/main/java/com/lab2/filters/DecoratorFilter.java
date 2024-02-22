package com.lab2.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class DecoratorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
        //Send the decorated object as a replacement for the original response
        chain.doFilter(request, wrapper);

        //Get the dynamically generated content from the decorator

        String prelude = "<!-- This is the prelude -->";
        String coda = "<!-- This is the coda -->";
        String content = prelude + wrapper.getCapturedContent() + coda;

        //Send the modified content using the original response
        response.setContentLength(content.length());
        PrintWriter out = response.getWriter();
        out.write(content);
        out.flush();
    }
}
