package com.lab2.logic;

import com.lab2.models.Input;
import com.lab2.models.Output;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class GraphProperties {
    Input graphInput;
    private Graph<Integer, DefaultEdge> graph;

    public GraphProperties(Input graphInput) {
        this.graphInput = graphInput;
    }

    public boolean isValidDimacsFormat() {
        //perform validation
        if (!graphInput.getDimacsGraph().startsWith("c ") && !graphInput.getDimacsGraph().startsWith("p ")) {
            return false;
        }

        String[] lines = graphInput.getDimacsGraph().split("\n");
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
            } else {
                return false;
            }
        }
        return true;
    }

    private Graph<Integer, DefaultEdge> parseDimacsFiletoGraph() {
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        String[] lines = graphInput.getDimacsGraph().split("\n");
        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("p")) {
                String[] parts = line.split(" ");
                int vertices = Integer.parseInt(parts[2]);

                // Add all vertices to the graph.
                for (int i = 1; i <= vertices; i++) {
                    graph.addVertex(i);
                }
            } else if (line.startsWith("e")) {
                // Process the edge line.
                String[] parts = line.split(" ");
                int source = Integer.parseInt(parts[1]);
                int target = Integer.parseInt(parts[2]);

                graph.addEdge(source, target);
            }
        }
        return graph;
    }

    private int calculateOrder() {
        return graph.vertexSet().size();
    }

    private int calculateSize() {
        return graph.edgeSet().size();
    }

    private int calculateNrConnectedComponents() {
        ConnectivityInspector<Integer, DefaultEdge> inspector = new ConnectivityInspector<>(graph);
        return inspector.connectedSets().size();
    }

    private int calculateMinDegree() {
        int minDegree = Integer.MAX_VALUE;
        for (int vertex : graph.vertexSet()) {
            int degree = graph.degreeOf(vertex);
            minDegree = Math.min(minDegree, degree);
        }
        return minDegree;
    }

    private int calculateMaxDegree() {
        int maxDegree = Integer.MIN_VALUE;
        for (int vertex : graph.vertexSet()) {
            int degree = graph.degreeOf(vertex);
            maxDegree = Math.max(maxDegree, degree);
        }
        return maxDegree;
    }

    public Output ProcessGraph() {
        this.graph = parseDimacsFiletoGraph();
        Output out = new Output();

        if (graphInput.getSelectedProperties().contains("order")) {
            out.setOrder(calculateOrder());
        }
        if (graphInput.getSelectedProperties().contains("size")) {
            out.setSize(calculateSize());
        }
        if (graphInput.getSelectedProperties().contains("connectedComponents")) {
            out.setConnectedComponents(calculateNrConnectedComponents());
        }
        if (graphInput.getSelectedProperties().contains("minDegree")) {
            out.setMinDegree(calculateMinDegree());
        }
        if (graphInput.getSelectedProperties().contains("maxDegree")) {
            out.setMaxDegree(calculateMaxDegree());
        }
        return out;
    }
}
