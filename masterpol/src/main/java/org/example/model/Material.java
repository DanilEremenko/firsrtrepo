package org.example.model;

public class Material {
    private int id;
    private String name;
    private MaterialType type;
    private double price;
    private double quantity;
    private double minQuantity;
    private double packSize;
    private String unit;

    public Material() {
    }

    public Material(int id, String name, MaterialType type, double price,
                    double quantity, double minQuantity, double packSize, String unit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.packSize = packSize;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public double getPackSize() {
        return packSize;
    }

    public void setPackSize(double packSize) {
        this.packSize = packSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}