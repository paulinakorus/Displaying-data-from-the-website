package org.example.service;

import org.example.service.Implementation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;

public class Client
{
    public static void main(String[] args ) throws IOException {
        HttpURLConnection connection = null;

        HttpClient client = HttpClient.newHttpClient();
        Implementation implementation = new Implementation(connection, client);
        implementation.fetchAll();
    }
}
