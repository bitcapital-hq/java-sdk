package app.btcore.java;

import app.btcore.java.model.OAuthCredentials;
import app.btcore.java.samples.SampleCurrentStatus;
import app.btcore.java.samples.SampleCurrentUser;
import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Response;

import java.io.IOException;

public class MainApplication {

    public static final Dotenv dotenv = Dotenv.load();

    public static void main(String... args) throws IOException {
        String apiUrl = MainApplication.dotenv.get("BITCAPITAL_URL");
        String clientId = MainApplication.dotenv.get("BITCAPITAL_CLIENT_ID");
        String clientSecret = MainApplication.dotenv.get("BITCAPITAL_CLIENT_SECRET");
        Bitcapital bitcapital = Bitcapital.initialize(apiUrl, clientId, clientSecret);

        // Ensure server is online and reachable
        SampleCurrentStatus.getCurrentStatus(bitcapital);

        // Authenticate using supplied credentials
        MainApplication.authenticate(bitcapital);

        // Perform a simple API call
        SampleCurrentUser.getCurrentUser(bitcapital);

        System.exit(0);
    }

    private static boolean authenticate(Bitcapital bitcapital) throws IOException {
        String email = MainApplication.dotenv.get("BITCAPITAL_EMAIL");
        String password = MainApplication.dotenv.get("BITCAPITAL_PASSWORD");

        Response<OAuthCredentials> currentTokenCall = bitcapital.oauth().token(
            "Basic " + bitcapital.getBasicToken(),
            email,
            password,
            null,
            "password"
        ).execute();

        if (!currentTokenCall.isSuccessful()) {
            MainApplication.handleError(currentTokenCall);
            return false;
        } else {
            // Print OAuth 2.0 credentials
            System.out.println("\nAuthenticated successfully: \n");
            OAuthCredentials currentToken = currentTokenCall.body();
            System.out.println("- Access Token: " + currentToken.accessToken);
            System.out.println("- Refresh Token: " + currentToken.refreshToken);
            System.out.println("- Expires In (seconds): " + currentToken.expiresIn);

            // Adds token to header interceptor
            bitcapital.setBearerToken(currentToken.accessToken);
            return true;
        }
    }

    private static <T> void handleError(Response<T> response) {
        System.out.println("\nERROR [" + response.code() + "] " + response.message());
        try {
            if (response.errorBody() != null) {
                System.out.println(response.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n");
    }

}
