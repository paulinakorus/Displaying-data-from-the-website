package org.example.model;

public class IndeksInfo {
    private String calcDate;
    private IndeksLevel indeksLevel;
    private String sourceDataDate;

    public IndeksInfo(String calcDate, IndeksLevel indeksLevel, String sourceDataDate){
        this.indeksLevel = indeksLevel;
        this.calcDate = calcDate;
        this.sourceDataDate = sourceDataDate;
    }

    public IndeksInfo(){
        this(null, null, null);
    }

    public String getCalcDate() {
        return calcDate;
    }

    public void setCalcDate(String calcDate) {
        this.calcDate = calcDate;
    }

    public IndeksLevel getIndeksLevel() {
        return indeksLevel;
    }

    public void setIndeksLevel(IndeksLevel indeksLevel) {
        this.indeksLevel = indeksLevel;
    }

    public String getSourceDataDate() {
        return sourceDataDate;
    }

    public void setSourceDataDate(String sourceDataDate) {
        this.sourceDataDate = sourceDataDate;
    }
}
