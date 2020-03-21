package app.btcore.java;

import app.btcore.java.ws.OAuthWebService;
import app.btcore.java.ws.StatusWebService;

import java.util.Base64;

/**
 * The main Bit Capital SDK service, wraps all available APIs and utilities.
 */
public final class Bitcapital {
    /* Internal Retrofit web services */
    private final StatusWebService statusAPI;
    private final OAuthWebService oauthAPI;

    /* Internal properties */
    protected final String apiUrl;
    protected final String clientId;
    protected final String clientSecret;
    protected final BitcapitalClient client;

    private Bitcapital(String apiUrl, String clientId, String clientSecret) {
        this.apiUrl = apiUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.client = new BitcapitalClient(this, false);

        // Create the instances of our API interfaces
        this.statusAPI = this.client.retrofit(StatusWebService.class);
        this.oauthAPI = this.client.retrofit(OAuthWebService.class);
    }

    /**
     * Initializes the Bit Capital SDK instance.
     * <p>
     * You'll need to supply a valid set of OAuth 2.0 credentials, and the API Url to be used in the requests.
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
     * @return The currently configured OAuth 2.0 Basic Credential for authenticated requests.
     */
    public String getBasicToken() {
        return Base64.getEncoder().encodeToString((this.clientId + ":" + this.clientSecret).getBytes());
    }

    /**
     * Gets the base API URL for the requests.
     *
     * @return The API URL configured in the `initialize()` method.
     */
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
        this.client.setBearerToken(bearerToken);
    }

    /**
     * Gets the current OkHttp client instance.
     *
     * @return The OkHttp client instance.
     */
    public BitcapitalClient getClient() {
        return client;
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


}