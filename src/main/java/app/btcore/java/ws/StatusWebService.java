package app.btcore.java.ws;

import app.btcore.java.model.status.ServerStatus;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatusWebService {
    /**
     * Get current API status.
     */
    @GET("status")
    Call<ServerStatus> current();
}