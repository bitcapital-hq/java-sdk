package app.btcore.java.model;

import app.btcore.java.model.role.UserRole;
import app.btcore.java.model.states.BaseStatefulModel;
import app.btcore.java.model.status.UserStatus;

import javax.annotation.Nullable;
import java.util.List;

public class AssetModel extends BaseModel {

    public String name;

    public String code;

    public String provider;

    @Nullable
    public String balance;

    @Nullable
    public String consolidatedBalance;

    @Nullable
    public List<String> pendingPayments;
}

