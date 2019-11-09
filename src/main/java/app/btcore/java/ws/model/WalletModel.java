package app.btcore.java.ws.model;

public class WalletModel extends BaseModel {

    public String status;

    public WalletStellarData stellar;

    public AssetModel[] assets;

    public static class WalletStellarData {
        public String publicKey;
    }
}
