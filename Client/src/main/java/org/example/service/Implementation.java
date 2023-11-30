package org.example.service;

import org.example.model.City;
import org.example.model.Commune;
import org.example.model.Station;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static java.net.URI.create;

public class Implementation {

    private HttpURLConnection connection;
    private HttpClient client;
    private static List<Station> stationList = new ArrayList<>();
    public Implementation(HttpURLConnection connection, HttpClient client){
        this.connection = connection;
        this.client = client;
    }

    // Getting all Stations
    public void fetchAll(){
        HttpRequest request = HttpRequest.newBuilder().uri(create("https://api.gios.gov.pl/pjp-api/rest/station/findAll")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply(Implementation::parseStations).join();          // thenApply(Main::parse) - could be parsing into objects

        //client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(System.out::println).join();
    }

    public static String parseStations(String responseBody) {
        JSONArray albums = new JSONArray(responseBody);                                 // getting our response to JSONArray
        for (int i=0; i < albums.length(); i++){
            JSONObject album = albums.getJSONObject(i);                                 // getting object
            stationList.add(parseOneStation(album));
        }
        return null;
    }

    private static Station parseOneStation(JSONObject album){
            int stationId = album.getInt("id");
            String stationName = album.getString("stationName");
            double gegrLat = album.getDouble("gegrLat");
            double gegrLon = album.getDouble("gegrLon");
            String adressStreet = null;
            if(!album.isNull("addressStreet")){
                adressStreet = album.getString("addressStreet");
            }

            Station station = new Station(stationId, stationName, gegrLat, gegrLon, parseCity(album), adressStreet);
            return station;
    }
    private static City parseCity(JSONObject album){
        JSONObject fullCity = album.getJSONObject("city");
        int cityId = fullCity.getInt("id");
        String cityName = fullCity.getString("name");

        City city = null;
        //if(cityId != 0 && cityName != null){
            city = new City(cityId, cityName, parseCommune(fullCity));
        //}
        return city;
    }

    private static Commune parseCommune(JSONObject fullCity){
        JSONObject fullCommune = fullCity.getJSONObject("commune");
        String communeName = fullCommune.getString("communeName");
        String districtName = fullCommune.getString("districtName");
        String provinceName = fullCommune.getString("provinceName");

        Commune commune = null;
        //if(communeName != null && districtName != null && provinceName != null){
            commune = new Commune(communeName, districtName, provinceName);
        //}
        return commune;
    }

}
