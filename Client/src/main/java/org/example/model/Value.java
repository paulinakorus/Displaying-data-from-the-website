package org.example.model;

public class Value {
    private String date;
    private double value;

    public Value(String date, double value) {
        this.date = date;
        this.value = value;
    }

    public Value(){
        this(null, 0.0);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
