package projects.java.android.ifsccodevalidator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BankDetailsAPI {
    String BASE_URL = "https://ifsc.razorpay.com/";

    @GET
    Call<BankDetails> getBankDetailsJSONObject(@Url String url);
}
