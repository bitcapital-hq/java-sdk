package app.btcore.java.samples;

import app.btcore.java.Bitcapital;
import app.btcore.java.model.AssetModel;
import app.btcore.java.model.TransactionModel;
import app.btcore.java.model.UserModel;
import app.btcore.java.model.WalletModel;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.HashMap;

public class SamplePayment {
    public static WalletModel getUserBalance(Bitcapital bitcapital, UserModel user) throws IOException {
        if (user.wallets != null) {
            System.out.println("\nFetching current wallet information from Bit Capital APIs...\n");
            WalletModel wallet = bitcapital.getClient().get("/wallets/" + user.wallets.get(0).id, WalletModel.class);

            if (wallet != null) {
                // Print basic API status
                System.out.println("- User ID: " + user.id);
                System.out.println("- Wallet ID: " + wallet.id);
                System.out.println("- Assets: ");

                if (wallet.assets != null) {
                    for (AssetModel asset : wallet.assets) {
                        System.out.println("  - " + asset.code + " ");
                        System.out.println("      balance: " + asset.balance);
                        System.out.println("      consolidatedBalance: " + asset.balance);
                    }
                }
            }
            return wallet;
        }

        return null;
    }

    public static TransactionModel sampleAssetEmission(Bitcapital bitcapital, String walletId) throws IOException {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("destination", walletId);
        requestMap.put("amount", "10.00");

        String jsonString = new Gson().toJson((requestMap));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);

        System.out.println("\nEmitting assets to specified wallet...\n");
        TransactionModel emission = bitcapital.getClient().post("/assets/TCN/emit", body, TransactionModel.class);

        if(emission != null) {
            System.out.println("- Emission ID: " + emission.id);
        }

        return emission;
    }
}
