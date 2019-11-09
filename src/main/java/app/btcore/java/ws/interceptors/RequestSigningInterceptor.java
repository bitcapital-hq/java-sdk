package app.btcore.java.ws.interceptors;

import app.btcore.java.Bitcapital;
import app.btcore.java.RequestSigning;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Date;

public class RequestSigningInterceptor implements Interceptor {
    private final Bitcapital bitcapital;

    public RequestSigningInterceptor(Bitcapital bitcapital) {
        this.bitcapital = bitcapital;
    }

    private String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();

        } catch (final IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String method = request.method().toUpperCase();
        String url = "/" + String.join("/", request.url().pathSegments());
        String body = null;

        if ((method.equals("POST") || method.equals("PUT")) && request.body() != null) {
            body = this.bodyToString(request.body());
        }

        try {
            long now = System.currentTimeMillis();
            String signature = new RequestSigning().sign(bitcapital.getClientSecret(), method, url, body, now);
            request = request.newBuilder()
                .addHeader("X-Request-Timestamp", String.valueOf(now))
                .addHeader("X-Request-Signature", signature)
                .build();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return chain.proceed(request);
    }
}