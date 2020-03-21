package app.btcore.java.model;

import com.google.gson.annotations.SerializedName;

public class OAuthCredentials {
    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("refresh_token")
    public String refreshToken;

    @SerializedName("expires_in")
    public String expiresIn;

    @SerializedName("user_id")
    public String userId;
}