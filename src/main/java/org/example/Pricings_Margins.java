package org.example;

public class Pricings_Margins {
    double suggested, target_margin, margin;
    DrugData drugData;

    public Pricings_Margins() {

    }

    public Pricings_Margins(double suggested, double target_margin, double margin, DrugData drugData) {
        this.suggested = suggested;
        this.target_margin = target_margin;
        this.margin = margin;
        this.drugData = drugData;

    }

    public double getSuggested() {
        return suggested;
    }

    public void setSuggested(double suggested) {
        this.suggested = suggested;
    }

    public double getTarget_margin() {
        return target_margin;
    }

    public void setTarget_margin(double target_margin) {
        this.target_margin = target_margin;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public DrugData getDrugData() {
        return drugData;
    }

    public void setDrugData(DrugData drugData) {
        this.drugData = drugData;
    }
}
