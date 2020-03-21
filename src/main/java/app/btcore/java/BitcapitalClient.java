package app.btcore.java;

import app.btcore.java.ws.interceptors.HttpLoggingInterceptor;
import app.btcore.java.ws.interceptors.OAuthTokenInterceptor;
import app.btcore.java.ws.interceptors.RequestSigningInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Nullable;
import java.io.IOException;

public class BitcapitalClient {

    /**
     * The OkHttp client used in the requests, with all interceptors and configurations setup for
     * Bit Capital APi requests.
     */
    protected final OkHttpClient client;
    private final Bitcapital bitcapital;
    private final OAuthTokenInterceptor authorizationHeaderInterceptor;
    private final Retrofit retrofitClient;

    public BitcapitalClient(Bitcapital bitcapital, boolean verbose) {
        this.bitcapital = bitcapital;
        this.authorizationHeaderInterceptor = new OAuthTokenInterceptor();

        // Prepare OkHttp default interceptors
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        RequestSigningInterceptor signatureHeaderInterceptor = new RequestSigningInterceptor(bitcapital);

        // Prepare the request logging
        if (verbose) {
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        }

        // Create the OK Http client for request interception
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .addInterceptor(this.authorizationHeaderInterceptor)
            .addInterceptor(signatureHeaderInterceptor);

        // Prepare the request logging
        if (verbose) {
            builder.addInterceptor(logging);
        }

        this.client = builder.build();

        this.retrofitClient = new Retrofit.Builder()
            .client(this.client)
            .baseUrl(this.bitcapital.getApiUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    /**
     * Sets current Bearer token for authenticated calls, or use "null" for no authentication.
     */
    public void setBearerToken(String bearerToken) {
        this.authorizationHeaderInterceptor.setAuthorizationHeader("Bearer " + bearerToken);
    }

    public <T> T retrofit(Class<T> cls) {
        return this.retrofitClient.create(cls);
    }

    public OkHttpClient getInstance() {
        return client;
    }

    public String getFullUrl(String path) {
        HttpUrl baseUrl = HttpUrl.parse(this.bitcapital.getApiUrl());

        if (baseUrl != null) {
            HttpUrl url = baseUrl.newBuilder().encodedPath(path).build();
            return url.toString();
        }
        return null;
    }

    @Nullable
    public <T> T get(String path, Class<T> cls) throws IOException {
        Request req = new Request.Builder()
            .get()
            .url(this.getFullUrl(path))
            .build();

        okhttp3.Response response = this.client.newCall(req).execute();

        if (response.body() != null) {
            return new Gson().fromJson(response.body().string(), cls);
        }

        return null;
    }

    @Nullable
    public <T> T post(String path, RequestBody body, Class<T> cls) throws IOException {
        Request req = new Request.Builder()
            .post(body)
            .url(this.getFullUrl(path))
            .build();

        okhttp3.Response response = this.client.newCall(req).execute();

        if (response.body() != null) {
            return new Gson().fromJson(response.body().string(), cls);
        }

        return null;
    }

    @Nullable
    public <T> T put(String path, RequestBody body, Class<T> cls) throws IOException {
        Request req = new Request.Builder()
            .put(body)
            .url(this.getFullUrl(path))
            .build();

        okhttp3.Response response = this.client.newCall(req).execute();

        if (response.body() != null) {
            return new Gson().fromJson(response.body().string(), cls);
        }

        return null;
    }

    @Nullable
    public <T> T delete(String path, Class<T> cls) throws IOException {
        Request req = new Request.Builder()
            .delete()
            .url(this.getFullUrl(path))
            .build();

        okhttp3.Response response = this.client.newCall(req).execute();

        if (response.body() != null) {
            return new Gson().fromJson(response.body().string(), cls);
        }

        return null;
    }
}
