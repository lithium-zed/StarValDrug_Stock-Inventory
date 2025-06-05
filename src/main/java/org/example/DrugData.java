package org.example;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrugData{
    String vendor_name, c_p, brand_name, generic_name,unit_of_measure;
    int quantity, batch_number;
    double purchase_cost, costPunit, selling_price, suggested_price, margin;
    Date exp_date;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public DrugData(int batch_number, String vendor_name, String c_p, String brand_name,
                    String generic_name, int quantity,
                    String unit_of_measure, String expDateString,
                    double purchase_cost, double costPunit, double selling_price) {
        this.vendor_name = vendor_name;
        this.c_p = c_p;
        this.brand_name = brand_name;
        this.generic_name = generic_name;
        try{
            this.exp_date = DATE_FORMAT.parse(expDateString);
        }catch (ParseException e){
            this.exp_date = null;
        }
        this.unit_of_measure = unit_of_measure;
        this.quantity = quantity;
        this.batch_number = batch_number;
        this.purchase_cost = purchase_cost;
        this.costPunit = costPunit;
        this.selling_price = selling_price;
    }

    public DrugData(double suggested_price, double margin) {
        this.suggested_price = suggested_price;
        this.margin = margin;
    }

//    public double getSuggested_price(){
//        suggested_price = getCostPunit() *
//    }

    public String getExpirationDateAsString(){
        if(exp_date == null){
            return "";
        }
        return DATE_FORMAT.format(exp_date);
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getC_p() {
        return c_p;
    }

    public void setC_p(String c_p) {
        this.c_p = c_p;
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

    public void setExp_date(Date exp_date) {
        this.exp_date = exp_date;
    }

    public Date getExp_date() {
        return exp_date;
    }

    public String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBatch_number() {
        return batch_number;
    }

    public void setBatch_number(int batch_number) {
        this.batch_number = batch_number;
    }

    public double getPurchase_cost() {
        return purchase_cost;
    }

    public void setPurchase_cost(double purchase_cost) {
        this.purchase_cost = purchase_cost;
    }

    public double getCostPunit() {
        return costPunit;
    }

    public void setCostPunit(double costPunit) {
        this.costPunit = costPunit;
    }

    public double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }
}
