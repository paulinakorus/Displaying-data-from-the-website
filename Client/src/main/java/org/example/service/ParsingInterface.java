package org.example.service;

import org.example.model.IndeksAir;
import org.example.model.Sensor;
import org.example.model.Stand;
import org.example.model.Station;

import java.util.List;

public interface ParsingInterface  {
    Station[] fetchAll();
    Stand[] fetchStand(int stationId);
    List<Stand[]> getStands();
    Sensor fetchSensor(int sensorId);
    IndeksAir fetchIndeksAir(int stationId);
    List<IndeksAir> getIndeksAir();
}
