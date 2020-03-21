package app.btcore.java.model.status;

public enum ConsumerStatus {
    /* Success states */
    ready,
    rejected,
    manually_approved,

    /* Pending states */
    pending_phone_verification,
    pending_documents,
    pending_email_verification,
    pending_deletion,
    pending_legal_acceptance,
    pending_billing_plan_subscription,

    /* Processing states */
    processing_documents,
    processing_wallets,
    processing_provider_documents,
    kyc_rechecking,

    /* Error states */
    provider_failed,
    invalid_documents,
    manual_verification,
    provider_rejected,

    /* Blocked state */
    suspended,
    blocked,
    deleted;
}
