package org.example.service;

import org.example.model.Sensor;
import org.example.model.Stand;
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
        //implementation.fetchStands();
        List<Station> stationList = implementation.getStationList();
        //List<List<Stand>> standList = implementation.getStandList();
        implementation.fetchSensors();
        List<Sensor> sensorList = implementation.getSensorList();
    }
}
