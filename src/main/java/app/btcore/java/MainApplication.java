package app.btcore.java;

import app.btcore.java.ws.model.TransactionModel;
import app.btcore.java.ws.model.UserModel;
import app.btcore.java.ws.model.WalletModel;
import app.btcore.java.ws.request.PaymentRecipientRequest;
import app.btcore.java.ws.request.PaymentRequest;
import app.btcore.java.ws.response.OAuthTokenResponse;
import app.btcore.java.ws.response.StatusResponse;
import retrofit2.Response;

import java.io.IOException;

public class MainApplication {
    public static final String API_URL = "https://testnet.btcore.app";
    public static final String CLIENT_ID = "sample_client_id";
    public static final String CLIENT_SECRET = "sample_client_secret";
    public static final String EMAIL = "user@company.com";
    public static final String PASSWORD = "123456";
    public static final String SOURCE_WALLET = "sample_wallet_id_with_enough_balance";

    public static void main(String... args) throws IOException {
        Bitcapital bitcapital = Bitcapital.initialize(MainApplication.API_URL, MainApplication.CLIENT_ID, MainApplication.CLIENT_SECRET);

        MainApplication.getCurrentStatus(bitcapital);

        boolean isAuthenticated = MainApplication.authenticate(bitcapital);

        if (isAuthenticated) {
            String recipientWalletId = MainApplication.getCurrentUser(bitcapital);

            if (recipientWalletId != null) {
                boolean result = MainApplication.samplePayment(bitcapital, recipientWalletId);

                if (result) {
                    System.exit(0);
                }
            }
        }

        System.exit(1);
    }

    private static void getCurrentStatus(Bitcapital bitcapital) throws IOException {
        StatusResponse currentStatus = bitcapital.status().current().execute().body();

        // Print basic API status
        System.out.println("\nStarting sample script for Bit Capital APIs...\n");
        System.out.println("- API URL: " + MainApplication.API_URL);
        System.out.println("- API Version: " + currentStatus.name + ":" + currentStatus.version + "\n");
    }

    private static boolean authenticate(Bitcapital bitcapital) throws IOException {
        Response<OAuthTokenResponse> currentTokenCall = bitcapital.oauth().token(
            "Basic " + bitcapital.getBasicToken(),
            MainApplication.EMAIL,
            MainApplication.PASSWORD,
            null,
            "password"
        ).execute();

        if (!currentTokenCall.isSuccessful()) {
            MainApplication.handleError(currentTokenCall);
            return false;
        } else {
            // Print OAuth 2.0 credentials
            System.out.println("\nAuthenticated successfully: \n");
            OAuthTokenResponse currentToken = currentTokenCall.body();
            System.out.println("- Access Token: " + currentToken.accessToken);
            System.out.println("- Refresh Token: " + currentToken.refreshToken);
            System.out.println("- Expires In (seconds): " + currentToken.expiresIn);

            // Adds token to header interceptor
            bitcapital.setBearerToken(currentToken.accessToken);
            return true;
        }
    }

    private static String getCurrentUser(Bitcapital bitcapital) throws IOException {
        Response<UserModel> currentUserCall = bitcapital.users().me().execute();

        if (!currentUserCall.isSuccessful()) {
            MainApplication.handleError(currentUserCall);
            return null;
        } else {
            System.out.println("\nCurrent user information: \n");

            UserModel user = currentUserCall.body();
            System.out.println("- ID: " + user.id);
            System.out.println("- Name: " + user.name);
            System.out.println("- Role: " + user.role);
            System.out.println("- Status: " + user.status);
            System.out.println("- Wallets: " + user.role);

            String firstWalletId = null;

            for (WalletModel wallet : user.wallets) {
                if (firstWalletId == null) {
                    firstWalletId = wallet.id;
                }
                System.out.println("  > ID: " + wallet.id);
                System.out.println("    Status: " + wallet.status);
                System.out.println("    Public Key: " + wallet.stellar.publicKey);
            }

            return firstWalletId;
        }
    }

    private static boolean samplePayment(Bitcapital bitcapital, String recipientWalletId) throws IOException {
        PaymentRecipientRequest[] recipients = {new PaymentRecipientRequest("1.00", recipientWalletId)};
        PaymentRequest request = new PaymentRequest(MainApplication.SOURCE_WALLET, recipients);
        Response<TransactionModel> samplePaymentCall = bitcapital.payments().pay(request).execute();

        if (!samplePaymentCall.isSuccessful()) {
            MainApplication.handleError(samplePaymentCall);
            return false;
        } else {
            TransactionModel transaction = samplePaymentCall.body();
            System.out.println("- ID: " + transaction.id);
            return true;
        }
    }

    private static <T> void handleError(Response<T> response) {
        System.out.println("\nERROR: " + response.code());
        try {
            System.out.println(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n");
    }

}
