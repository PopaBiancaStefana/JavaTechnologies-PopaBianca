package com.lab2.controllers;

import com.lab2.logic.GraphProperties;
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
import java.util.Scanner;

/**
 * Handles the incoming request from input.jsp
 * Processes it using the input and logic package, and forwards the result to result.jsp
 */

@WebServlet(name = "graphController", value = "/graph-controller")
@MultipartConfig
public class GraphController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Get file
        Part dimacsFile = request.getPart("dimacsFile");
        InputStream inputStream = dimacsFile.getInputStream();
        String content = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        inputStream.close();

        // Set input
        String[] properties = request.getParameterValues("property");
        Input graphInput = new Input(content, Arrays.asList(properties));

        // Validate dimacs format and get output
        GraphProperties graphProp = new GraphProperties(graphInput);
        if(!graphProp.isValidDimacsFormat()){
            request.setAttribute("errorMessage","Bad Format");
            request.getRequestDispatcher("views/errorPage.jsp").forward(request,response);
            return;
        }
        Output out = graphProp.ProcessGraph();
        request.setAttribute("output", out);

        // Forward to result.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/result.jsp");
        dispatcher.forward(request, response);
    }
}