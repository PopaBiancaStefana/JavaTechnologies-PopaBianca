package com.lab2.logic;

import com.lab2.models.Input;
import com.lab2.models.Output;
import org.graph4j.*;
import org.graph4j.alg.connectivity.ConnectivityAlgorithm;
import org.graph4j.measures.GraphMeasures;

public class GraphProperties {
    Input graphInput;
    private Graph graph;
    public GraphProperties(Input graphInput) {
        this.graphInput = graphInput;
    }

    public boolean isValidDimacsFormat() {
        //perform validation

        String inputGraph = graphInput.getDimacsGraph();
        if (inputGraph == null){
            return false;
        }

        if(!inputGraph.startsWith("p") && !inputGraph.startsWith("c")){
            return false;
        }

        String[] lines = inputGraph.split("\n");
        boolean foundProblemLine = false;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("p")) {
                if (foundProblemLine){
                    return false;
                }
                foundProblemLine = true;

                if (!line.matches("p edge \\d+ \\d+")) {
                    return false;
                }
            } else if (line.startsWith("e")) {
                if (!line.matches("e \\d+ \\d+"))
                    return false;
            } else if(!line.startsWith("c")) {
                return false;
            }
        }
        return true;
    }

    private Graph parseDimacsFiletoGraph() {

        String[] lines = graphInput.getDimacsGraph().split("\n");
        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("p")) {
                String[] parts = line.split(" ");
                int vertices = Integer.parseInt(parts[2]);
                graph = GraphBuilder.numVertices(vertices).buildGraph();

            } else if (line.startsWith("e")) {
                // Process the edge line.
                String[] parts = line.split(" ");
                int source = Integer.parseInt(parts[1]) - 1;
                int target = Integer.parseInt(parts[2]) - 1;

                graph.addEdge(source,target);
            }
        }
        return graph;
    }

    private int calculateNrConnectedComponents() {
      var connect = new ConnectivityAlgorithm(graph);
      return connect.getConnectedSets().size();
    }
    public Output ProcessGraph() {
        graph = parseDimacsFiletoGraph();
        Output out = new Output();

        if (graphInput.getSelectedProperties().contains("order")) {
            out.setOrder(graph.numVertices());
        }
        if (graphInput.getSelectedProperties().contains("size")) {
            out.setSize(graph.numEdges());
        }
        if (graphInput.getSelectedProperties().contains("connectedComponents")) {
            out.setConnectedComponents(calculateNrConnectedComponents());
        }
        if (graphInput.getSelectedProperties().contains("minDegree")) {
            out.setMinDegree(GraphMeasures.minDegree(graph));
        }
        if (graphInput.getSelectedProperties().contains("maxDegree")) {
            out.setMaxDegree(GraphMeasures.maxDegree(graph));
        }
        return out;
    }
}
