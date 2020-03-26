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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public static TransactionModel sampleAssetEmission(Bitcapital bitcapital) throws IOException {
        HashMap<String, String> recipientsMap = new HashMap<>();
        recipientsMap.put("destination", "DESTINATION_WALLET_ID");
        recipientsMap.put("amount", "10.00");
        recipientsMap.put("asset", "BRLP");

        List<HashMap> recipients = new ArrayList<>();
        recipients.add(recipientsMap);

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("recipients", recipients);
        requestMap.put("source", "SOURCE_WALLET_ID");

        String jsonString = new Gson().toJson((requestMap));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);

        System.out.println("\nEmitting assets to specified wallet...\n");
        TransactionModel payment = bitcapital.getClient().post("/payments", body, TransactionModel.class);

        if(payment != null) {
            System.out.println("- P2P ID: " + payment.id);
        }

        return payment;
    }
}
