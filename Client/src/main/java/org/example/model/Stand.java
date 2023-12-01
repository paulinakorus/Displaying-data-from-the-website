package org.example.model;

public class Stand {
    private int id;
    private int stationId;
    private Param param;

    public Stand(int standId, int stationId, Param param) {
        this.id = standId;
        this.stationId = stationId;
        this.param = param;
    }

    public Stand(){
        this(0, 0, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }
}
