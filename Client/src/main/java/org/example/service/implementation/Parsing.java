package org.example.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.IndeksAir;
import org.example.model.Sensor;
import org.example.model.Stand;
import org.example.model.Station;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Parsing {
    private static final String BASE_API_URL = "https://api.gios.gov.pl/pjp-api/rest";
    private static final HttpClient client =  HttpClient.newHttpClient();
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

    public <R> R get(String path, Class<R> responseType){
        var objectMapper = new ObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_API_URL + path))
                .GET()
                .build();

        try{
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), responseType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
