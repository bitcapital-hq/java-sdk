/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package app.btcore.java.signing;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to handle the HMAC SHA256 request signature routines.
 */
public class RequestSigning {
    private static final char[] digits = "0123456789ABCDEF".toCharArray();

    /**
     * Signs a simple request using the Client Secret. This method should be used when no request body was supplied,
     * for example on GET requests.
     *
     * @param secret The OAuth 2.0 Client Secret
     * @param method The HTTP method to be used with this signature
     * @param url The API URL to be used with this signature.
     *
     * @return The request signature generated for this request.
     * @throws InvalidKeyException Throws exception if the key supplied is invalid.
     */
    public String sign(String secret, String method, String url) throws InvalidKeyException {
        return this.sign(secret, method, url, null, System.currentTimeMillis());
    }

    /**
     * Signs a request using the Client Secret and the request body. This method should be used when there
     * is a request body to be signed, for example in POST and PUT requests.
     *
     * @param secret The OAuth 2.0 Client Secret
     * @param method The HTTP method to be used with this signature
     * @param url The API URL to be used with this signature.
     * @param body The request body to be used with this signature.
     *
     * @return The request signature generated for this request.
     * @throws InvalidKeyException Throws exception if the key supplied is invalid.
     */
    public String sign(String secret, String method, String url, String body) throws InvalidKeyException {
        return this.sign(secret, method, url, body, System.currentTimeMillis());
    }

    /**
     * Signs a request using the Client Secret and the request body and a specific timestamp. This method is considered
     * internal API and should be used for debug purposes only. Prefer using the `sign(secret, method, url, body)` method.
     *
     * @param secret The OAuth 2.0 Client Secret
     * @param method The HTTP method to be used with this signature
     * @param url The API URL to be used with this signature.
     * @param body The request body to be used with this signature.
     * @param timestamp The timestamp to sign this request with.
     *
     * @return The request signature generated for this request.
     * @throws InvalidKeyException Throws exception if the key supplied is invalid.
     */
    public String sign(String secret, String method, String url, String body, long timestamp) throws InvalidKeyException {
        List<String> segments = new ArrayList<>();

        segments.add(method);
        segments.add(url);
        segments.add(String.valueOf(timestamp));

        if (body != null && body.length() > 0) {
            segments.add(body);
        }

        return this.raw(secret, String.join(",", segments));
    }

    /**
     * Generates signature hash from raw string.
     *
     * @param key The secret key to be used in the algorithm
     * @param content The content to be used in the algorithm
     *
     * @return The generated hash
     * @throws InvalidKeyException Throws exception if the key supplied is invalid.
     */
    public String raw(String key, String content) throws InvalidKeyException {
        String algorithm = "HmacSHA256";

        try {
            return this.generateSignature(content, key, algorithm);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException exception) {
            // Algorithm or encoding is not available, maybe we should crash
            throw new RuntimeException(exception);
        }
    }

    private static String bytesToHex(final byte[] bytes) {
        final StringBuilder buf = new StringBuilder();
        for (byte aByte : bytes) {
            buf.append(RequestSigning.digits[(aByte >> 4) & 0x0f]);
            buf.append(RequestSigning.digits[aByte & 0x0f]);
        }
        return buf.toString();
    }

    private String generateSignature(String content, String key, String algorithm)
        throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        // 1. Get an algorithm instance.
        Mac sha256_hmac = Mac.getInstance(algorithm);

        // 2. Create secret key.
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);

        // 3. Assign secret key algorithm.
        sha256_hmac.init(secret_key);

        // You can use any other encoding format to get hash text in that encoding.
        return RequestSigning.bytesToHex(sha256_hmac.doFinal(content.getBytes(StandardCharsets.UTF_8))).toLowerCase();
    }
}
