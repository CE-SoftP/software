package com.app.maneger_and_product;


import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class ProductDb {
    @Id
    @Column(name = "pro_id")
    private int proId;
    @Column(name = "pro_name")
    private String proName;
    @Column(name = "INFORMATION")
    private String info;
    @Column(name = "price")
    private int proPrice;
    @Column(name = "discount")
    private int productDiscount;
    @ManyToOne
    @JoinColumn(name = "catid", nullable = false)
    private Catagroies categories;
    @Column(name = "number_of")
    private int numberOfProducts;

    @Column(name = "image")
    private String productImage;
    @Column(name = "section")
    private String productSection;
    @Column(name = "available")
    private String productIsAvailable;
    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getProPrice() {
        return proPrice;
    }

    public void setProPrice(int proPrice) {
        this.proPrice = proPrice;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public int getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductSection() {
        return productSection;
    }

    public void setProductSection(String productSection) {
        this.productSection = productSection;
    }

    public String getProductIsAvailable() {
        return productIsAvailable;
    }

    public void setProductIsAvailable(String productIsAvailable) {
        this.productIsAvailable = productIsAvailable;
    }

    public Catagroies getCategories() {
        return categories;
    }

    public void setCategories(Catagroies categories) {
        this.categories = categories;
    }
}
