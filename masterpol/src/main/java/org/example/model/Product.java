package org.example.model;

public class Product {
    private int id;
    private ProductType type;
    private String name;
    private String article;
    private double minPartnerPrice;

    public Product(int id, ProductType type, String name, String article, double minPartnerPrice) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.article = article;
        this.minPartnerPrice = minPartnerPrice;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public double getMinPartnerPrice() {
        return minPartnerPrice;
    }

    public void setMinPartnerPrice(double minPartnerPrice) {
        this.minPartnerPrice = minPartnerPrice;
    }
}
