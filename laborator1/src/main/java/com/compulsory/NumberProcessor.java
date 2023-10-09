package com.compulsory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "NumberProcessor", value = "number-processor", urlPatterns = {"/number-processor"})
public class NumberProcessor extends HttpServlet {

    public void init() {
        //make init
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String number;

        try {
            number = request.getParameter("number");
            if (!isNumber(number)) throw new NumberFormatException();

        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h2>Please enter a valid number.</h2>");
            out.println("</body></html>");
            return;
        }

        out.println("<html><body>");
        out.println("<ol>");
        for (char c : number.toCharArray()) {
            out.println("<li>" + c + "</li>");
        }
        out.println("</ol>");
        out.println("</body></html>");
    }

    public boolean isNumber(String number) {
        if (number == null || number.isEmpty())
            return false;

        if (number.charAt(0) == '0')
            return false;

        for (int i = 0; i < number.length(); i++)
            if (!Character.isDigit(number.charAt(i)))
                return false;

        return true;
    }

    public void destroy() {
    }
}