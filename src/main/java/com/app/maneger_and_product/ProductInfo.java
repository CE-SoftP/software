package com.app.maneger_and_product;

public class ProductInfo {
        private int proId;

        private int userRating;

        private String proName;
        private String info;
        private int proPrice;

        private int numberOfPro;

        private String proImage;
        private String proSection;

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

        public int getNumberOfPro() {
            return numberOfPro;
        }

    public void setNumberOfPro(int num) {
        numberOfPro =num;
    }

        public String getProImage() {
            return proImage;
        }

        public void setProImage(String proImage) {
            this.proImage = proImage;
        }

        public String getProSection() {
            return proSection;
        }

        public void setProSection(String proSection) {
            this.proSection = proSection;
        }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
}
