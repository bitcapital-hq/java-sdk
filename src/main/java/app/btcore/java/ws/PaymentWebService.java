package app.btcore.java.ws;

import app.btcore.java.request.PaymentRequest;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface PaymentWebService {
  @POST("payments")
  Call<Object> payment(@Body() PaymentRequest request);
}