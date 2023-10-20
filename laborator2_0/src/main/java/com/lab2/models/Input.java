package com.lab2.models;

import java.util.List;

public class Input {
    private String dimacsGraph;
    private List<String> selectedProperties;

    public Input(String dimacsGraph, List<String> selectedProperties) {
        this.dimacsGraph = dimacsGraph;
        this.selectedProperties = selectedProperties;
    }

    public String getDimacsGraph() {
        return dimacsGraph;
    }

    public void setDimacsGraph(String dimacsGraph) {
        this.dimacsGraph = dimacsGraph;
    }

    public List<String> getSelectedProperties() {
        return selectedProperties;
    }

    public void setSelectedProperties(List<String> selectedProperties) {
        this.selectedProperties = selectedProperties;
    }
}
