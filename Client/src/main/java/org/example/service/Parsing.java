package org.example.service;

import org.example.model.IndeksAir;
import org.example.model.Sensor;
import org.example.model.Stand;
import org.example.model.Station;

import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import static org.example.service.Client.get;

public class Parsing {
    private HttpURLConnection connection;
    private HttpClient client;
    private static List<Sensor> sensorList = new ArrayList<>();

    public Station[] fetchAll(){
        var data = get("/station/findAll", Station[].class);
        return data;
    }

    public Stand[] fetchStand(int stationId){
        var data = get("/station/sensors/" + stationId, Stand[].class);
        return data;
    }

    public List<Stand[]> getStands(){
        var stations = fetchAll();
        List<Stand[]> standsList = new ArrayList<>();

        for(int i=0; i<stations.length; i++){
            var stand = fetchStand(stations[i].getId());
            if(stand != null){
                standsList.add(stand);
            }
        }
        return standsList;
    }

    public Sensor fetchSensor(int sensorId){
        var data = get("/data/getData/" + sensorId, Sensor.class);
        return data;
    }

    public IndeksAir fetchIndeksAir(int stationId){
        var data = get("/aqindex/getIndex/" + stationId, IndeksAir.class);
        return data;
    }

    public List<IndeksAir> getIndeksAir(){
        var stations = fetchAll();
        List<IndeksAir> indeksAirList = new ArrayList<>();

        for(int i=0; i<stations.length; i++){
            var indeksAir = fetchIndeksAir(stations[i].getId());
            if(indeksAir != null){
                indeksAirList.add(indeksAir);
            }
        }
        return indeksAirList;
    }
}
