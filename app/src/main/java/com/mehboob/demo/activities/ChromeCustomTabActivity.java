package com.mehboob.demo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.mehboob.demo.DeviceIp;
import com.mehboob.demo.Provide;
import com.mehboob.demo.R;
import com.mehboob.demo.databinding.ActivityChromeCustomTab2Binding;
import com.mehboob.demo.session.SessionManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class ChromeCustomTabActivity extends AppCompatActivity {

    ActivityChromeCustomTab2Binding binding;
    private String url = "https://huaylike.net/";

    private static final String TAG = "ChromeCustomTabActivity";

    private boolean isClicked =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChromeCustomTab2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

        // below line is setting toolbar color
        // for our custom chrome tab.
        customIntent.setToolbarColor(ContextCompat.getColor(ChromeCustomTabActivity.this, R.color.purple_200));

        // we are calling below method after
        // setting our toolbar color.
        openCustomTab(ChromeCustomTabActivity.this, customIntent.build(), Uri.parse(url));


        binding.txtWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

                // below line is setting toolbar color
                // for our custom chrome tab.
                customIntent.setToolbarColor(ContextCompat.getColor(ChromeCustomTabActivity.this, R.color.purple_200));

                // we are calling below method after
                // setting our toolbar color.
                openCustomTab(ChromeCustomTabActivity.this, customIntent.build(), Uri.parse(url));
            }
        });

    }
    public void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);


            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);

        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG,"On start");
    }
}