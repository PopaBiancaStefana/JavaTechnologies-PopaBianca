package com.lab2.controllers;

import com.lab2.logic.GraphProperties;
import com.lab2.logic.ImageGraphGenerator;
import com.lab2.models.Input;
import com.lab2.models.Output;
import java.io.*;
import jakarta.servlet.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.util.Arrays;

/**
 * Handles the incoming request from input.jsp
 * Processes it using the input and logic package, and forwards the result to result.jsp
 */

@WebServlet(name = "graphController", value = "/graph-controller" ,urlPatterns = {"/graph-controller"})
@MultipartConfig
public class GraphController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String content = getFileContent(request.getPart("dimacsFile"));

        // Get the properties from the request
        String[] properties = request.getParameterValues("property");

        if(properties == null){
            // **BONUS** add default property from attribute setted by LISTENER
            String defaultProperty = getServletContext().getAttribute("defaultProperty").toString();
            properties = new String[]{defaultProperty};
        }

        // **BONUS** store the selected properties in a COOKIE
        setCookie(response, "userProperties", String.join("|", properties));

        // **BONUS** verify CAPTCHA
        boolean isConnected = (boolean) request.getSession().getAttribute("isConnected");
        String userCaptcha = request.getParameter("captcha");
        if((isConnected && userCaptcha.equals("no") || (!isConnected && userCaptcha.equals("yes")))){
            forwardToError(request, response, "The answer to the CAPTCHA is wrong.");
            return;
        }

        // Process input and validate
        Input graphInput = new Input(content, Arrays.asList(properties));

        GraphProperties graphProp = new GraphProperties(graphInput);
        if(!graphProp.isValidDimacsFormat()){
            forwardToError(request, response, "Bad Format");
            return;
        }

        // Get output
        Output out = graphProp.ProcessGraph();
        request.setAttribute("output", out);

        forwardToResult(request, response);
    }

    private void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(60 * 60 * 24 * 30);  //will last 30 days
        response.addCookie(cookie);
    }
    private String getFileContent(Part filePart) throws IOException {
        try (InputStream inputStream = filePart.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private void forwardToError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("views/errorPage.jsp").forward(request, response);
    }

    private void forwardToResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/result.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get the cookie userProperties and set it as a request attribute
        String userProperties = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userProperties".equals(cookie.getName())) {
                    userProperties = cookie.getValue();
                    break;
                }
            }
        }

        if (userProperties != null) {
            request.setAttribute("userProperties", userProperties);
        }

        // Forward to the JSP page
        request.getRequestDispatcher("views/input.jsp").forward(request, response);
    }
}