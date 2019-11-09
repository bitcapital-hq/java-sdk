package app.btcore.java.ws;

import app.btcore.java.ws.model.UserModel;
import app.btcore.java.ws.response.StatusResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserWebService {
    /**
     * Gets current User information.
     */
    @GET("users/me")
    Call<UserModel> me();
}