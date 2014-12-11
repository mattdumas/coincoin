package fr.coincoin.domain;

import java.math.BigDecimal;

public class Ad {

    private String name;

    private String url;

    private String price;

    private String imageUrl;

    private Ad() {

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class Builder {

        private Ad ad = new Ad();

        public Builder withName(String name) {
            ad.name = name;
            return this;
        }


        public Builder withUrl(String url) {
            ad.url = url;
            return this;
        }


        public Builder withPrice(String price) {
            ad.price = price;
            return this;
        }


        public Builder withImageUrl(String imageUrl) {
            ad.imageUrl = imageUrl;
            return this;
        }

        public Ad build() {
            return ad;
        }
    }
}
