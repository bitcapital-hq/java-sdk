package app.btcore.java.model;

import app.btcore.java.model.states.BaseStatefulModel;
import app.btcore.java.model.status.WalletStatus;

import javax.annotation.Nullable;
import java.util.List;

public class WalletModel extends BaseStatefulModel<WalletStatus> {

    public static class Stellar {
        public String publicKey;
    }

    public Stellar stellar;

    @Nullable
    public List<AssetModel> assets;

}
