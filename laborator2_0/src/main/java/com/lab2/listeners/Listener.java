package com.lab2.listeners;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener()
public class Listener implements ServletContextListener {

    /* Application Startup Event */
    public void contextInitialized(ServletContextEvent ce) {
        ServletContext context = ce.getServletContext();

        // Read the context init parameter
        String defaultProperty = context.getInitParameter("defaultProperty");

        // Store it in the application scope
        context.setAttribute("defaultProperty", defaultProperty);
    }
    public void contextDestroyed(ServletContextEvent ce) {}

}
