package com.app.maneger_and_product;


import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class ProductDb {
    @Id
    @Column(name = "pro_id")
    private int productId;
    @Column(name = "pro_name")
    private String productName;
    @Column(name = "INFORMATION")
    private String information;
    @Column(name = "price")
    private int price;
    @Column(name = "discount")
    private int discount;
    @ManyToOne
    @JoinColumn(name = "catid", nullable = false)
    private Catagroies category;
    @Column(name = "number_of")
    private int numberOf;

    @Column(name = "image")
    private String image;
    @Column(name = "section")
    private String section;
    @Column(name = "available")
    private String available;
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(int numberOf) {
        this.numberOf = numberOf;
    }

    public int getDiscount() {
        return discount;
    }

    public String getAvailable() {
        return available;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }



    public void setAvailable(String available) {
        this.available = available;
    }

    public Catagroies getCategory() {
        return category;
    }

    public void setCategory(Catagroies category) {
        this.category = category;
    }
}
