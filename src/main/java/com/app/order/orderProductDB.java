package com.app.order;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_PRODUCT")
public class orderProductDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "ORDERID")
    private int orderId;

    @Column(name = "PRODUCTID")
    private int productId;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "PRODUCTNAME")
    private String productName;

    @Column(name = "PRODUCTPRICE")
    private int productPrice;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
