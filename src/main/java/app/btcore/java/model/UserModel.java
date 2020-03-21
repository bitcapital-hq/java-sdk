package app.btcore.java.model;

import app.btcore.java.model.role.UserRole;
import app.btcore.java.model.states.BaseStatefulModel;
import app.btcore.java.model.status.UserStatus;

import java.util.List;

public class UserModel extends BaseStatefulModel<UserStatus> {

    public String name;

    public String firstName;

    public String lastName;

    public String email;

    public UserRole role;

    public List<WalletModel> wallets;
}

