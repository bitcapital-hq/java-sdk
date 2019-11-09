package app.btcore.java.ws.response;

import com.google.gson.annotations.SerializedName;

public class OAuthTokenResponse {

  @SerializedName("access_token")
  public String accessToken;

  @SerializedName("refresh_token")
  public String refreshToken;

  @SerializedName("expires_in")
  public String expiresIn;

  @SerializedName("user_id")
  public String userId;
}