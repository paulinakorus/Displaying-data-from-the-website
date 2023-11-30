package org.example.service;

import org.example.model.Station;
import org.example.service.Implementation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.util.List;

public class Client
{
    public static void main(String[] args ) throws IOException {
        HttpURLConnection connection = null;

        HttpClient client = HttpClient.newHttpClient();
        Implementation implementation = new Implementation(connection, client);
        implementation.fetchAll();
        List<Station> stationList = implementation.getStationList();
        System.out.println(stationList);
    }
}
