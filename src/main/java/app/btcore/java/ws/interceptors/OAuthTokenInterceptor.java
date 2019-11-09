package app.btcore.java.ws.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OAuthTokenInterceptor implements Interceptor {
    protected String authorizationHeader;

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (authorizationHeader != null && authorizationHeader.length() > 0) {
            request = request.newBuilder().addHeader("Authorization", authorizationHeader).build();
        }

        return chain.proceed(request);
    }
}