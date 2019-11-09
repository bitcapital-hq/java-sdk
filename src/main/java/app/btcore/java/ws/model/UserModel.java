package app.btcore.java.ws.model;

public class UserModel extends BaseStatefulModel {

    public String name;

    public String role;

    public String firstName;

    public String lastName;

    public String email;

    public DomainModel domain;

    public ConsumerModel consumer;

    public WalletModel[] wallets;
}
