package app.btcore.java.ws;

import app.btcore.java.ws.model.TransactionModel;
import app.btcore.java.ws.request.PaymentRequest;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface PaymentWebService {
  @POST("payments")
  Call<TransactionModel> pay(@Body() PaymentRequest request);
}