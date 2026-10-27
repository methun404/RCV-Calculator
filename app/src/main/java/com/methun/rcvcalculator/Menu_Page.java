package com.methun.rcvcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Menu_Page Activity.
 * Serves as the primary navigation dashboard of RCV Calculator, allowing users
 * to select between "Color to Value" and "Value to Color" calculation modes,
 * as well as submit user feedback.
 */
public class Menu_Page extends AppCompatActivity {

    private static final String FEEDBACK_EMAIL = "techcraftb@gmail.com";
    private static final String EMAIL_SUBJECT = "RCV Calculator Feedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__page);

        RelativeLayout color2Value = findViewById(R.id.c2v);
        RelativeLayout value2Color = findViewById(R.id.v2c);

        // Navigate to Color to Value Calculator
        color2Value.setOnClickListener(view -> {
            Intent intent = new Intent(Menu_Page.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Navigate to Value to Color Calculator
        value2Color.setOnClickListener(view -> {
            Intent intent = new Intent(Menu_Page.this, Value_to_Color.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Dispatches an email Intent for user feedback.
     */
    private void sendFeedbackEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{FEEDBACK_EMAIL});
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.setType("message/rfc822");

        try {
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, EMAIL_SUBJECT));
        } catch (Exception e) {
            try {
                startActivity(Intent.createChooser(intent, EMAIL_SUBJECT));
            } catch (Exception fallbackEx) {
                Toast.makeText(this, "No email client found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Backward-compatible alias for sendFeedbackEmail
     */
    private void emil() {
        sendFeedbackEmail();
    }

    /**
     * Displays the user feedback dialog.
     * Invoked via XML onClick attribute.
     *
     * @param view Triggering view
     */
    public void dialog(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(Menu_Page.this, R.style.MyDialogTheme);
        alert.setTitle("Feedback");
        alert.setIcon(R.drawable.ic_baseline_feedback_24);
        alert.setMessage("Do you have any suggestions or feedback? We would love to hear that.");
        alert.setCancelable(true);
        alert.setPositiveButton("Give Feedback", (dialogInterface, i) -> sendFeedbackEmail());
        alert.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}