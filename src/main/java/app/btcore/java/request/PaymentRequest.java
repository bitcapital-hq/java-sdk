package app.btcore.java.request;

import java.util.List;

import app.btcore.java.model.PaymentRecipientModel;

public class PaymentRequest {
  public String source;
  public List<PaymentRecipientModel> recipients;

  public PaymentRequest(String source, List<PaymentRecipientModel> recipients) {
    this.source = source;
    this.recipients = recipients;
  }
}