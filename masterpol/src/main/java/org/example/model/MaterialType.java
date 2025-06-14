package org.example.model;

public class MaterialType {
    private int id;
    private String name;
    private double materialLossPercent;

    public MaterialType(int id, String name, double loss) {
        this.id = id;
        this.name = name;
        this.materialLossPercent = loss;
    }

    public MaterialType() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaterialLossPercent(double materialLossPercent) {
        this.materialLossPercent = materialLossPercent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaterialLossPercent() {
        return materialLossPercent;
    }

    @Override
    public String toString() {
        return name;
    }
}