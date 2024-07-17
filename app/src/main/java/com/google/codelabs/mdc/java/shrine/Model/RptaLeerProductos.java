package com.google.codelabs.mdc.java.shrine.Model;

import java.util.List;

public class RptaLeerProductos {
    private int code;
    private List<Product> data;
    private String message;

    public int getCode() {
        return code;
    }

    public List<Product> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
