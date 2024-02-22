package com.lab2.controllers;

import com.lab2.logic.ImageGraphGenerator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Controller for image generation for Captcha
 */

@WebServlet(name = "imageController", value = "/image-controller" ,urlPatterns = {"/image-controller"})
public class ImageController extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");

        // Generate random graph and set diameter as attribute
        ImageGraphGenerator imageGen = new ImageGraphGenerator();
        boolean isConnected = imageGen.isConnected();
        request.getSession().setAttribute("isConnected", isConnected);

        // Generate image from graph
        BufferedImage image = imageGen.getGraphImage();

        // Write image to response
        ImageIO.write(image, "png", response.getOutputStream());
    }

}
