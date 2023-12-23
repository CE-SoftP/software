package com.app.maneger_and_product;

public class ProductInfo {
        private int productId;

        private String productName;
        private String information;
        private int price;

        private int numberOf;

        private String image;
        private String section;

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

    public void setNumberOf(int num) {
        numberOf=num;
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



}
