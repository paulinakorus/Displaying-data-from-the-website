package org.example.model;

public class Param {
    private String paramName;
    private String paramFormula;
    private String paramCode;
    private int idParam;

    public Param(String paramName, String paramFormula, String paramCode, int iidParamd) {
        this.paramName = paramName;
        this.paramFormula = paramFormula;
        this.paramCode = paramCode;
        this.idParam = idParam;
    }

    public Param(){
        this(null, null, null, 0);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
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

    public int getIdParam() {
        return idParam;
    }

    public void setIdParam(int idParam) {
        this.idParam = idParam;
    }
}
