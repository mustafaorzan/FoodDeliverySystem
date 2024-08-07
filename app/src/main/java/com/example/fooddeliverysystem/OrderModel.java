package com.example.fooddeliverysystem;

public class OrderModel {//Bu düzenlenebilir
    private String restaurantId, restaurantName, customerId, customerName, customerLastName, customerAddress, food, date;
    private int quantity, price;
    //RestoranId - RestoranAdı - MüşteriId - MüşteriAd - MüşteriSoyad - MüşteriAdres - Yemek - Adet - Fiyat(Toplam Fiyat) - Tam tarih şeklinde


    public OrderModel(String restaurantId, String restaurantName, String customerId, String customerName, String customerLastName, String customerAddress, String food, int quantity, int price, String date) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.customerAddress = customerAddress;
        this.food = food;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OrderModel(){}

}
