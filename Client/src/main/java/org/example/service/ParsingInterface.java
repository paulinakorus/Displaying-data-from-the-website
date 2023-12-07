package org.example.service;

import org.example.model.*;

import java.util.List;

public interface ParsingInterface  {
    List<City> getCityList();
    List<Station> getAllCity(String name);
    List<Station> fetchAll();
    Stand[] fetchStand(int stationId);
    List<Stand[]> getStands();
    Sensor fetchSensor(int sensorId);
    IndeksAir fetchIndeksAir(int stationId);
    List<IndeksAir> getIndeksAir();
}
