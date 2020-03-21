package app.btcore.java.ws;

import app.btcore.java.model.OAuthCredentials;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OAuthWebService {
    @FormUrlEncoded
    @POST("oauth/token")
    Call<OAuthCredentials> token(@Header("Authorization") String basic, @Field("username") String email, @Field("password") String password, @Field("scopes") String[] scopes, @Field("grant_type") String grantType);
}