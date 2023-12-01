package org.example.model;

public class Sensor {
    private String key;
    private Value[] values;

    public Sensor(String key, Value[] values){
        this.key = key;
        this.values = values;
    }

    public Sensor(){
        this(null, null);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Value[] getValues() {
        return values;
    }

    public void setValues(Value[] values) {
        this.values = values;
    }
}
