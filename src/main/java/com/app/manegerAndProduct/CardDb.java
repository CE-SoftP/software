package com.app.manegerAndProduct;

import com.app.customer.CustomerDb;
import jakarta.persistence.*;

@Entity
@Table(name = "card")
public class CardDb {
    @Id
   //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARDID")
    private int cardId;

    @ManyToOne
    @JoinColumn(name = "PRODUCTID", nullable = false)
    private ProductDb productDb;


    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private CustomerDb customerDb;

    @Column(name = "TOTALPRICE")
    private int totalPrice;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public ProductDb getProductDb() {
        return productDb;
    }

    public void setProductDb(ProductDb productDb) {
        this.productDb = productDb;
    }

    public CustomerDb getCustomerDb() {
        return customerDb;
    }

    public void setCustomerDb(CustomerDb customerDb) {
        this.customerDb = customerDb;
    }


    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
