package app.btcore.java;

import app.btcore.java.ws.OAuthWebService;
import app.btcore.java.ws.PaymentWebService;
import app.btcore.java.ws.StatusWebService;
import app.btcore.java.ws.UserWebService;
import app.btcore.java.ws.interceptors.HttpLoggingInterceptor;
import app.btcore.java.ws.interceptors.OAuthTokenInterceptor;
import app.btcore.java.ws.interceptors.RequestSigningInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Base64;

public final class Bitcapital {
    private final StatusWebService statusAPI;
    private final OAuthWebService oauthAPI;
    private final UserWebService userAPI;
    private final PaymentWebService paymentAPI;

    protected final String apiUrl;
    protected final String clientId;
    protected final String clientSecret;
    protected final OAuthTokenInterceptor authorizationHeaderInterceptor;
    protected final RequestSigningInterceptor signatureHeaderInterceptor;

    private Bitcapital(String apiUrl, String clientId, String clientSecret) {
        this.apiUrl = apiUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizationHeaderInterceptor = new OAuthTokenInterceptor();
        this.signatureHeaderInterceptor = new RequestSigningInterceptor(this);

        // Prepare the request logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        // Create the OK Http client for request interception
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
            .addInterceptor(this.authorizationHeaderInterceptor)
            .addInterceptor(this.signatureHeaderInterceptor)
            .addInterceptor(logging)
            .build();

        // Create a very simple REST adapter which points the Bit Capital APIs
        Retrofit retrofit = new Retrofit.Builder()
            .client(defaultHttpClient)
            .baseUrl(this.apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        // Create the instances of our API interfaces
        this.statusAPI = retrofit.create(StatusWebService.class);
        this.oauthAPI = retrofit.create(OAuthWebService.class);
        this.userAPI = retrofit.create(UserWebService.class);
        this.paymentAPI = retrofit.create(PaymentWebService.class);
    }

    /**
     * Initializes the Bit Capital SDK instance.
     *
     * @param apiUrl       The instance url for API calls
     * @param clientId     The OAuth 2.0 client ID
     * @param clientSecret The OAuth 2.0 client secret
     */
    public static Bitcapital initialize(String apiUrl, String clientId, String clientSecret) {
        return new Bitcapital(apiUrl, clientId, clientSecret);
    }

    /**
     * Gets current Basic token for OAuth 2.0 authorizations.
     *
     * @return
     */
    public String getBasicToken() {
        return Base64.getEncoder().encodeToString((this.clientId + ":" + this.clientSecret).getBytes());
    }

    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Gets the OAuth 2.0 client id for current SDK instance.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets the OAuth 2.0 client secret for current SDK instance.
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Sets current Bearer token for authenticated calls, or use "null" for no authentication.
     */
    public void setBearerToken(String bearerToken) {
        this.authorizationHeaderInterceptor.setAuthorizationHeader("Bearer " + bearerToken);
    }

    /**
     * Gets the Status web service instance.
     */
    public StatusWebService status() {
        return this.statusAPI;
    }

    /**
     * Gets the OAuth web service instance.
     */
    public OAuthWebService oauth() {
        return this.oauthAPI;
    }

    /**
     * Gets the Users web service instance.
     */
    public UserWebService users() {
        return this.userAPI;
    }

    /**
     * Gets the Payment web service instance.
     */
    public PaymentWebService payments() {
        return this.paymentAPI;
    }
}