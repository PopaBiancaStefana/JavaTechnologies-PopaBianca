package com.lab2.logic;

import org.graph4j.*;
import org.graph4j.alg.connectivity.ConnectivityAlgorithm;
import org.graph4j.generate.GraphGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Point;

public class ImageGraphGenerator {
    Graph generatedGraph;

    public ImageGraphGenerator(){
       GenerateRandomGraph();
    }
    public void GenerateRandomGraph(){
        //generate a random number from 9 to 15
        int vertexes = (int)(Math.random() * 6) + 9;
        generatedGraph = GraphGenerator.randomGnp(vertexes,0.5);
        System.out.println("Is connected from Graph Generator : " + isConnected());
    }

    public boolean isConnected(){
        var connect = new ConnectivityAlgorithm(generatedGraph);
        return connect.isConnected();
    }

    public BufferedImage getGraphImage(){
        BufferedImage bufferedImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 200, 200);
        graphics.setColor(Color.BLACK);

        // Draw the vertexes
        ArrayList<Point> coordinates = getCircularLayoutCoordinates(generatedGraph.numVertices(), bufferedImage);
        for( Point p : coordinates){
            graphics.fillOval(p.x - 5, p.y - 5, 10, 10);
            graphics.drawString(Integer.toString(coordinates.indexOf(p)), p.x - 5, p.y - 5);
        }

        // Draw the edges
        for (var it = generatedGraph.edgeIterator(); it.hasNext();) {
            int source = it.next().source();
            int target = it.next().target();
            graphics.drawLine(coordinates.get(source).x, coordinates.get(source).y, coordinates.get(target).x, coordinates.get(target).y);
        }

        graphics.dispose();
        return bufferedImage;
    }

    private ArrayList<Point> getCircularLayoutCoordinates(int numberOfVertices, BufferedImage bufferedImage) {
        ArrayList<Point> coordinates = new ArrayList<>();

        int centerX = bufferedImage.getWidth() / 2;
        int centerY = bufferedImage.getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 15;

        double angleBetweenVertices = 2 * Math.PI / numberOfVertices;
        for (int i = 0; i < numberOfVertices; i++) {
            int x = centerX + (int) (radius * Math.cos(i * angleBetweenVertices));
            int y = centerY + (int) (radius * Math.sin(i * angleBetweenVertices));
            coordinates.add(new Point(x, y));
        }
        return coordinates;
    }
}
