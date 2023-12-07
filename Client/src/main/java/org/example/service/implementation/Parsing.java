package org.example.service.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.*;
import org.example.service.ParsingInterface;
import org.example.service.implementation.Client;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Parsing implements ParsingInterface {
    private static final String BASE_API_URL = "https://api.gios.gov.pl/pjp-api/rest";
    private static Parsing INSTANCE = null;
    private static final HttpClient client =  HttpClient.newHttpClient();
    private static List<Sensor> sensorList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();

    public List<City> getCityList(){
        List<Station> stationList = fetchAll();
        for (Station station : stationList) {
            addToList(station.getCity());
        }
        return cityList;
    }

    public List<Station> getAllCity(String name){
        List<Station> stationList = fetchAll();
        List<Station> stationCityList = new ArrayList<>();
        for (Station station : stationList) {
            if(station.getCity().getName().equals(name)){
                stationCityList.add(station);
            }
        }
        return stationCityList;
    }

    public List<Station> fetchAll(){
        var data = List.of(get("/station/findAll", Station[].class));
        return data;
    }

    public void addToList (City city){
        if(!ifExist(city)){
            this.cityList.add(city);
        }
    }

    public Boolean ifExist(City ourCity){
        if(this.cityList == null)
            return false;
        else {
            for (City city : this.cityList) {
                if(city.getId() == ourCity.getId())
                    return true;
            }
        }
        return false;
    }

    public Stand[] fetchStand(int stationId){
        var data = get("/station/sensors/" + stationId, Stand[].class);
        return data;
    }

    public List<Stand[]> getStands(){
        var stations = fetchAll();
        List<Stand[]> standsList = new ArrayList<>();

        for(int i=0; i<stations.size(); i++){
            var stand = fetchStand(stations.get(i).getId());
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

        for(int i=0; i<stations.size(); i++){
            var indeksAir = fetchIndeksAir(stations.get(i).getId());
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

    public static ParsingInterface getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Parsing();
        }
        return INSTANCE;
    }
}
