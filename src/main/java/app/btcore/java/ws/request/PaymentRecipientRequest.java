package app.btcore.java.ws.request;

public class PaymentRecipientRequest {
  public String asset;
  public String amount;
  public String destination;

  public PaymentRecipientRequest(String amount, String destination) {
    this(amount, destination, null);
  }
  
  public PaymentRecipientRequest(String amount, String destination, String asset) {
    this.asset = asset;
    this.amount = amount;
    this.destination = destination;
  }
}