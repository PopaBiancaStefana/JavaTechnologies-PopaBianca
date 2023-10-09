package com.homework;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.logging.Logger;

@WebServlet(name = "AdjacencyMatrix", value = "adjacency-matrix", urlPatterns = {"/adjacency-matrix"})
public class AdjacencyMatrix extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(AdjacencyMatrix.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logRequestInfo(request);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //Servlet invocation using HTML form with post mehtod
        out.println("<html><body>");
        out.println("<form method=\"post\" action=\"adjacency-matrix\">" +
                "Number of vertices:" +
                "<input type=\"number\" name=\"numVertices\" size=\"3\" value=\"\">" +
                "</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logRequestInfo(request);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int nrVertices;

        try {
            nrVertices = Integer.parseInt(request.getParameter("numVertices"));
            if (nrVertices <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h2>Please enter a positive number greater than 1:</h2>");
            out.println("</body></html>");
            return;
        }

        int[][] adjacencyMatrix = generateRandomTree(nrVertices);

        out.println(returnHTMLResponse(nrVertices, adjacencyMatrix));
//        out.println(returnTEXTResponse(nrVertices, adjacencyMatrix));
    }

    private void logRequestInfo(HttpServletRequest request) {
        LOGGER.info("HTTP Method: " + request.getMethod());
        LOGGER.info("Client IP: " + request.getRemoteAddr());
        LOGGER.info("User-Agent: " + request.getHeader("User-Agent"));
        LOGGER.info("Client Language(s): " + request.getHeader("Accept-Language"));
        LOGGER.info("numVertices Parameter: " + request.getParameter("numVertices"));
    }

    private int[][] generateRandomTree(int n) {
        int[][] adjacencyMatrix = new int[n][n];
        Random rand = new Random();
        for (int i = 1; i < n; i++) {
            int connectTo = rand.nextInt(i); //no cycle is formed
            adjacencyMatrix[i][connectTo] = 1;
            adjacencyMatrix[connectTo][i] = 1;
        }
        return adjacencyMatrix;
    }

    private String returnHTMLResponse(int nrVertices, int[][] adjacencyMatrix) {
        StringBuilder html = new StringBuilder("<h2>Adjacency Matrix for a random tree:</h2>"
                + "<table border='1'>");

        for (int i = 0; i < nrVertices; i++) {
            html.append("<tr>");
            for (int j = 0; j < nrVertices; j++) {
                html.append("<td>").append(adjacencyMatrix[i][j]).append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table></body></html>");
        return html.toString();
    }

    String returnTEXTResponse(int nrVertices, int[][] adjacencyMatrix) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < nrVertices; i++) {
            for (int j = 0; j < nrVertices; j++) {
                text.append(adjacencyMatrix[i][j]);
                if (j < nrVertices - 1) text.append(",");
            }
            text.append('\n');
        }
        return text.toString();
    }

    public void destroy() {
    }
}