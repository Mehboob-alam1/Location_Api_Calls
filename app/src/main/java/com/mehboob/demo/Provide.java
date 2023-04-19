package com.mehboob.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.mehboob.demo.session.SessionManager;


public class Provide {

    private Context context;
    private ReviewManager reviewManager;
    private Activity activity;
    private SessionManager sessionManager;
    private    AlertDialog alert;

    public Provide(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        reviewManager = ReviewManagerFactory.create(context);
        sessionManager = new SessionManager(context);
    }

    public void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        final View customLayout = LayoutInflater.from(context).inflate(R.layout.rate_us_layout, null);

        alertDialog.setView(customLayout);

        // send data from the AlertDialog to the Activity
        ImageView cancel = customLayout.findViewById(R.id.dialog_cancel);
        RatingBar ratingBar = customLayout.findViewById(R.id.ratingBar);


        AppCompatButton button = customLayout.findViewById(R.id.btnRateUs);

        button.setOnClickListener(view -> {
            sessionManager.saveIsRated(true);
            alert.cancel();
            try {
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName() + "");
                Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(goMarket);
            } catch (ActivityNotFoundException e) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "");
                Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(goMarket);
            }


//showRateApp();

        });

       alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });

    }

    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Getting the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(activity, reviewInfo);

                flow.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
//                        if (task.isComplete() && task.isSuccessful()) {
//                            Toast.makeText(context, "Reviewed", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "Not reviewed", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });
            }
        });
    }
}
