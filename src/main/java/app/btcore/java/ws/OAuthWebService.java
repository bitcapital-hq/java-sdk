package app.btcore.java.ws;

import app.btcore.java.ws.response.OAuthTokenResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface OAuthWebService {
  @FormUrlEncoded
  @POST("oauth/token")
  Call<OAuthTokenResponse> token(@Header("Authorization") String basic, @Field("username") String email, @Field("password") String password, @Field("scopes") String [] scopes, @Field("grant_type") String grantType);
}