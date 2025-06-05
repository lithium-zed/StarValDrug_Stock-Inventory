package org.example;

public class CheckoutData {
    int batch_number;
    String brand_name, generic_name;
    int quantity;
    double price;

    public CheckoutData(int batch_number, String brand_name, String generic_name, int quantity, double price) {
        this.batch_number = batch_number;
        this.brand_name = brand_name;
        this.generic_name = generic_name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getBatch_number() {
        return batch_number;
    }

    public void setBatch_number(int batch_number) {
        this.batch_number = batch_number;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getGeneric_name() {
        return generic_name;
    }

    public void setGeneric_name(String generic_name) {
        this.generic_name = generic_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
