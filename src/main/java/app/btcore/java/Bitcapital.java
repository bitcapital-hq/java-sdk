package app.btcore.java;

import java.io.IOException;
import java.util.List;

import app.btcore.java.response.StatusResponse;
import app.btcore.java.ws.StatusWebService;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class Bitcapital {
  public static final String API_URL = "https://testnet.btcore.app";

  public static void main(String... args) throws IOException {
    // Create a very simple REST adapter which points the GitHub API.
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    // Create an instance of our GitHub API interface.
    StatusWebService statusAPI = retrofit.create(StatusWebService.class);

    // Create a call instance for looking up Retrofit contributors.
    StatusResponse currentStatus = statusAPI.status().execute().body();

    // Print basic API status
    System.out.println("\nAPI URL: " + Bitcapital.API_URL);
    System.out.println("\nAPI Version: " + currentStatus.name + ":" + currentStatus.version + "\n\n");
  }
}