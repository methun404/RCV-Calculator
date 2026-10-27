package com.methun.rcvcalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;


/**
 * MainActivity - Color to Value Resistor Calculator.
 *
 * Provides an interactive UI to calculate the resistance of 4-band, 5-band,
 * and 6-band through-hole resistors. As users select color bands from visual
 * spinners, the virtual resistor graphic updates its colors in real-time,
 * and the exact calculated resistance (Ohms, kΩ, MΩ, GΩ), tolerance (±%),
 * and temperature coefficient (ppm/°C) are displayed dynamically.
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout first_band, second_band, third_band, multiplier_band, tolerance_band, temperature_band;

    // Unity Ads Configuration
    private final String GameID = "4655191";
    private final String bannerPlacement = "Banner_Android";
    private final boolean testMood = false;

    private int band_mode = 0;
    private ImageView moreButton;
    private View band4_layout;
    private View band5_layout;
    private View band6_layout;
    private TextView display_value;
    private int def = 0;

    private int first_4b_value, second_4b_value;
    private float multiplier_4b_value;
    private String tolerance_4b_value = "±5%";

    private int first_5b_value, second_5b_value, third_5b_value;
    private float multiplier_5b_value;

    private int first_6b_value, second_6b_value, third_6b_value;
    private float multiplier_6b_value;
    private String tempture_6b_val = "@5ppm/°C%";

    private Spinner first_b_spinner;
    private Spinner second_b_spinner;
    private Spinner third_b_spinner;
    private Spinner multiplier_b_spinner;
    private Spinner tolerance_b_spinner;
    private Spinner temperature_b_spinner;

    private View first_4b, second_4b, multiple_4b, toletance_4b;
    private View first_5b, second_5b, third_5b, multiple_5b, toletance_5b;
    private View first_6b, second_6b, third_6b, multiple_6b, toletance_6b, temperature_6b;

    private String first_b_selected_color = "Brown";
    private String second_b_selected_color = "Black";
    private String third_b_selected_color = "Black";
    private String multiplier_b_selected_color = "Black";
    private String tolerance_b_selected_color = "Brown";
    private String temperature_b_selected_color = "Black";

    // Resistor Color Definitions
    private final String[] first_band_colorN = {"Brown", "Red", "Orange", "Yellow", "Green", "Blue", "Violet", "Grey", "White"};
    private final String[] first_band_colorV = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final int[] first_band_colorImg = {R.drawable.brown_img, R.drawable.red_img, R.drawable.orange_img, R.drawable.yellow_img, R.drawable.green_img,
            R.drawable.blue_img, R.drawable.violet_img, R.drawable.grey_img, R.drawable.white_img};

    private final String[] second_third_band_colorN = {"Black", "Brown", "Red", "Orange", "Yellow", "Green", "Blue", "Violet", "Grey", "White"};
    private final String[] second_third_band_colorV = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final int[] second_third_band_colorImg = {R.drawable.black_img, R.drawable.brown_img, R.drawable.red_img, R.drawable.orange_img, R.drawable.yellow_img,
            R.drawable.green_img, R.drawable.blue_img, R.drawable.violet_img, R.drawable.grey_img, R.drawable.white_img};

    private final String[] multiplier_band_colorN = {"Black", "Brown", "Red", "Orange", "Yellow", "Green", "Blue", "Violet", "Grey", "White", "Gold", "Silver"};
    private final String[] multiplier_band_colorV = {"1", "10", "100", "1k", "10k", "100k", "1M", "10M", "100M", "1G", "0.1", "0.01"};
    private final int[] multiplier_band_colorImg = {R.drawable.black_img, R.drawable.brown_img, R.drawable.red_img, R.drawable.orange_img, R.drawable.yellow_img,
            R.drawable.green_img, R.drawable.blue_img, R.drawable.violet_img, R.drawable.grey_img, R.drawable.white_img, R.drawable.gold_img, R.drawable.silver_img};

    private final String[] tolerance_band_colorN = {"Brown", "Red", "Orange", "Yellow", "Green", "Blue", "Violet", "Grey", "Gold", "Silver", "None"};
    private final String[] tolerance_band_colorV = {"±1%", "±2%", "±0.05%", "±0.02%", "±0.5%", "±0.25%", "±0.1%", "±0.01%", "±5%", "±10%", "±20%"};
    private final int[] tolerance_band_colorImg = {R.drawable.brown_img, R.drawable.red_img, R.drawable.orange_img, R.drawable.yellow_img,
            R.drawable.green_img, R.drawable.blue_img, R.drawable.violet_img, R.drawable.grey_img, R.drawable.gold_img, R.drawable.silver_img, R.drawable.white_img};

    private final String[] temperature_band_colorN = {"Black", "Brown", "Red", "Orange", "Yellow", "Green", "Blue", "Violet", "Grey"};
    private final String[] temperature_band_colorV = {"250ppm/°C", "100ppm/°C", "50ppm/°C", "15ppm/°C", "25ppm/°C", "20ppm/°C", "10ppm/°C", "5ppm/°C", "1ppm/°C"};
    private final int[] temperature_band_colorImg = {R.drawable.black_img, R.drawable.brown_img, R.drawable.red_img, R.drawable.orange_img, R.drawable.yellow_img,
            R.drawable.green_img, R.drawable.blue_img, R.drawable.violet_img, R.drawable.grey_img};

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Unity Banner Ads
        UnityAds.initialize(this, GameID, testMood);

        IUnityBannerListener bannerListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String s, View view) {
                ((ViewGroup) findViewById(R.id.cvAdsL)).removeView(view);
                ((ViewGroup) findViewById(R.id.cvAdsL)).addView(view);
            }

            @Override
            public void onUnityBannerUnloaded(String s) {
            }

            @Override
            public void onUnityBannerShow(String s) {
            }

            @Override
            public void onUnityBannerClick(String s) {
            }

            @Override
            public void onUnityBannerHide(String s) {
            }

            @Override
            public void onUnityBannerError(String s) {
                UnityBanners.destroy();
                Handler handler = new Handler();
                handler.postDelayed(() -> UnityBanners.loadBanner(MainActivity.this, bannerPlacement), 5000);
            }
        };
        UnityBanners.setBannerListener(bannerListener);
        UnityBanners.loadBanner(this, bannerPlacement);

        RadioGroup group = findViewById(R.id.radioGroup);
        display_value = findViewById(R.id.vlu);
        band4_layout = findViewById(R.id.b4_lay);
        band5_layout = findViewById(R.id.b5_lay);
        band6_layout = findViewById(R.id.b6_lay);
        moreButton = findViewById(R.id.more_icon);
        first_band = findViewById(R.id.fst_b);
        second_band = findViewById(R.id.snd_b);
        third_band = findViewById(R.id.trd_b);
        multiplier_band = findViewById(R.id.mlt_b);
        tolerance_band = findViewById(R.id.tlr_b);
        temperature_band = findViewById(R.id.tmp_b);



        first_4b=findViewById(R.id.first_4b);
        second_4b=findViewById(R.id.second_4b);
        multiple_4b=findViewById(R.id.multiple_4b);
        toletance_4b=findViewById(R.id.tolarence_4b);

        first_5b=findViewById(R.id.first_5b);
        second_5b=findViewById(R.id.second_5b);
        third_5b=findViewById(R.id.third_5b);
        multiple_5b=findViewById(R.id.multiple_5b);
        toletance_5b=findViewById(R.id.tolarence_5b);

        first_6b=findViewById(R.id.first_6b);
        second_6b=findViewById(R.id.second_6b);
        third_6b=findViewById(R.id.third_6b);
        multiple_6b=findViewById(R.id.multiple_6b);
        toletance_6b=findViewById(R.id.tolarence_6b);
        temperature_6b=findViewById(R.id.temperature_6b);

        group.check(R.id.radioButton1);
      //  default_spinnerValue();

        first_b_spinner = findViewById(R.id.spn);
         second_b_spinner = findViewById(R.id.spn1);
         third_b_spinner = findViewById(R.id.spn2);
         multiplier_b_spinner = findViewById(R.id.spn3);
         tolerance_b_spinner = findViewById(R.id.spn4);
         temperature_b_spinner = findViewById(R.id.spn5);
        spinnner_adaptor first_b_spinner_adaptor=new spinnner_adaptor(this,first_band_colorN,first_band_colorV,first_band_colorImg);
        first_b_spinner.setAdapter(first_b_spinner_adaptor);
        spinnner_adaptor second_third_b_spinner_adaptor=new spinnner_adaptor(this,second_third_band_colorN,second_third_band_colorV,second_third_band_colorImg);
        second_b_spinner.setAdapter(second_third_b_spinner_adaptor);
        third_b_spinner.setAdapter(second_third_b_spinner_adaptor);
        spinnner_adaptor multiplier_b_spinner_adaptor=new spinnner_adaptor(this,multiplier_band_colorN,multiplier_band_colorV,multiplier_band_colorImg);
        multiplier_b_spinner.setAdapter(multiplier_b_spinner_adaptor);
        spinnner_adaptor tolerance_b_spinner_adaptor=new spinnner_adaptor(this,tolerance_band_colorN,tolerance_band_colorV,tolerance_band_colorImg);
        tolerance_b_spinner.setAdapter(tolerance_b_spinner_adaptor);
        spinnner_adaptor temperature_b_spinner_adaptor=new spinnner_adaptor(this,temperature_band_colorN,temperature_band_colorV,temperature_band_colorImg);
        temperature_b_spinner.setAdapter(temperature_b_spinner_adaptor);

        group.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i){
                case R.id.radioButton1:
                    band4_layout.setVisibility(View.VISIBLE);
                    band5_layout.setVisibility(View.GONE);
                    band6_layout.setVisibility(View.GONE);

                    first_band.setVisibility(View.VISIBLE);
                    second_band.setVisibility(View.VISIBLE);
                    third_band.setVisibility(View.GONE);
                    multiplier_band.setVisibility(View.VISIBLE);
                    tolerance_band.setVisibility(View.VISIBLE);
                    temperature_band.setVisibility(View.GONE);
                  band_mode=4;
                  color_Change();
                    break;
                case R.id.radioButton2:
                    band4_layout.setVisibility(View.GONE);
                    band5_layout.setVisibility(View.VISIBLE);
                    band6_layout.setVisibility(View.GONE);

                    first_band.setVisibility(View.VISIBLE);
                    second_band.setVisibility(View.VISIBLE);
                    third_band.setVisibility(View.VISIBLE);
                    multiplier_band.setVisibility(View.VISIBLE);
                    tolerance_band.setVisibility(View.VISIBLE);
                    temperature_band.setVisibility(View.GONE);
                    band_mode=5;
                    color_Change();
                    break;
                case R.id.radioButton3:
                    band4_layout.setVisibility(View.GONE);
                    band5_layout.setVisibility(View.GONE);
                    band6_layout.setVisibility(View.VISIBLE);

                    first_band.setVisibility(View.VISIBLE);
                    second_band.setVisibility(View.VISIBLE);
                    third_band.setVisibility(View.VISIBLE);
                    multiplier_band.setVisibility(View.VISIBLE);
                    tolerance_band.setVisibility(View.VISIBLE);
                    temperature_band.setVisibility(View.VISIBLE);
                    band_mode=6;
                    color_Change();
                    break;
            }
        });

        moreButton.setOnClickListener(view -> {
            PopupMenu popup=new PopupMenu(MainActivity.this,moreButton);
            popup.getMenuInflater().inflate(R.menu.more_option,popup.getMenu());
            popup.show();

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.av:
                            vc();
                            break;
                        case R.id.st:
                            txsC();
                            break;
                        case R.id.fq:
                            emilfb();
                            break;
                        case R.id.exit2:
                            finish();
                    }
                    return false;
                }
            });

        });

        first_b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                first_b_selected_color=first_band_colorN[i];
                color_Change();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        second_b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                second_b_selected_color=second_third_band_colorN[i];
                color_Change();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        third_b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                third_b_selected_color=second_third_band_colorN[i];
                color_Change();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        multiplier_b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                multiplier_b_selected_color=multiplier_band_colorN[i];
                color_Change();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tolerance_b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tolerance_b_selected_color=tolerance_band_colorN[i];
                tolerance_4b_value=tolerance_band_colorV[i];

                color_Change();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        temperature_b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                temperature_b_selected_color=temperature_band_colorN[i];
                tempture_6b_val="@"+temperature_band_colorV[i];
                color_Change();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // 4band spinner perfome click
        first_4b.setOnClickListener(view -> {first_b_spinner.performClick();});
        second_4b.setOnClickListener(view -> {second_b_spinner.performClick();});
        multiple_4b.setOnClickListener(view -> {multiplier_b_spinner.performClick();});
        toletance_4b.setOnClickListener(view -> {tolerance_b_spinner.performClick();});

        // 5band spinner perfome click
        first_5b.setOnClickListener(view -> {first_b_spinner.performClick();});
        second_5b.setOnClickListener(view -> {second_b_spinner.performClick();});
        third_5b.setOnClickListener(view -> {third_b_spinner.performClick();});
        multiple_5b.setOnClickListener(view -> {multiplier_b_spinner.performClick();});
        toletance_5b.setOnClickListener(view -> {tolerance_b_spinner.performClick();});

        // 6band spinner perfome click
        first_6b.setOnClickListener(view -> {first_b_spinner.performClick();});
        second_6b.setOnClickListener(view -> {second_b_spinner.performClick();});
        third_6b.setOnClickListener(view -> {third_b_spinner.performClick();});
        multiple_6b.setOnClickListener(view -> {multiplier_b_spinner.performClick();});
        toletance_6b.setOnClickListener(view -> {tolerance_b_spinner.performClick();});
        temperature_6b.setOnClickListener(view -> {temperature_b_spinner.performClick();});

    }
    private void color_Change() {

        if (band_mode == 0 || band_mode == 4) {
            switch (first_b_selected_color.toLowerCase()) {
                case "brown":
                    first_4b.setBackgroundResource(R.color.brown);
                    first_4b_value = 1;
                    break;
                case "red":
                    first_4b.setBackgroundResource(R.color.red);
                    first_4b_value = 2;
                    break;
                case "orange":
                    first_4b.setBackgroundResource(R.color.orange);
                    first_4b_value = 3;
                    break;
                case "yellow":
                    first_4b.setBackgroundResource(R.color.yellow);
                    first_4b_value = 4;
                    break;
                case "green":
                    first_4b.setBackgroundResource(R.color.green);
                    first_4b_value = 5;
                    break;
                case "blue":
                    first_4b.setBackgroundResource(R.color.blue);
                    first_4b_value = 6;
                    break;
                case "violet":
                    first_4b.setBackgroundResource(R.color.violet);
                    first_4b_value = 7;
                    break;
                case "grey":
                    first_4b.setBackgroundResource(R.color.grey);
                    first_4b_value = 8;
                    break;
                case "white":
                    first_4b.setBackgroundResource(R.drawable.white_img);
                    first_4b_value = 9;
                    break;

            }
            switch (second_b_selected_color.toLowerCase()) {
                case "black":
                    second_4b.setBackgroundResource(R.drawable.black_img);
                    second_4b_value = 0;
                    break;
                case "brown":
                    second_4b.setBackgroundResource(R.color.brown);
                    second_4b_value = 1;
                    break;
                case "red":
                    second_4b.setBackgroundResource(R.color.red);
                    second_4b_value = 2;
                    break;
                case "orange":
                    second_4b.setBackgroundResource(R.color.orange);
                    second_4b_value = 3;
                    break;
                case "yellow":
                    second_4b.setBackgroundResource(R.color.yellow);
                    second_4b_value = 4;
                    break;
                case "green":
                    second_4b.setBackgroundResource(R.color.green);
                    second_4b_value = 5;
                    break;
                case "blue":
                    second_4b.setBackgroundResource(R.color.blue);
                    second_4b_value = 6;
                    break;
                case "violet":
                    second_4b.setBackgroundResource(R.color.violet);
                    second_4b_value = 7;
                    break;
                case "grey":
                    second_4b.setBackgroundResource(R.color.grey);
                    second_4b_value = 8;
                    break;
                case "white":
                    second_4b.setBackgroundResource(R.drawable.white_img);
                    second_4b_value = 9;
                    break;

            }
            switch (multiplier_b_selected_color.toLowerCase()) {
                case "black":
                    multiple_4b.setBackgroundResource(R.drawable.black_img);
                    multiplier_4b_value = 0;
                    break;
                case "brown":
                    multiple_4b.setBackgroundResource(R.color.brown);
                    multiplier_4b_value = 1;
                    break;
                case "red":
                    multiple_4b.setBackgroundResource(R.color.red);
                    multiplier_4b_value = 2;
                    break;
                case "orange":
                    multiple_4b.setBackgroundResource(R.color.orange);
                    multiplier_4b_value = 3;
                    break;
                case "yellow":
                    multiple_4b.setBackgroundResource(R.color.yellow);
                    multiplier_4b_value = 4;
                    break;
                case "green":
                    multiple_4b.setBackgroundResource(R.color.green);
                    multiplier_4b_value = 5;
                    break;
                case "blue":
                    multiple_4b.setBackgroundResource(R.color.blue);
                    multiplier_4b_value = 6;
                    break;
                case "violet":
                    multiple_4b.setBackgroundResource(R.color.violet);
                    multiplier_4b_value = 7;
                    break;
                case "grey":
                    multiple_4b.setBackgroundResource(R.color.grey);
                    multiplier_4b_value = 8;
                    break;
                case "white":
                    multiple_4b.setBackgroundResource(R.drawable.white_img);
                    multiplier_4b_value = 9;
                    break;
                case "gold":
                    multiple_4b.setBackgroundResource(R.drawable.clr);
                    multiplier_4b_value = -1;
                    break;
                case "silver":
                    multiple_4b.setBackgroundResource(R.color.silver);
                    multiplier_4b_value = -2;
                    break;

            }
            switch (tolerance_b_selected_color.toLowerCase()) {
                case "brown":
                    toletance_4b.setBackgroundResource(R.color.brown);
                    break;
                case "red":
                    toletance_4b.setBackgroundResource(R.color.red);
                    break;
                case "orange":
                    toletance_4b.setBackgroundResource(R.color.orange);
                    break;
                case "yellow":
                    toletance_4b.setBackgroundResource(R.color.yellow);
                    break;
                case "green":
                    toletance_4b.setBackgroundResource(R.color.green);
                    break;
                case "blue":
                    toletance_4b.setBackgroundResource(R.color.blue);
                    break;
                case "violet":
                    toletance_4b.setBackgroundResource(R.color.violet);
                    break;
                case "grey":
                    toletance_4b.setBackgroundResource(R.color.grey);
                    break;
                case "gold":
                    toletance_4b.setBackgroundResource(R.drawable.clr);
                    break;
                case "silver":
                    toletance_4b.setBackgroundResource(R.color.silver);
                    break;
                case "none":
                    toletance_4b.setBackgroundResource(R.color.res_body);
                    break;


            }

        }
        if (band_mode == 5) {
            switch (first_b_selected_color.toLowerCase()) {
                case "brown":
                    first_5b.setBackgroundResource(R.color.brown);
                    first_5b_value=1;
                    break;
                case "red":
                    first_5b.setBackgroundResource(R.color.red);
                    first_5b_value=2;
                    break;
                case "orange":
                    first_5b.setBackgroundResource(R.color.orange);
                    first_5b_value=3;
                    break;
                case "yellow":
                    first_5b.setBackgroundResource(R.color.yellow);
                    first_5b_value=4;
                    break;
                case "green":
                    first_5b.setBackgroundResource(R.color.green);
                    first_5b_value=5;
                    break;
                case "blue":
                    first_5b.setBackgroundResource(R.color.blue);
                    first_5b_value=6;
                    break;
                case "violet":
                    first_5b.setBackgroundResource(R.color.violet);
                    first_5b_value=7;
                    break;
                case "grey":
                    first_5b.setBackgroundResource(R.color.grey);
                    first_5b_value=8;
                    break;
                case "white":
                    first_5b.setBackgroundResource(R.drawable.white_img);
                    first_5b_value=9;
                    break;

            }
            switch (second_b_selected_color.toLowerCase()) {
                case "black":
                    second_5b.setBackgroundResource(R.drawable.black_img);
                    second_5b_value=0;
                    break;
                case "brown":
                    second_5b.setBackgroundResource(R.color.brown);
                    second_5b_value=1;
                    break;
                case "red":
                    second_5b.setBackgroundResource(R.color.red);
                    second_5b_value=2;
                    break;
                case "orange":
                    second_5b.setBackgroundResource(R.color.orange);
                    second_5b_value=3;
                    break;
                case "yellow":
                    second_5b.setBackgroundResource(R.color.yellow);
                    second_5b_value=4;
                    break;
                case "green":
                    second_5b.setBackgroundResource(R.color.green);
                    second_5b_value=5;
                    break;
                case "blue":
                    second_5b.setBackgroundResource(R.color.blue);
                    second_5b_value=6;
                    break;
                case "violet":
                    second_5b.setBackgroundResource(R.color.violet);
                    second_5b_value=7;
                    break;
                case "grey":
                    second_5b.setBackgroundResource(R.color.grey);
                    second_5b_value=8;
                    break;
                case "white":
                    second_5b.setBackgroundResource(R.drawable.white_img);
                    second_5b_value=9;
                    break;

            }
            switch (third_b_selected_color.toLowerCase()) {
                case "black":
                    third_5b.setBackgroundResource(R.drawable.black_img);
                    third_5b_value=0;
                    break;
                case "brown":
                    third_5b.setBackgroundResource(R.color.brown);
                    third_5b_value=1;
                    break;
                case "red":
                    third_5b.setBackgroundResource(R.color.red);
                    third_5b_value=2;
                    break;
                case "orange":
                    third_5b.setBackgroundResource(R.color.orange);
                    third_5b_value=3;
                    break;
                case "yellow":
                    third_5b.setBackgroundResource(R.color.yellow);
                    third_5b_value=4;
                    break;
                case "green":
                    third_5b.setBackgroundResource(R.color.green);
                    third_5b_value=5;
                    break;
                case "blue":
                    third_5b.setBackgroundResource(R.color.blue);
                    third_5b_value=6;
                    break;
                case "violet":
                    third_5b.setBackgroundResource(R.color.violet);
                    third_5b_value=7;
                    break;
                case "grey":
                    third_5b.setBackgroundResource(R.color.grey);
                    third_5b_value=8;
                    break;
                case "white":
                    third_5b.setBackgroundResource(R.drawable.white_img);
                    third_5b_value=9;
                    break;

            }
            switch (multiplier_b_selected_color.toLowerCase()) {
                case "black":
                    multiple_5b.setBackgroundResource(R.drawable.black_img);
                    multiplier_5b_value=0;
                    break;
                case "brown":
                    multiple_5b.setBackgroundResource(R.color.brown);
                    multiplier_5b_value=1;
                    break;
                case "red":
                    multiple_5b.setBackgroundResource(R.color.red);
                    multiplier_5b_value=2;
                    break;
                case "orange":
                    multiple_5b.setBackgroundResource(R.color.orange);
                    multiplier_5b_value=3;
                    break;
                case "yellow":
                    multiple_5b.setBackgroundResource(R.color.yellow);
                    multiplier_5b_value=4;
                    break;
                case "green":
                    multiple_5b.setBackgroundResource(R.color.green);
                    multiplier_5b_value=5;
                    break;
                case "blue":
                    multiple_5b.setBackgroundResource(R.color.blue);
                    multiplier_5b_value=6;
                    break;
                case "violet":
                    multiple_5b.setBackgroundResource(R.color.violet);
                    multiplier_5b_value=7;
                    break;
                case "grey":
                    multiple_5b.setBackgroundResource(R.color.grey);
                    multiplier_5b_value=8;
                    break;
                case "white":
                    multiple_5b.setBackgroundResource(R.drawable.white_img);
                    multiplier_5b_value=9;
                    break;
                case "gold":
                    multiple_5b.setBackgroundResource(R.drawable.clr);
                    multiplier_5b_value=-1;
                    break;
                case "silver":
                    multiple_5b.setBackgroundResource(R.color.silver);
                    multiplier_5b_value=-2;
                    break;

            }
            switch (tolerance_b_selected_color.toLowerCase()) {
                case "brown":
                    toletance_5b.setBackgroundResource(R.color.brown);
                    break;
                case "red":
                    toletance_5b.setBackgroundResource(R.color.red);
                    break;
                case "orange":
                    toletance_5b.setBackgroundResource(R.color.orange);
                    break;
                case "yellow":
                    toletance_5b.setBackgroundResource(R.color.yellow);
                    break;
                case "green":
                    toletance_5b.setBackgroundResource(R.color.green);
                    break;
                case "blue":
                    toletance_5b.setBackgroundResource(R.color.blue);
                    break;
                case "violet":
                    toletance_5b.setBackgroundResource(R.color.violet);
                    break;
                case "grey":
                    toletance_5b.setBackgroundResource(R.color.grey);
                    break;
                case "gold":
                    toletance_5b.setBackgroundResource(R.drawable.clr);
                    break;
                case "silver":
                    toletance_5b.setBackgroundResource(R.color.silver);
                    break;
                case "none":
                    toletance_5b.setBackgroundResource(R.color.res_body);
                    break;


            }
        }
        if (band_mode == 6) {
            switch (first_b_selected_color.toLowerCase()) {
                case "brown":
                    first_6b.setBackgroundResource(R.color.brown);
                    first_6b_value=1;
                    break;
                case "red":
                    first_6b.setBackgroundResource(R.color.red);
                    first_6b_value=2;
                    break;
                case "orange":
                    first_6b.setBackgroundResource(R.color.orange);
                    first_6b_value=3;
                    break;
                case "yellow":
                    first_6b.setBackgroundResource(R.color.yellow);
                    first_6b_value=4;
                    break;
                case "green":
                    first_6b.setBackgroundResource(R.color.green);
                    first_6b_value=5;
                    break;
                case "blue":
                    first_6b.setBackgroundResource(R.color.blue);
                    first_6b_value=6;
                    break;
                case "violet":
                    first_6b.setBackgroundResource(R.color.violet);
                    first_6b_value=7;
                    break;
                case "grey":
                    first_6b.setBackgroundResource(R.color.grey);
                    first_6b_value=8;
                    break;
                case "white":
                    first_6b.setBackgroundResource(R.drawable.white_img);
                    first_6b_value=9;
                    break;

            }
            switch (second_b_selected_color.toLowerCase()) {
                case "black":
                    second_6b.setBackgroundResource(R.drawable.black_img);
                    second_6b_value=0;
                    break;
                case "brown":
                    second_6b.setBackgroundResource(R.color.brown);
                    second_6b_value=1;
                    break;
                case "red":
                    second_6b.setBackgroundResource(R.color.red);
                    second_6b_value=2;
                    break;
                case "orange":
                    second_6b.setBackgroundResource(R.color.orange);
                    second_6b_value=3;
                    break;
                case "yellow":
                    second_6b.setBackgroundResource(R.color.yellow);
                    second_6b_value=4;
                    break;
                case "green":
                    second_6b.setBackgroundResource(R.color.green);
                    second_6b_value=5;
                    break;
                case "blue":
                    second_6b.setBackgroundResource(R.color.blue);
                    second_6b_value=6;
                    break;
                case "violet":
                    second_6b.setBackgroundResource(R.color.violet);
                    second_6b_value=7;
                    break;
                case "grey":
                    second_6b.setBackgroundResource(R.color.grey);
                    second_6b_value=8;
                    break;
                case "white":
                    second_6b.setBackgroundResource(R.drawable.white_img);
                    second_6b_value=9;
                    break;

            }
            switch (third_b_selected_color.toLowerCase()) {
                case "black":
                    third_6b.setBackgroundResource(R.drawable.black_img);
                    third_6b_value=0;
                    break;
                case "brown":
                    third_6b.setBackgroundResource(R.color.brown);
                    third_6b_value=1;
                    break;
                case "red":
                    third_6b.setBackgroundResource(R.color.red);
                    third_6b_value=2;
                    break;
                case "orange":
                    third_6b.setBackgroundResource(R.color.orange);
                    third_6b_value=3;
                    break;
                case "yellow":
                    third_6b.setBackgroundResource(R.color.yellow);
                    third_6b_value=4;
                    break;
                case "green":
                    third_6b.setBackgroundResource(R.color.green);
                    third_6b_value=5;
                    break;
                case "blue":
                    third_6b.setBackgroundResource(R.color.blue);
                    third_6b_value=6;
                    break;
                case "violet":
                    third_6b.setBackgroundResource(R.color.violet);
                    third_6b_value=7;
                    break;
                case "grey":
                    third_6b.setBackgroundResource(R.color.grey);
                    third_6b_value=8;
                    break;
                case "white":
                    third_6b.setBackgroundResource(R.drawable.white_img);
                    third_6b_value=9;
                    break;

            }
            switch (multiplier_b_selected_color.toLowerCase()) {
                case "black":
                    multiple_6b.setBackgroundResource(R.drawable.black_img);
                    multiplier_6b_value=0;
                    break;
                case "brown":
                    multiple_6b.setBackgroundResource(R.color.brown);
                    multiplier_6b_value=1;
                    break;
                case "red":
                    multiple_6b.setBackgroundResource(R.color.red);
                    multiplier_6b_value=2;
                    break;
                case "orange":
                    multiple_6b.setBackgroundResource(R.color.orange);
                    multiplier_6b_value=3;
                    break;
                case "yellow":
                    multiple_6b.setBackgroundResource(R.color.yellow);
                    multiplier_6b_value=4;
                    break;
                case "green":
                    multiple_6b.setBackgroundResource(R.color.green);
                    multiplier_6b_value=5;
                    break;
                case "blue":
                    multiple_6b.setBackgroundResource(R.color.blue);
                    multiplier_6b_value=6;
                    break;
                case "violet":
                    multiple_6b.setBackgroundResource(R.color.violet);
                    multiplier_6b_value=7;
                    break;
                case "grey":
                    multiple_6b.setBackgroundResource(R.color.grey);
                    multiplier_6b_value=8;
                    break;
                case "white":
                    multiple_6b.setBackgroundResource(R.drawable.white_img);
                    multiplier_6b_value=9;
                    break;
                case "gold":
                    multiple_6b.setBackgroundResource(R.drawable.clr);
                    multiplier_6b_value=-1;
                    break;
                case "silver":
                    multiple_6b.setBackgroundResource(R.color.silver);
                    multiplier_6b_value=-2;
                    break;

            }
            switch (tolerance_b_selected_color.toLowerCase()) {
                case "brown":
                    toletance_6b.setBackgroundResource(R.color.brown);
                    break;
                case "red":
                    toletance_6b.setBackgroundResource(R.color.red);
                    break;
                case "orange":
                    toletance_6b.setBackgroundResource(R.color.orange);
                    break;
                case "yellow":
                    toletance_6b.setBackgroundResource(R.color.yellow);
                    break;
                case "green":
                    toletance_6b.setBackgroundResource(R.color.green);
                    break;
                case "blue":
                    toletance_6b.setBackgroundResource(R.color.blue);
                    break;
                case "violet":
                    toletance_6b.setBackgroundResource(R.color.violet);
                    break;
                case "grey":
                    toletance_6b.setBackgroundResource(R.color.grey);
                    break;
                case "gold":
                    toletance_6b.setBackgroundResource(R.drawable.clr);
                    break;
                case "silver":
                    toletance_6b.setBackgroundResource(R.color.silver);
                    break;
                case "none":
                    toletance_6b.setBackgroundResource(R.color.res_body);
                    break;
            }
            switch (temperature_b_selected_color.toLowerCase()) {
                case "black":
                    temperature_6b.setBackgroundResource(R.drawable.black_img);
                    break;
                case "brown":
                    temperature_6b.setBackgroundResource(R.color.brown);
                    break;
                case "red":
                    temperature_6b.setBackgroundResource(R.color.red);
                    break;
                case "orange":
                    temperature_6b.setBackgroundResource(R.color.orange);
                    break;
                case "yellow":
                    temperature_6b.setBackgroundResource(R.color.yellow);
                    break;
                case "green":
                    temperature_6b.setBackgroundResource(R.color.green);
                    break;
                case "blue":
                    temperature_6b.setBackgroundResource(R.color.blue);
                    break;
                case "violet":
                    temperature_6b.setBackgroundResource(R.color.violet);
                    break;
                case "grey":
                    temperature_6b.setBackgroundResource(R.color.grey);
                    break;
                case "gold":
                    temperature_6b.setBackgroundResource(R.drawable.clr);
                    break;
                case "silver":
                    temperature_6b.setBackgroundResource(R.color.silver);
                    break;
                case "none":
                    temperature_6b.setBackgroundResource(R.color.res_body);
                    break;
            }

        }
        value_calculation();
    }

    @Override
    protected void onStart() {
        if (def==0){
            tolerance_b_spinner.setSelection(8);
            temperature_b_spinner.setSelection(7);
            def=1;
        }
        super.onStart();
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void value_calculation(){
      if (band_mode==0 || band_mode==4) {


          String f_value = first_4b_value + String.valueOf(second_4b_value);
          int val_4b = Integer.parseInt(f_value);

          double pow = Math.pow(10, multiplier_4b_value);
          double cal = val_4b * pow;
          if (cal<1000){

                  display_value.setText(""+(int)cal+"Ω"+" "+tolerance_4b_value);

          }
          if (cal>=1000&&cal<1000000){
              double k_cal=cal/1000;
              if (multiplier_4b_value==2&&second_4b_value!=0){
                   display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value);
               }
               if (multiplier_4b_value==2 && second_4b_value==0){
                   display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value);
               }
               if (multiplier_4b_value==3 || multiplier_4b_value==4){
                   display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value);
               }
          }
          if (cal>=1000000&&cal<1000000000){
              double m_cal=cal/1000000;
              if (multiplier_4b_value==5&&second_4b_value!=0){
                  display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value);
              }
              if (multiplier_4b_value==5 && second_4b_value==0){
                  display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value);
              }
              if (multiplier_4b_value==6 || multiplier_4b_value==7){
                  display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value);
              }
          }
          if (cal>=1000000000){
              double g_cal=cal/1000000000;
              if (multiplier_4b_value==8&&second_4b_value!=0){
                  display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value);
              }
              if (multiplier_4b_value==8 && second_4b_value==0){
                  display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value);
              }
              if (multiplier_4b_value==9 ){
                  display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value);
              }
          }

          if (multiplier_4b_value==-1){
              String f_value_G = String.valueOf(first_4b_value) + second_4b_value;
              int val_4b_G = Integer.parseInt(f_value_G);
              float g_cal_4= (float) (val_4b_G*0.1);
              if (multiplier_4b_value==-1&&second_4b_value==0){
                  display_value.setText(""+(int)g_cal_4+"Ω"+" "+tolerance_4b_value);
              }
              if (multiplier_4b_value==-1&&second_4b_value!=0){
                  display_value.setText(""+g_cal_4+"Ω"+" "+tolerance_4b_value);
              }
          }
          if (multiplier_4b_value==-2){
              String f_value_Gx = String.valueOf(first_4b_value) + second_4b_value;
              int val_4b_Gx = Integer.parseInt(f_value_Gx);
              float g_cal_4x= (float) (val_4b_Gx*0.01);
              display_value.setText(""+g_cal_4x+"Ω"+" "+tolerance_4b_value);
          }
      }

      if (band_mode==5){


          if (multiplier_5b_value!=-1 && multiplier_5b_value!=-2){
              String f_value_5b = first_5b_value + String.valueOf(second_5b_value)+ third_5b_value;
              int val_4b_5B = Integer.parseInt(f_value_5b);

              double pow = Math.pow(10, multiplier_5b_value);
              double cal_x = val_4b_5B * pow;
              if (cal_x<1000){
                  display_value.setText(""+(int)cal_x+"Ω"+" "+tolerance_4b_value);
              }
              if (cal_x>=1000&&cal_x<1000000){
                  double k_cal=cal_x/1000;
                  if (multiplier_5b_value==1&&second_5b_value!=0&&third_5b_value!=0){
                      display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==1&&second_5b_value==0&&third_5b_value==0){
                      display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==1&&second_5b_value!=0&&third_5b_value==0){
                      display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==1&&second_5b_value==0&&third_5b_value!=0){
                      display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==2&&second_5b_value!=0&&third_5b_value!=0){
                      display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==2 && second_5b_value!=0&&third_5b_value==0){
                      display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==2 && second_4b_value==0&&third_5b_value!=0){
                      display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==2 && second_4b_value==0&&third_5b_value==0){
                      display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==3){
                      display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value);
                  }
              }
              if (cal_x>=1000000&&cal_x<1000000000){
                  double m_cal=cal_x/1000000;
                  if (multiplier_5b_value==4&&second_5b_value!=0&&third_5b_value!=0){
                      display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==4 && second_5b_value!=0&&third_5b_value==0){
                      display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==4 && second_5b_value==0&&third_5b_value!=0){
                      display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==4 && second_5b_value==0&&third_5b_value==0){
                      display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==5 && second_5b_value==0&&third_5b_value==0){
                      display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==5 && second_5b_value!=0&&third_5b_value==0){
                      display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==5 && second_5b_value==0&&third_5b_value!=0){
                      display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==5 && second_5b_value!=0&&third_5b_value!=0){
                      display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==6){
                      display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value);
                  }
              }
              if (cal_x>=1000000000){
                  double g_cal=cal_x/1000000000;
                  if (multiplier_5b_value==7&&second_5b_value!=0&& third_5b_value!=0){
                      display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==7 && second_5b_value==0&& third_5b_value==0){
                      display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==7 && second_5b_value!=0&& third_5b_value==0){
                      display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==7 && second_5b_value==0&& third_5b_value!=0){
                      display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value);
                  }

                  if (multiplier_5b_value==8&&second_5b_value!=0&& third_5b_value!=0){
                      display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==8 && second_5b_value==0&& third_5b_value==0){
                      display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==8 && second_5b_value!=0&& third_5b_value==0){
                      display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==8 && second_5b_value==0&& third_5b_value!=0){
                      display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value);
                  }
                  if (multiplier_5b_value==7 && second_5b_value==0&& third_5b_value!=0){
                      display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value);
                  }

                  if (multiplier_5b_value==9 ){
                      display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value);
                  }
              }
          }
          if (multiplier_5b_value==-1){


              String f_value_5b = String.valueOf(first_5b_value) + second_5b_value + third_5b_value;
              int val_4b_5B = Integer.parseInt(f_value_5b);

              float pow = (float) Math.pow(10, multiplier_5b_value);
              float cal_xc = val_4b_5B * pow;
              if (second_5b_value!=0&&third_5b_value!=0){
                  display_value.setText(""+String.format("%.1f",cal_xc)+"Ω"+" "+tolerance_4b_value);
              }
              if (second_5b_value==0&&third_5b_value==0){
                  display_value.setText(""+(int)cal_xc+"Ω"+" "+tolerance_4b_value);
              }
              if (second_5b_value==0&&third_5b_value!=0){
                  display_value.setText(""+String.format("%.1f",cal_xc)+"Ω"+" "+tolerance_4b_value);
              }
              if (second_5b_value!=0&&third_5b_value==0){
                  display_value.setText(""+(int)cal_xc+"Ω"+" "+tolerance_4b_value);
              }
          }
          if (multiplier_5b_value==-2){
              String f_value_5b = String.valueOf(first_5b_value) + second_5b_value + third_5b_value;
              int val_4b_5B = Integer.parseInt(f_value_5b);

              float pow = (float) Math.pow(10, multiplier_5b_value);
              float cal_xc = val_4b_5B * pow;

              if (second_5b_value!=0&&third_5b_value!=0){
                  display_value.setText(""+String.format("%.2f",cal_xc)+"Ω"+" "+tolerance_4b_value);
              }
              if (second_5b_value==0&&third_5b_value==0){
                  display_value.setText(""+(int)cal_xc+"Ω"+" "+tolerance_4b_value);
              }
              if (second_5b_value==0&&third_5b_value!=0){
                  display_value.setText(""+String.format("%.2f",cal_xc)+"Ω"+" "+tolerance_4b_value);
              }
              if (second_5b_value!=0&&third_5b_value==0){
                  display_value.setText(""+String.format("%.1f",cal_xc)+"Ω"+" "+tolerance_4b_value);
              }

          }



}

        if (band_mode==6){


            if (multiplier_6b_value!=-1 && multiplier_6b_value!=-2){
                String f_value_6b = first_6b_value + String.valueOf(second_6b_value)+ third_6b_value;
                int val_4b_5B = Integer.parseInt(f_value_6b);

                double pow = Math.pow(10, multiplier_6b_value);
                double cal_x = val_4b_5B * pow;
                if (cal_x<1000){
                    display_value.setText(""+(int)cal_x+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }
                if (cal_x>=1000&&cal_x<1000000){
                    double k_cal=cal_x/1000;
                    if (multiplier_6b_value==1&&second_6b_value!=0&&third_6b_value!=0){
                        display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==1&&second_6b_value==0&&third_6b_value==0){
                        display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==1&&second_6b_value!=0&&third_6b_value==0){
                        display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==1&&second_6b_value==0&&third_6b_value!=0){
                        display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==2&&second_6b_value!=0&&third_6b_value!=0){
                        display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==2 && second_6b_value!=0&&third_6b_value==0){
                        display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==2 && second_6b_value==0&&third_6b_value!=0){
                        display_value.setText(""+k_cal+"kΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==2 && second_6b_value==0&&third_6b_value==0){
                        display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==3){
                        display_value.setText(""+(int)k_cal+"kΩ"+" "+tolerance_4b_value);
                    }
                }
                if (cal_x>=1000000&&cal_x<1000000000){
                    double m_cal=cal_x/1000000;
                    if (multiplier_6b_value==4&&second_6b_value!=0&&third_6b_value!=0){
                        display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==4 && second_6b_value!=0&&third_6b_value==0){
                        display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==4 && second_6b_value==0&&third_6b_value!=0){
                        display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==4 && second_6b_value==0&&third_6b_value==0){
                        display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==5 && second_6b_value==0&&third_6b_value==0){
                        display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==5 && second_6b_value!=0&&third_6b_value==0){
                        display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==5 && second_6b_value==0&&third_6b_value!=0){
                        display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==5 && second_6b_value!=0&&third_6b_value!=0){
                        display_value.setText(""+m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==6){
                        display_value.setText(""+(int)m_cal+"MΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                }
                if (cal_x>=1000000000){
                    double g_cal=cal_x/1000000000;
                    if (multiplier_6b_value==7&&second_6b_value!=0&& third_6b_value!=0){
                        display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==7 && second_6b_value==0&& third_6b_value==0){
                        display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==7 && second_6b_value!=0&& third_6b_value==0){
                        display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==7 && second_6b_value==0&& third_6b_value!=0){
                        display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }

                    if (multiplier_6b_value==8&&second_6b_value!=0&& third_6b_value!=0){
                        display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==8 && second_6b_value==0&& third_6b_value==0){
                        display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==8 && second_6b_value!=0&& third_6b_value==0){
                        display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==8 && second_6b_value==0&& third_6b_value!=0){
                        display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                    if (multiplier_6b_value==7 && second_6b_value==0&& third_6b_value!=0){
                        display_value.setText(""+g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }

                    if (multiplier_6b_value==9 ){
                        display_value.setText(""+(int)g_cal+"GΩ"+" "+tolerance_4b_value+" "+tempture_6b_val);
                    }
                }
            }
            if (multiplier_6b_value==-1){


                String f_value_6b = first_6b_value + String.valueOf(second_6b_value)+ third_6b_value;
                int val_4b_6B = Integer.parseInt(f_value_6b);

                float pow = (float) Math.pow(10, multiplier_6b_value);
                float cal_xc = val_4b_6B * pow;
                if (second_6b_value!=0&&third_6b_value!=0){
                    display_value.setText(""+String.format("%.1f",cal_xc)+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }
                if (second_6b_value==0&&third_6b_value==0){
                    display_value.setText(""+(int)cal_xc+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }
                if (second_6b_value==0&&third_6b_value!=0){
                    display_value.setText(""+String.format("%.1f",cal_xc)+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }
                if (second_6b_value!=0&&third_6b_value==0){
                    display_value.setText(""+(int)cal_xc+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }
            }
            if (multiplier_6b_value==-2){
                String f_value_6b = first_6b_value + String.valueOf(second_6b_value)+ third_6b_value;
                int val_4b_6B = Integer.parseInt(f_value_6b);

                float pow = (float) Math.pow(10, multiplier_6b_value);
                float cal_xc = val_4b_6B * pow;

                if (second_6b_value!=0&&third_6b_value!=0){
                    display_value.setText(""+String.format("%.2f",cal_xc)+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }
                if (second_6b_value==0&&third_6b_value==0){
                    display_value.setText(""+(int)cal_xc+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }
                if (second_6b_value==0&&third_6b_value!=0){
                    display_value.setText(""+String.format("%.2f",cal_xc)+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }
                if (second_6b_value!=0&&third_6b_value==0){
                    display_value.setText(""+String.format("%.1f",cal_xc)+"Ω"+" "+tolerance_4b_value+" "+tempture_6b_val);
                }

            }



        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(MainActivity.this,Menu_Page.class);
        startActivity(i);
        this.finish();
    }

    private void vc(){
        Intent i = new Intent(MainActivity.this,Value_to_Color.class);
        startActivity(i);
        this.finish();
    }
    private void txsC(){
        String sx = null;
        if (band_mode==0 || band_mode ==4){
             sx =4+"b" +" - "+ display_value.getText().toString() + "\n"+"["+first_b_selected_color+","+second_b_selected_color+","+multiplier_b_selected_color+","+tolerance_b_selected_color+"]";
        }
        if (band_mode==5){
            sx =band_mode+"b" +" - "+ display_value.getText().toString() + "\n"+"["+first_b_selected_color+","+second_b_selected_color+","+third_b_selected_color+","+multiplier_b_selected_color+","+tolerance_b_selected_color+"]";
        }

        if (band_mode==6){
            sx =band_mode+"b" +" - "+ display_value.getText().toString() + "\n"+"["+first_b_selected_color+","+second_b_selected_color+","+third_b_selected_color+","+multiplier_b_selected_color+","+tolerance_b_selected_color+","+temperature_b_selected_color+"]";
        }

        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_TEXT,sx);
        startActivity(Intent.createChooser(i,"Rcv Calculator"));
    }

    public void back(View view){
        Intent i=new Intent(MainActivity.this,Menu_Page.class);
        startActivity(i);
        this.finish();
    }

    private void emilfb(){
        String to = "techcraftb@gmail.com";
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
        i.putExtra(Intent.EXTRA_SUBJECT,"Rcv Calculator Feedback");
        i.putExtra(Intent.EXTRA_TEXT,"");
        i.setType("message/rfc822");
        try {
            i.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(i,"Rcv Calculator Feedback"));

        }catch (Exception e){
            try {
                startActivity(Intent.createChooser(i,"Rcv Calculator Feedback"));
            }catch (Exception e1){
                Toast.makeText(this, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}