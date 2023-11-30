package org.example.model;

public class IndeksAir {
    private int stationId;
    private IndeksInfo station;
    private IndeksInfo so2;
    private IndeksInfo no2;
    private IndeksInfo pm10;
    private IndeksInfo o3;
    private Boolean stIndexStatus;
    private String stIndexCrParam;

    public IndeksAir(int stationId, IndeksInfo station, IndeksInfo so2, IndeksInfo no2, IndeksInfo pm10, IndeksInfo o3, Boolean stIndexStatus, String stIndexCrParam){
        this.stationId = stationId;
        this.station = station;
        this.so2 = so2;
        this.no2 = no2;
        this.pm10 = pm10;
        this.o3 = o3;
        this.stIndexStatus = stIndexStatus;
        this.stIndexCrParam = stIndexCrParam;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public IndeksInfo getStation() {
        return station;
    }

    public void setStation(IndeksInfo station) {
        this.station = station;
    }

    public IndeksInfo getSo2() {
        return so2;
    }

    public void setSo2(IndeksInfo so2) {
        this.so2 = so2;
    }

    public IndeksInfo getNo2() {
        return no2;
    }

    public void setNo2(IndeksInfo no2) {
        this.no2 = no2;
    }

    public IndeksInfo getPm10() {
        return pm10;
    }

    public void setPm10(IndeksInfo pm10) {
        this.pm10 = pm10;
    }

    public IndeksInfo getO3() {
        return o3;
    }

    public void setO3(IndeksInfo o3) {
        this.o3 = o3;
    }

    public Boolean getStIndexStatus() {
        return stIndexStatus;
    }

    public void setStIndexStatus(Boolean stIndexStatus) {
        this.stIndexStatus = stIndexStatus;
    }

    public String getStIndexCrParam() {
        return stIndexCrParam;
    }

    public void setStIndexCrParam(String stIndexCrParam) {
        this.stIndexCrParam = stIndexCrParam;
    }
}
