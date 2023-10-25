package com.lab2.models;

public class Output {
    private Integer order;
    private Long size;
    private Integer connectedComponents;
    private Integer minDegree;
    private Integer maxDegree;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getConnectedComponents() {
        return connectedComponents;
    }

    public void setConnectedComponents(Integer connectedComponents) {
        this.connectedComponents = connectedComponents;
    }

    public Integer getMinDegree() {
        return minDegree;
    }

    public void setMinDegree(Integer minDegree) {
        this.minDegree = minDegree;
    }

    public Integer getMaxDegree() {
        return maxDegree;
    }

    public void setMaxDegree(Integer maxDegree) {
        this.maxDegree = maxDegree;
    }
}
