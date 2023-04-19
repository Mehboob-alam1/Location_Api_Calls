package com.mehboob.demo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.mehboob.demo.GetData;
import com.mehboob.demo.Location;
import com.mehboob.demo.Provide;
import com.mehboob.demo.R;
import com.mehboob.demo.ReterofitClient;
import com.mehboob.demo.databinding.ActivitySplashBinding;
import com.mehboob.demo.recievers.CheckInternetConnection;
import com.mehboob.demo.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private SessionManager sessionManager;
    private static final String TAG = "SplashActivity";
    private CheckInternetConnection checkInternetConnection;
    private Provide provide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        sessionManager = new SessionManager(this);
        provide = new Provide(this, this);
        checkInternetConnection = new CheckInternetConnection(SplashActivity.this);


        binding.btnGetStarted.setOnClickListener(view -> {
            if (sessionManager.fetchCountryCode().equals("IN") && sessionManager.fetchCountryName().equals("India")) {
                Log.d(TAG, "India");
                Intent i = new Intent(SplashActivity.this, IndianActivity.class);

                startActivity(i);

                // close this activity

                finish();

            } else {
                Intent i = new Intent(SplashActivity.this, ChromeCustomTabActivity.class);

                startActivity(i);

                // close this activity

                finish();
            }
        });
        if (CheckInternetConnection.isOnline(SplashActivity.this)) {

            if (sessionManager.fetchCountryName() == null && sessionManager.fetchCountryCode() == null)
                callApi();
            else {
                updateUi();
                Log.d(TAG, "Location Added to session manager");
            }
        } else {
            showNoInternetDialog();
        }
        if (sessionManager.fetchIsSecond()) {

            if (sessionManager.fetchIsRated()) {
                Log.d(TAG, "Rated already");
            } else {
                provide.showAlertDialog();
            }
        } else {
            Log.d(TAG, "NO dialog to show");
            sessionManager.saveIsSecond(true);
        }

//
    }


    private void updateUi() {
        if (sessionManager.fetchCountryName() != null) {


            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {


                    binding.btnGetStarted.setVisibility(View.VISIBLE);

                }


            }, 5 * 1000); // wait for 5 seconds
        }
    }

    private void callApi() {
        GetData service = ReterofitClient.getRetrofitInstance().create(GetData.class);
        Call<Location> call = service.getLocation(sessionManager.fetchIP());
        call.enqueue(new Callback<Location>() {

            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {

                Location data = response.body();
                assert data != null;

                if (data.getStatus().equals("success")) {
                    Log.d(TAG, data.getCountryCode());
                    Log.d(TAG, data.getStatus());
                    sessionManager.saveCountryCode(data.getCountryCode());
                    sessionManager.saveCountryName(data.getCountry());
                    updateUi();
                } else {
                    Log.d(TAG, data.getStatus());
                    updateUi();
                }

            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Log.d("TAG", t.getLocalizedMessage());
                Toast.makeText(SplashActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showNoInternetDialog() {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });

            alertDialog.show();
        } catch (Exception e) {
            Log.d(TAG, "Show Dialog: " + e.getMessage());
        }
    }


}