package org.example.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.IndeksAir;
import org.example.model.Sensor;
import org.example.model.Stand;
import org.example.model.Station;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Client
{
    private static final String BASE_API_URL = "https://api.gios.gov.pl/pjp-api/rest";
    private static final HttpClient client =  HttpClient.newHttpClient();

    public static void main(String[] args ) throws IOException {
        HttpURLConnection connection = null;
        Parsing parse = new Parsing();

        Station[] tab = parse.fetchAll();
        Stand[] stands = parse.fetchStand(114);
        List<Stand[]> standsList = parse.getStands();
        Sensor sensor = parse.fetchSensor(92);
        IndeksAir indeksAir = parse.fetchIndeksAir(52);
        //List<IndeksAir> indeksAirList = parse.getIndeksAir();                                                         // too slow
    }

    public static <R> R get(String path, Class<R> responseType){
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
