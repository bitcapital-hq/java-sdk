package app.btcore.java.model;

public class PaymentRecipientModel {
  public String asset;
  public String amount;
  public String destination;

  public PaymentRecipientModel(String amount, String destination) {
    this(amount, destination, null);
  }
  
  public PaymentRecipientModel(String amount, String destination, String asset) {
    this.asset = asset;
    this.amount = amount;
    this.destination = destination;
  }
}