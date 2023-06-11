package com.example.krillgames.Fragments;

public class CardAward {
    private String title, company, code, category, price, quantity;
    private int image;

    public CardAward(String title, String company, String code, String category, String  price, String quantity, int image) {
        this.title = title;
        this.company = company;
        this.code = code;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public int getImage() { return image; }
    public void setImage(int image) { this.image = image; }
}
