package app.btcore.java.ws;

import app.btcore.java.response.StatusResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;

public interface StatusWebService {
  @GET("status")
  Call<StatusResponse> status();
}