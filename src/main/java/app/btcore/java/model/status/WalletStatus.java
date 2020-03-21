package app.btcore.java.model.status;

public enum WalletStatus {
    ready,
    registered_in_provider,
    registered,
    pending,
    pending_provider_approval,
    failed,
    rejected;
}
