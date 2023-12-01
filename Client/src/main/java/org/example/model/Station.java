package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"addressStreet"})
public class Station {
    private int id;
    private String stationName;
    private double gegrLat;
    private double gegrLon;
    private City city;
    private String adressStreet;

    public Station(int id, String stationName, double gegrLat, double gegrLon, City city, String adressStreet) {
        this.id = id;
        this.stationName = stationName;
        this.gegrLat = gegrLat;
        this.gegrLon = gegrLon;
        this.city = city;
        this.adressStreet = adressStreet;
    }

    public Station(){
        this(0, null, 0.0, 0.0, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getGegrLat() {
        return gegrLat;
    }

    public void setGegrLat(double gegrLat) {
        this.gegrLat = gegrLat;
    }

    public double getGegrLon() {
        return gegrLon;
    }

    public void setGegrLon(double gegrLon) {
        this.gegrLon = gegrLon;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAdressStreet() {
        return adressStreet;
    }

    public void setAdressStreet(String adressStreet) {
        this.adressStreet = adressStreet;
    }
}
