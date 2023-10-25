package com.lab2.logic;

import org.graph4j.*;
import org.graph4j.generate.GraphGenerator;

public class ImageGraphGenerator {
    Graph GeneratedGraph;

    public ImageGraphGenerator(){
        GeneratedGraph = GenerateRandomGraph();
    }
    public Graph GenerateRandomGraph(){
        return GraphGenerator.randomGnp(15,0.7);
    }

}
