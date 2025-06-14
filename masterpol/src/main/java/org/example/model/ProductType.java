package org.example.model;

public class ProductType {
    private int id;
    private String name;
    private double coefficient;

    public ProductType(int id, String name, double coeff) {
        this.id = id;
        this.name = name;
        this.coefficient = coeff;
    }

    public ProductType() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}