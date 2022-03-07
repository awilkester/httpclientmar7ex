package com.dci;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void okHTTP(String endPoint) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(endPoint)
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            int responseCode = response.code();
            String responseBody = response.body().string();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement je = JsonParser.parseString(responseBody);
            String prettyJsonString = gson.toJson(je);
            System.out.println(prettyJsonString);
            HashMap<String, Map<String, Object>> map = new Gson().fromJson(responseBody, HashMap.class);
            //System.out.println(map);

            System.out.println("The temperature in your city is: "+map.get("current").get("temp_c"));
            System.out.println("Air quality of your city is: "+map.get("current").get("air_quality"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void method2(String endPoint){
  HttpClient client = HttpClient.newHttpClient();
  HttpRequest request = HttpRequest
      .newBuilder()
      .uri(
          URI
              .create(endPoint) // using json placeholder url
      )
      .GET() // the HTTP request method
      .build(); // building the request object
  String uglyJsonString = client.sendAsync(
      request, // our HttpRequest instance
      HttpResponse.BodyHandlers.ofString()
  )
      .thenApply(HttpResponse::body)
//      .thenAccept(StringBuilder::append)
      .join();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);

}

// weather api key 7e10b6cddb5e4201958141039220303

//    Write a Java program that displays the current weather of a city. It should work as follows:
//
//Allow a user to input a text value corresponding to a city.
//Display the current weather of the specified city.
//Ensure that the air quality information is also included along with the weather information.
//Use the OkHttp Http client library.
    public static void main(String[] args) {
        String weatherAPI = "http://api.weatherapi.com/v1/current.json";
        String apiKey = "?key=c77682ddb3614fa8a1f142310220303";
        StringBuilder city = new StringBuilder("&q=");
        String aqi = "&aqi=yes";
        System.out.println("Welcome to Andrew's Weather App!" +
                "\n\nTo begin, please input which city you'd like to get weather data for.");

        Scanner scanner = new Scanner(System.in);
        city.append(scanner.nextLine());
        String apiEndpoint = weatherAPI + apiKey + city;
        System.out.println(apiEndpoint);
        okHTTP(weatherAPI + apiKey + city + aqi);
    }
}
