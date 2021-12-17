package com.example.paytech.model;

import java.io.Serializable;

public class Payment implements Serializable {
    private String description, paymentFormat,textNumberParcel;
    private int numberParcel;
    private float valueProduct;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentFormat() {
        return paymentFormat;
    }

    public void setPaymentFormat(String paymentFormat) {
        this.paymentFormat = paymentFormat;
    }

    public int getNumberParcel() {
        return numberParcel;
    }

    public void setNumberParcel(int numberParcel) {
        this.numberParcel = numberParcel;
    }

    public float getValueProduct() {
        return valueProduct;
    }

    public void setValueProduct(float valueProduct) {
        this.valueProduct = valueProduct;
    }

    public String getTextNumberParcel() {
        return textNumberParcel;
    }

    public void setTextNumberParcel(String textNumberParcel) {
        this.textNumberParcel = textNumberParcel;
    }
}
