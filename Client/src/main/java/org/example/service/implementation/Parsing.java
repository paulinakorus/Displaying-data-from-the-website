package org.example.service.implementation;

import org.example.model.IndeksAir;
import org.example.model.Sensor;
import org.example.model.Stand;
import org.example.model.Station;
import org.example.service.ParsingInterface;

import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

public class Parsing implements ParsingInterface{
    private static ParsingInterface INSTANCE = null;
    private HttpURLConnection connection;
    private HttpClient client;
    private static List<Sensor> sensorList = new ArrayList<>();

    @Override
    public Station[] fetchAll(){
        var data = Client.get("/station/findAll", Station[].class);
        return data;
    }

    @Override
    public Stand[] fetchStand(int stationId){
        var data = Client.get("/station/sensors/" + stationId, Stand[].class);
        return data;
    }

    @Override
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

    @Override
    public Sensor fetchSensor(int sensorId){
        var data = Client.get("/data/getData/" + sensorId, Sensor.class);
        return data;
    }

    @Override
    public IndeksAir fetchIndeksAir(int stationId){
        var data = Client.get("/aqindex/getIndex/" + stationId, IndeksAir.class);
        return data;
    }

    @Override
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

    public static ParsingInterface getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Parsing();
        }
        return INSTANCE;
    }
}