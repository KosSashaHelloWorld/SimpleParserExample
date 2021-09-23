package com.example.demo.entity;

import javax.persistence.*;

@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "delivery_price")
    private Double deliveryPrice;

    @Column(name = "country", length = 45)
    private String country;

    @Column(name = "seekers", length = 45)
    private String seekers;

    @Column(name = "product_href")
    private String productHref;

    public Product(String productName, Double productPrice, Double deliveryPrice, String country, String seekers, String productHref) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.deliveryPrice = deliveryPrice;
        this.country = country;
        this.seekers = seekers;
        this.productHref = productHref;
    }

    public Product() {

    }

    public String getSeekers() {
        return seekers;
    }

    public void setSeekers(String seekers) {
        this.seekers = seekers;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductHref() {
        return productHref;
    }

    public void setProductHref(String productHref) {
        this.productHref = productHref;
    }
}