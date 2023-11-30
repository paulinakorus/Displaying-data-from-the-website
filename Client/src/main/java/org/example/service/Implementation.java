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

import static java.net.URI.create;

public class Implementation {

    private HttpURLConnection connection;
    private HttpClient client;
    public Implementation(HttpURLConnection connection, HttpClient client){
        this.connection = connection;
        this.client = client;
    }

    public void fetchAll(){
        HttpRequest request = HttpRequest.newBuilder().uri(create("https://api.gios.gov.pl/pjp-api/rest/station/findAll")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply(Implementation::parseStation).join();          // thenApply(Main::parse) - could be parsing into objects

        //client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(System.out::println).join();
    }

    public static String parseStation(String responseBody) {
        JSONArray albums = new JSONArray(responseBody);                                 // getting our response to JSONArray
        for (int i=0; i < albums.length(); i++){
            JSONObject album = albums.getJSONObject(i);                                 // getting object
            int stationId = album.getInt("id");
            String stationName = album.getString("stationName");
            double gegrLat = album.getDouble("gegrLat");
            double gegrLon = album.getDouble("gegrLon");

            JSONObject fullCity = album.getJSONObject("city");
            int cityId = fullCity.getInt("id");
            String cityName = fullCity.getString("name");

            JSONObject fullCommune = fullCity.getJSONObject("commune");
            String communeName = fullCommune.getString("communeName");
            String districtName = fullCommune.getString("districtName");
            String provinceName = fullCommune.getString("provinceName");
            Commune commune = new Commune(communeName, districtName, provinceName);

            City city = new City(cityId, cityName, commune);
            String adressStreet = album.getString("addressStreet");

            Station station = new Station(stationId, stationName, gegrLat, gegrLon, city, adressStreet);
        }
        return null;
    }
}
