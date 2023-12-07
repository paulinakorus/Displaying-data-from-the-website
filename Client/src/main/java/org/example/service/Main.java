package org.example.service;

import org.example.model.City;
import org.example.model.Station;
import org.example.service.Parsing;
import org.example.service.implementation.Client;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Parsing parsing = new Parsing();
        Client client = new Client();

        List<Station> stationList = parsing.fetchAll();
        List<Station> stations = parsing.getAllCity("Wrocław");
        List<City> cityList = parsing.getCityList();
    }
}