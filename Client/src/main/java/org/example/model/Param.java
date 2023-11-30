package org.example.model;

public class Param {
    private String name;
    private String paramFormula;
    private String paramCode;
    private int id;

    public Param(String name, String paramFormula, String paramCode, int id) {
        this.name = name;
        this.paramFormula = paramFormula;
        this.paramCode = paramCode;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParamFormula() {
        return paramFormula;
    }

    public void setParamFormula(String paramFormula) {
        this.paramFormula = paramFormula;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
