package com.tenpercent.pojo;

public class Orders {
    private String numberOrder;
    private String dateOrder;
    private String stautsOrder;
    private String totalOrder;
    private String qunitityOrder;
    private Products producte;

    public Orders(String nuMberOrder, String dateOrder, String stautsOrder, String totalOrder, String qunitityOrder, Products producte) {
        this.numberOrder = nuMberOrder;
        this.dateOrder = dateOrder;
        this.stautsOrder = stautsOrder;
        this.totalOrder = totalOrder;
        this.qunitityOrder = qunitityOrder;
        this.producte = producte;
    }

    public Orders() {
    }

    public String getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(String numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getQunitityOrder() {
        return qunitityOrder;
    }

    public void setQunitityOrder(String qunitityOrder) {
        this.qunitityOrder = qunitityOrder;
    }

    public String getNuMberOrder() {
        return numberOrder;
    }

    public void setNuMberOrder(String nuMberOrder) {
        this.numberOrder = nuMberOrder;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getStautsOrder() {
        return stautsOrder;
    }

    public void setStautsOrder(String stautsOrder) {
        this.stautsOrder = stautsOrder;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Products getProducte() {
        return producte;
    }

    public void setProducte(Products producte) {
        this.producte = producte;
    }
}
