package projects.java.android.ifsccodevalidator;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import projects.java.android.ifsccodevalidator.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final String NA = "NA";
    private String ifscCode;
    private ActivityMainBinding binding;
    private LottieAnimationView lottieAnimationView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        setNightModeOFF();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opt, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.getBankDetailsBtn.setOnClickListener(v -> onGetBankDetailsBtnClicked());
    }

    private void init() {
        listView = binding.listView;
        lottieAnimationView = binding.lottieAnimationView;
    }

    private void setNightModeOFF() {
        UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
    }

    private void onGetBankDetailsBtnClicked() {
        setError("", false);
        ifscCode = Objects.requireNonNull(binding.ifscTxtEdt.getText()).toString();
        setListViewVisibility(View.GONE);
        if (ifscCode.equals("")) {
            setError("Enter an IFSC code", true);
        } else if (ifscCode.length() <= 10) {
            setError("Enter a valid IFSC code", true);
        } else {
            if (hasOnline()) {
                setLottieAnimation(R.raw.lottie_processing, true);
                setBankDetails();
            } else {
                setLottieAnimation(R.raw.lottie_no_internet, true);
                Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setError(String msg, boolean isVisible) {
        TextInputLayout layout = binding.ifscTxtInL;
        if (isVisible) {
            setListViewVisibility(View.GONE);
            layout.setErrorEnabled(true);
            layout.setError(msg);
        } else {
            layout.setError(null);
        }
    }

    private boolean hasOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.share_opt) {
            Toast.makeText(this, "Share this app", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.feedback_opt) {
            composeEmail();
        } else if (id == R.id.about_opt) {
            openLink(Uri.parse(getResources().getString(R.string.github)));
        }
        return true;
    }

    private void openLink(Uri uri) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setLottieAnimation(@RawRes int rawRes, boolean isVisible) {
        if (isVisible) {
            lottieAnimationView.setAnimation(rawRes);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
            lottieAnimationView.playAnimation();
        } else {
            lottieAnimationView.setVisibility(View.GONE);
        }
    }

    private void composeEmail() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND).
                    putExtra(Intent.EXTRA_EMAIL, new String[]{"shibaprasadsahu943@gmail.com"})
                    .putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            intent.setSelector(new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto:")));
            startActivity(Intent.createChooser(intent, "Send Feedback"));
        } catch (Exception e) {
            Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setBankDetails() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BankDetailsAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BankDetailsAPI bankDetailsAPI = retrofit.create(BankDetailsAPI.class);
        Call<BankDetails> bankDetailsAPICall = bankDetailsAPI.getBankDetailsJSONObject(ifscCode);
        bankDetailsAPICall.enqueue(new Callback<BankDetails>() {
            @Override
            public void onResponse(@NonNull Call<BankDetails> call, @NonNull Response<BankDetails> response) {
                showBankDetails(response.body());
                Log.d(TAG, "Response received - " + ifscCode);
            }

            @Override
            public void onFailure(@NonNull Call<BankDetails> call, @NonNull Throwable t) {
                setLottieAnimation(R.raw.lottie_try_again, true);
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private ListAdapter getListAdapter(BankDetails details) {
        Resources res = getResources();
        ArrayList<List> lists = new ArrayList<>();
        lists.add(new List(res.getString(R.string.bank), details.getBankName()));
        lists.add(new List(res.getString(R.string.ifsc), details.getIFSC()));
        lists.add(new List(res.getString(R.string.branch), details.getBranchName()));
        try {
            String micr = details.getMICR();
            if (!micr.equals("") && !micr.equals(NA)) {
                lists.add(new List(res.getString(R.string.micr), micr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String swift = details.getSWIFT();
            if (!swift.equals("") && !swift.equals(NA)) {
                lists.add(new List(res.getString(R.string.swift), swift));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        lists.add(new List(res.getString(R.string.upi), details.isUPI() ? YES : NO));
        lists.add(new List(res.getString(R.string.neft), details.isNEFT() ? YES : NO));
        lists.add(new List(res.getString(R.string.imps), details.isIMPS() ? YES : NO));
        lists.add(new List(res.getString(R.string.rtgs), details.isRTGS() ? YES : NO));
        lists.add(new List(res.getString(R.string.bank_code), details.getBANK_CODE()));
        try {
            String contact = details.getContact();
            if (!contact.equals("") && !contact.equals(NA)) {
                lists.add(new List(res.getString(R.string.contact), contact));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        lists.add(new List(res.getString(R.string.address), details.getAddress() + "," + details.getCityName()));
        lists.add(new List(res.getString(R.string.district), details.getDistrictName()));
        lists.add(new List(res.getString(R.string.state), details.getStateName()));
        return new ListAdapter(this, lists);
    }

    private void setListViewVisibility(final int VISIBLE_TYPE) {
        listView.setVisibility(VISIBLE_TYPE);
    }

    private void showBankDetails(BankDetails details) {
        if (details != null) {
            listView.setAdapter(getListAdapter(details));
            setLottieAnimation(R.raw.lottie_processing, false);
            setListViewVisibility(View.VISIBLE);
        } else {
            setListViewVisibility(View.GONE);
            setLottieAnimation(R.raw.lottie_processing, false);
            setError("Invalid IFSC Code", true);
        }
    }


}