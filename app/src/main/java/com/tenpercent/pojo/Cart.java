package com.tenpercent.pojo;

public class Cart {
    private String  id;
    private String qauntity;

    public Cart(String id, String qauntity) {
        this.id = id;
        this.qauntity = qauntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQauntity() {
        return qauntity;
    }

    public void setQauntity(String qauntity) {
        this.qauntity = qauntity;
    }
}
