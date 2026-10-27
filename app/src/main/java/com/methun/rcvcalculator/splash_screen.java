package com.methun.rcvcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Splash Screen Activity.
 * Displays a branded intro screen for 2 seconds before navigating to the main menu.
 */
public class splash_screen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Delayed navigation using Main Looper
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(splash_screen.this, Menu_Page.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT_MS);
    }
}