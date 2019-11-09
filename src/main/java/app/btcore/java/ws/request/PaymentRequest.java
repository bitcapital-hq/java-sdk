package app.btcore.java.ws.request;

import java.util.List;

public class PaymentRequest {
  public String source;
  public PaymentRecipientRequest[] recipients;

  public PaymentRequest(String source, PaymentRecipientRequest[] recipients) {
    this.source = source;
    this.recipients = recipients;
  }
}