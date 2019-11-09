package app.btcore.java.ws;

import app.btcore.java.ws.response.StatusResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatusWebService {
    /**
     * Get current API status.
     */
    @GET("status")
    Call<StatusResponse> current();
}