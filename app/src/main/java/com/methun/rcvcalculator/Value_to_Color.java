package com.methun.rcvcalculator;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;

/**
 * Value_to_Color - Reverse Resistor Color Code Calculator.
 *
 * Allows users to input a desired resistance value, select the unit (Ω, kΩ, MΩ, GΩ),
 * and pick tolerance and temperature coefficient ratings. The reverse algorithm
 * derives the required color-band sequence for 4-band, 5-band, or 6-band resistors
 * and paints them onto the live interactive resistor graphic in real-time.
 */
public class Value_to_Color extends AppCompatActivity {

    private String GameID="4655191";
    private String bannerPlacement="Banner_Android";
    private boolean testMood=false;
    int def=0;
    Spinner spinner,tol_vc,tmp_vc;
    String[] ohmSimble={"Ω","kΩ","MΩ","GΩ"};
    int band_vc=0;
    String mulit;
    View band4_layout_vc;
    View band5_layout_vc;
    View band6_layout_vc;
    LinearLayout tmp_b;
    TextView dis;
    EditText inp_vcx;
    long multiply_value ;
    String _4b_disV,_5b_disV,_6b_disV;

    View first_4b,second_4b,multiple_4b,toletance_4b;
    View first_5b,second_5b,third_5b,multiple_5b,toletance_5b;
    View first_6b,second_6b,third_6b,multiple_6b,toletance_6b,temperature_6b;

    String tol_val;
    String tmp_val;
    String inputValue;
    int d_def=0;
    ImageView optionBtn;

    String _4FirstC="Brown",
            _4secondC="Black",
            _4MultiC="Red",
            _4tol_vx="Gold";
    String _5FirstC="Red",
            _5secondC="Black",
            _5thirdC="Black",
            _5MultiC="Brown";
    String _6FirstC="Red",
            _6secondC="Red",
            _6thirdC="Black",
            _6MultiC="Brown",
            _6tmp_vx="Violet";


    String[] tolerance_band_colorN={"Brown","Red","Orange","Yellow","Green","Blue","Violet","Grey","Gold","Silver","None"};
    String[] tolerance_band_colorV={"±1%","±2%","±0.05%","±0.02%","±0.5%","±0.25%","±0.1%","±0.01%","±5%","±10%","±20%"};
    int[] tolerance_band_colorImg={R.drawable.brown_img,R.drawable.red_img,R.drawable.orange_img,R.drawable.yellow_img,
            R.drawable.green_img, R.drawable.blue_img,R.drawable.violet_img,R.drawable.grey_img,R.drawable.gold_img,R.drawable.silver_img,R.drawable.white_img};

    String[] temperature_band_colorN={"Black","Brown","Red","Orange","Yellow","Green","Blue","Violet","Grey"};
    String[] temperature_band_colorV={"250ppm/°C","100ppm/°C","50ppm/°C","15ppm/°C","25ppm/°C","20ppm/°C","10ppm/°C","5ppm/°C","1ppm/°C"};
    int[] temperature_band_colorImg={R.drawable.black_img,R.drawable.brown_img,R.drawable.red_img,R.drawable.orange_img,R.drawable.yellow_img,
            R.drawable.green_img, R.drawable.blue_img,R.drawable.violet_img,R.drawable.grey_img};


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_to__color);

       UnityAds.initialize(this,GameID, testMood);

        IUnityBannerListener bannerListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String s, View view) {
                ((ViewGroup)findViewById(R.id.vcAdsL)).removeView(view);
                ((ViewGroup)findViewById(R.id.vcAdsL)).addView(view);
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
              //  Toast.makeText(Value_to_Color.this, ""+s, Toast.LENGTH_SHORT).show();
                Handler handler =new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UnityBanners.loadBanner(Value_to_Color.this,bannerPlacement);
                    }
                },5000);
            }

        };
        UnityBanners.setBannerListener(bannerListener);
        UnityBanners.loadBanner(this,bannerPlacement);


        spinner = findViewById(R.id.bnd_spn);
        icn_adapter my_spn=new icn_adapter(ohmSimble,this);
        spinner.setAdapter(my_spn);
        RadioGroup group= findViewById(R.id.radioGroup_vc);
        group.check(R.id.radioButton1_vc);
        tol_vc = findViewById(R.id.tol_vc);
        tmp_vc = findViewById(R.id.tmp_vc);
        band4_layout_vc = findViewById(R.id.b4_vc);
        band5_layout_vc = findViewById(R.id.b5_vc);
        band6_layout_vc = findViewById(R.id.b6_vc);
        tmp_b = findViewById(R.id.tmp_b_vc);
        dis = findViewById(R.id.vlu_vc);
        inp_vcx =findViewById(R.id.inpx_vcx);
        inputValue = inp_vcx.getText().toString().trim();
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

        optionBtn = findViewById(R.id.more_iconx);

        spinnner_adaptor tolerance_b_spinner_adaptor=new spinnner_adaptor(this,tolerance_band_colorN,tolerance_band_colorV,tolerance_band_colorImg);
        tol_vc.setAdapter(tolerance_b_spinner_adaptor);
        spinnner_adaptor temperature_b_spinner_adaptor=new spinnner_adaptor(this,temperature_band_colorN,temperature_band_colorV,temperature_band_colorImg);
        tmp_vc.setAdapter(temperature_b_spinner_adaptor);


        group.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i){
                case R.id.radioButton1_vc:
                    band4_layout_vc.setVisibility(View.VISIBLE);
                    band5_layout_vc.setVisibility(View.GONE);
                    band6_layout_vc.setVisibility(View.GONE);
                    tmp_b.setVisibility(View.GONE);
                    tol_vc.setVisibility(View.VISIBLE);
                    tmp_vc.setVisibility(View.GONE);
                    band_vc=4;
                    break;
                case R.id.radioButton2_vc:
                    band4_layout_vc.setVisibility(View.GONE);
                    band5_layout_vc.setVisibility(View.VISIBLE);
                    band6_layout_vc.setVisibility(View.GONE);
                    tmp_b.setVisibility(View.GONE);
                    tol_vc.setVisibility(View.VISIBLE);
                    tmp_vc.setVisibility(View.GONE);
                    band_vc=5;
                    break;
                case R.id.radioButton3_vc:
                    band4_layout_vc.setVisibility(View.GONE);
                    band5_layout_vc.setVisibility(View.GONE);
                    band6_layout_vc.setVisibility(View.VISIBLE);
                    tmp_b.setVisibility(View.VISIBLE);
                    tol_vc.setVisibility(View.VISIBLE);
                    tmp_vc.setVisibility(View.VISIBLE);
                    band_vc=6;
                    break;
            }
                _disVal();
        });

        optionBtn.setOnClickListener(view -> {
            PopupMenu popup=new PopupMenu(Value_to_Color.this,optionBtn);
            popup.getMenuInflater().inflate(R.menu.more_2,popup.getMenu());
            popup.show();

            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.ac:
                       cv();
                        break;
                    case R.id.sta:
                        txsCa();
                        break;
                    case R.id.fqa:
                       emilfbx();
                        break;
                    case R.id.exit1:
                        finish();
                }
                return false;
            });
        });

        tol_vc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tol_val=tolerance_band_colorV[i];
                _4tol_vx=tolerance_band_colorN[i];
                switch (tolerance_band_colorN[i].toLowerCase()) {
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
                switch (tolerance_band_colorN[i].toLowerCase()) {
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
                switch (tolerance_band_colorN[i].toLowerCase()) {
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
                def=1;
                _disVal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tmp_vc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tmp_val=temperature_band_colorV[i];
                _6tmp_vx=temperature_band_colorN[i];
                switch (temperature_band_colorN[i].toLowerCase()) {
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
                def=1;
                _disVal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mulit=ohmSimble[i];
                _disVal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button btn=findViewById(R.id.btn_x);
        btn.setOnClickListener(view -> {

            int ok=1;




            if (inp_vcx.getText().toString().isEmpty()) {
                inp_vcx.setError("No value entered!");
                inp_vcx.requestFocus();
                return;
            }
            if (inp_vcx.getText().toString().length()>11) {
                inp_vcx.setError("Invalid value!");
                inp_vcx.requestFocus();
                return;
            }

            if (inp_vcx.getText().toString().length() == 1 && inp_vcx.getText().toString().equals(".") ) {
                inp_vcx.setError("Invalid value entered!");
                inp_vcx.requestFocus();
                ok=0;
                return;
            }
           if (inp_vcx.getText().toString().equals("0") && inp_vcx.getText().toString().length() == 1){
               inp_vcx.setError("Invalid value entered!");
               inp_vcx.requestFocus();
               return;
           }
           if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString()) >= 100 && Float.parseFloat(inp_vcx.getText().toString()) < 1000 && inp_vcx.getText().toString().contains(".") && inp_vcx.getText().toString().length() > 3){
               inp_vcx.setError("Invalid value entered!");
               inp_vcx.requestFocus();
               return;
           }
            float fx=Float.parseFloat(inp_vcx.getText().toString())*1;

            if (fx==0 || fx <0){
                inp_vcx.setError("No value entered!");
                inp_vcx.requestFocus();
                return;
            }
            if (fx!=0 || fx >0){

            }

           if (band_vc==4){
               if ( mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString()) <= 100 && Float.parseFloat(inp_vcx.getText().toString()) >= 10 && inp_vcx.getText().toString().contains(".") ){
                   inp_vcx.setError("Invalid value entered!");
                   inp_vcx.requestFocus();
                   ok=0;
                   return;
               }
             if (ok==1){
                 _mult();
             }
                   for (int i=2;i<String.valueOf(multiply_value).length();i++){
                       if (String.valueOf(multiply_value).charAt(i)!='0'){
                           inp_vcx.setError("Invalid value entered!");
                           inp_vcx.requestFocus();
                           return;
                       }
                   }
                   _color();
           }
           if (band_vc==5){
               if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=100 && Float.parseFloat(inp_vcx.getText().toString()) < 1000 && inp_vcx.getText().toString().contains(".")){
                   inp_vcx.setError("Invalid value entered!");
                   inp_vcx.requestFocus();
               }
               if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString()) < 1){
                   inp_vcx.setError("Invalid value entered!");
                   inp_vcx.requestFocus();
               }

               if (ok==1){
                   _5multi();
               }
               int dt=0;
               if ( inp_vcx.getText().toString().length() >3 &&  inp_vcx.getText().toString().contains(".")){
                   dt=1;
               }
              if (dt==0 && inp_vcx.getText().toString().length() >3){
                  for (int i = 3; i< inp_vcx.getText().toString().length(); i++){
                      if (inp_vcx.getText().toString().charAt(i)!='0'){
                          inp_vcx.setError("Invalid value entered!");
                          inp_vcx.requestFocus();
                          return;
                      }
                  }
              }
               for (int i=3;i<String.valueOf(multiply_value).length();i++){
                   if (String.valueOf(multiply_value).charAt(i)!='0'){
                       inp_vcx.setError("Invalid value entered!");
                       inp_vcx.requestFocus();
                       return;
                   }
               }
               _color();
           }

            if (band_vc==6){
                if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=100 && Float.parseFloat(inp_vcx.getText().toString()) < 1000 && inp_vcx.getText().toString().contains(".")){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                }
                if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString()) < 1){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                }

                if (ok==1){
                    _6multi();
                }
                int dt=0;
                if ( inp_vcx.getText().toString().length() >3 &&  inp_vcx.getText().toString().contains(".")){
                    dt=1;
                }
                if (dt==0 && inp_vcx.getText().toString().length() >3){
                    for (int i = 3; i< inp_vcx.getText().toString().length(); i++){
                        if (inp_vcx.getText().toString().charAt(i)!='0'){
                            inp_vcx.setError("Invalid value entered!");
                            inp_vcx.requestFocus();
                            return;
                        }
                    }
                }
                for (int i=3;i<String.valueOf(multiply_value).length();i++){
                    if (String.valueOf(multiply_value).charAt(i)!='0'){
                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                }
                _color();
            }

        });

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Value_to_Color.this,Menu_Page.class);
        startActivity(i);
        this.finish();
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        if (band_vc==0){
            band_vc=4;
        }
        if (mulit==""){
            mulit="Ω";
        }
        if (def==0){
            tol_vc.setSelection(8);
            tmp_vc.setSelection(7);
            def=1;
        }
        if (d_def==0){
         def_dis();
            d_def=1;
        }
        super.onStart();
    }
    private void band4Color(String a, String b, String c){
        switch (String.valueOf(a)) {
            case "1":
                first_4b.setBackgroundResource(R.color.brown);
                _4FirstC="Brown";
                break;
            case "2":
                first_4b.setBackgroundResource(R.color.red);
                _4FirstC="Red";
                break;
            case "3":
                first_4b.setBackgroundResource(R.color.orange);
                _4FirstC="Orange";
                break;
            case "4":
                first_4b.setBackgroundResource(R.color.yellow);
                _4FirstC="Yellow";
                break;
            case "5":
                first_4b.setBackgroundResource(R.color.green);
                _4FirstC="Green";
                break;
            case "6":
                first_4b.setBackgroundResource(R.color.blue);
                _4FirstC="Blue";
                break;
            case "7":
                first_4b.setBackgroundResource(R.color.violet);
                _4FirstC="Violet";
                break;
            case "8":
                first_4b.setBackgroundResource(R.color.grey);
                _4FirstC="Grey";
                break;
            case "9":
                first_4b.setBackgroundResource(R.drawable.white_img);
                _4FirstC="White";
                break;

        }
        switch (String.valueOf(b)) {
            case "0":
                second_4b.setBackgroundResource(R.drawable.black_img);
                _4secondC="Black";
                break;
            case "1":
                second_4b.setBackgroundResource(R.color.brown);
                _4secondC="Brown";
                break;
            case "2":
                second_4b.setBackgroundResource(R.color.red);
                _4secondC="Red";
                break;
            case "3":
                second_4b.setBackgroundResource(R.color.orange);
                _4secondC="Orange";
                break;
            case "4":
                second_4b.setBackgroundResource(R.color.yellow);
                _4secondC="Yellow";
                break;
            case "5":
                second_4b.setBackgroundResource(R.color.green);
                _4secondC="Green";
                break;
            case "6":
                second_4b.setBackgroundResource(R.color.blue);
                _4secondC="Blue";
                break;
            case "7":
                second_4b.setBackgroundResource(R.color.violet);
                _4secondC="Violet";
                break;
            case "8":
                second_4b.setBackgroundResource(R.color.grey);
                _4secondC="Grey";
                break;
            case "9":
                second_4b.setBackgroundResource(R.drawable.white_img);
                _4secondC="White";
                break;

        }
        switch (String.valueOf(c)) {
            case "0":
                multiple_4b.setBackgroundResource(R.drawable.black_img);
                _4MultiC = "Black";
                break;
            case "1":
                multiple_4b.setBackgroundResource(R.color.brown);
                _4MultiC = "Brown";
                break;
            case "2":
                multiple_4b.setBackgroundResource(R.color.red);
                _4MultiC = "Red";
                break;
            case "3":
                multiple_4b.setBackgroundResource(R.color.orange);
                _4MultiC = "Orange";
                break;
            case "4":
                multiple_4b.setBackgroundResource(R.color.yellow);
                _4MultiC = "Yellow";
                break;
            case "5":
                multiple_4b.setBackgroundResource(R.color.green);
                _4MultiC = "Green";
                break;
            case "6":
                multiple_4b.setBackgroundResource(R.color.blue);
                _4MultiC = "Blue";
                break;
            case "7":
                multiple_4b.setBackgroundResource(R.color.violet);
                _4MultiC = "Violet";
                break;
            case "8":
                multiple_4b.setBackgroundResource(R.color.grey);
                _4MultiC = "Grey";
                break;
            case "9":
                multiple_4b.setBackgroundResource(R.drawable.white_img);
                _4MultiC = "White";
                break;
            case "-1":
                multiple_4b.setBackgroundResource(R.drawable.clr);
                _4MultiC = "Gold";
                break;
            case "-2":
                multiple_4b.setBackgroundResource(R.color.silver);
                _4MultiC = "Silver";
                break;

        }
    }
    private void band5Color(String a, String b, String c,String m){
        switch (String.valueOf(a)) {
            case "1":
                first_5b.setBackgroundResource(R.color.brown);
                _5FirstC="Brown";
                break;
            case "2":
                first_5b.setBackgroundResource(R.color.red);
                _5FirstC="Red";
                break;
            case "3":
                first_5b.setBackgroundResource(R.color.orange);
                _5FirstC="Orange";
                break;
            case "4":
                first_5b.setBackgroundResource(R.color.yellow);
                _5FirstC="Yellow";
                break;
            case "5":
                first_5b.setBackgroundResource(R.color.green);
                _5FirstC="Green";
                break;
            case "6":
                first_5b.setBackgroundResource(R.color.blue);
                _5FirstC="Blue";
                break;
            case "7":
                first_5b.setBackgroundResource(R.color.violet);
                _5FirstC="Violet";
                break;
            case "8":
                first_5b.setBackgroundResource(R.color.grey);
                _5FirstC="Grey";
                break;
            case "9":
                first_5b.setBackgroundResource(R.drawable.white_img);
                _5FirstC="White";
                break;

        }
        switch (String.valueOf(b)) {
            case "0":
                second_5b.setBackgroundResource(R.drawable.black_img);
                _5secondC="Black";
                break;
            case "1":
                second_5b.setBackgroundResource(R.color.brown);
                _5secondC="Brown";
                break;
            case "2":
                second_5b.setBackgroundResource(R.color.red);
                _5secondC="Red";
                break;
            case "3":
                second_5b.setBackgroundResource(R.color.orange);
                _5secondC="Orange";
                break;
            case "4":
                second_5b.setBackgroundResource(R.color.yellow);
                _5secondC="Yellow";
                break;
            case "5":
                second_5b.setBackgroundResource(R.color.green);
                _5secondC="Green";
                break;
            case "6":
                second_5b.setBackgroundResource(R.color.blue);
                _5secondC="Blue";
                break;
            case "7":
                second_5b.setBackgroundResource(R.color.violet);
                _5secondC="Violet";
                break;
            case "8":
                second_5b.setBackgroundResource(R.color.grey);
                _5secondC="Grey";
                break;
            case "9":
                second_5b.setBackgroundResource(R.drawable.white_img);
                _5secondC="White";
                break;

        }
         switch (String.valueOf(c)) {
                case "0":
                    third_5b.setBackgroundResource(R.drawable.black_img);
                    _5thirdC = "Black";
                    break;
                case "1":
                    third_5b.setBackgroundResource(R.color.brown);
                    _5thirdC = "Brown";
                    break;
                case "2":
                    third_5b.setBackgroundResource(R.color.red);
                    _5thirdC = "Red";
                    break;
                case "3":
                    third_5b.setBackgroundResource(R.color.orange);
                    _5thirdC = "Orange";
                    break;
                case "4":
                    third_5b.setBackgroundResource(R.color.yellow);
                    _5thirdC = "Yellow";
                    break;
                case "5":
                    third_5b.setBackgroundResource(R.color.green);
                    _5thirdC = "Green";
                    break;
                case "6":
                    third_5b.setBackgroundResource(R.color.blue);
                    _5thirdC = "Blue";
                    break;
                case "7":
                    third_5b.setBackgroundResource(R.color.violet);
                    _5thirdC = "Violet";
                    break;
                case "8":
                    third_5b.setBackgroundResource(R.color.grey);
                    _5thirdC = "Grey";
                    break;
                case "9":
                    third_5b.setBackgroundResource(R.drawable.white_img);
                    _5thirdC = "White";
                    break;

            }
        switch (String.valueOf(m)) {
            case "0":
                multiple_5b.setBackgroundResource(R.drawable.black_img);
                _5MultiC = "Black";
                break;
            case "1":
                multiple_5b.setBackgroundResource(R.color.brown);
                _5MultiC = "Brown";
                break;
            case "2":
                multiple_5b.setBackgroundResource(R.color.red);
                _5MultiC = "Red";
                break;
            case "3":
                multiple_5b.setBackgroundResource(R.color.orange);
                _5MultiC = "Orange";
                break;
            case "4":
                multiple_5b.setBackgroundResource(R.color.yellow);
                _5MultiC = "Yellow";
                break;
            case "5":
                multiple_5b.setBackgroundResource(R.color.green);
                _5MultiC = "Green";
                break;
            case "6":
                multiple_5b.setBackgroundResource(R.color.blue);
                _5MultiC = "Blue";
                break;
            case "7":
                multiple_5b.setBackgroundResource(R.color.violet);
                _5MultiC = "Violet";
                break;
            case "8":
                multiple_5b.setBackgroundResource(R.color.grey);
                _5MultiC = "Grey";
                break;
            case "9":
                multiple_5b.setBackgroundResource(R.drawable.white_img);
                _5MultiC = "White";
                break;
            case "-1":
                multiple_5b.setBackgroundResource(R.drawable.clr);
                _5MultiC = "Gold";
                break;
            case "-2":
                multiple_5b.setBackgroundResource(R.color.silver);
                _5MultiC = "Silver";
                break;

        }
    }
    private void band6Color(String a, String b, String c,String m){
        switch (String.valueOf(a)) {
            case "1":
                first_6b.setBackgroundResource(R.color.brown);
                _6FirstC = "Brown";
                break;
            case "2":
                first_6b.setBackgroundResource(R.color.red);
                _6FirstC = "Red";
                break;
            case "3":
                first_6b.setBackgroundResource(R.color.orange);
                _6FirstC = "Orange";
                break;
            case "4":
                first_6b.setBackgroundResource(R.color.yellow);
                _6FirstC = "Yellow";
                break;
            case "5":
                first_6b.setBackgroundResource(R.color.green);
                _6FirstC = "Green";
                break;
            case "6":
                first_6b.setBackgroundResource(R.color.blue);
                _6FirstC = "Blue";
                break;
            case "7":
                first_6b.setBackgroundResource(R.color.violet);
                _6FirstC = "Violet";
                break;
            case "8":
                first_6b.setBackgroundResource(R.color.grey);
                _6FirstC = "Grey";
                break;
            case "9":
                first_6b.setBackgroundResource(R.drawable.white_img);
                _6FirstC = "White";
                break;

        }
        switch (String.valueOf(b)) {
            case "0":
                second_6b.setBackgroundResource(R.drawable.black_img);
                _6secondC = "Black";
                break;
            case "1":
                second_6b.setBackgroundResource(R.color.brown);
                _6secondC = "Brown";
                break;
            case "2":
                second_6b.setBackgroundResource(R.color.red);
                _6secondC = "Red";
                break;
            case "3":
                second_6b.setBackgroundResource(R.color.orange);
                _6secondC = "Orange";
                break;
            case "4":
                second_6b.setBackgroundResource(R.color.yellow);
                _6secondC = "Yellow";
                break;
            case "5":
                second_6b.setBackgroundResource(R.color.green);
                _6secondC = "Green";
                break;
            case "6":
                second_6b.setBackgroundResource(R.color.blue);
                _6secondC = "Blue";
                break;
            case "7":
                second_6b.setBackgroundResource(R.color.violet);
                _6secondC = "Violet";
                break;
            case "8":
                second_6b.setBackgroundResource(R.color.grey);
                _6secondC = "Grey";
                break;
            case "9":
                second_6b.setBackgroundResource(R.drawable.white_img);
                _6secondC = "White";
                break;

        }
        switch (String.valueOf(c)) {
            case "0":
                third_6b.setBackgroundResource(R.drawable.black_img);
                _6thirdC = "Black";
                break;
            case "1":
                third_6b.setBackgroundResource(R.color.brown);
                _6thirdC = "Brown";
                break;
            case "2":
                third_6b.setBackgroundResource(R.color.red);
                _6thirdC = "Red";
                break;
            case "3":
                third_6b.setBackgroundResource(R.color.orange);
                _6thirdC = "Orange";
                break;
            case "4":
                third_6b.setBackgroundResource(R.color.yellow);
                _6thirdC = "Yellow";
                break;
            case "5":
                third_6b.setBackgroundResource(R.color.green);
                _6thirdC = "Green";
                break;
            case "6":
                third_6b.setBackgroundResource(R.color.blue);
                _6thirdC = "Blue";
                break;
            case "7":
                third_6b.setBackgroundResource(R.color.violet);
                _6thirdC = "Violet";
                break;
            case "8":
                third_6b.setBackgroundResource(R.color.grey);
                _6thirdC = "Grey";
                break;
            case "9":
                third_6b.setBackgroundResource(R.drawable.white_img);
                _6thirdC = "White";
                break;

        }
        switch (String.valueOf(m)) {
            case "0":
                multiple_6b.setBackgroundResource(R.drawable.black_img);
                _6MultiC = "Black";
                break;
            case "1":
                multiple_6b.setBackgroundResource(R.color.brown);
                _6MultiC = "Brown";
                break;
            case "2":
                multiple_5b.setBackgroundResource(R.color.red);
                _6MultiC = "Red";
                break;
            case "3":
                multiple_6b.setBackgroundResource(R.color.orange);
                _6MultiC = "Orange";
                break;
            case "4":
                multiple_6b.setBackgroundResource(R.color.yellow);
                _6MultiC = "Yellow";
                break;
            case "5":
                multiple_6b.setBackgroundResource(R.color.green);
                _6MultiC = "Green";
                break;
            case "6":
                multiple_6b.setBackgroundResource(R.color.blue);
                _6MultiC = "Blue";
                break;
            case "7":
                multiple_6b.setBackgroundResource(R.color.violet);
                _6MultiC = "Violet";
                break;
            case "8":
                multiple_6b.setBackgroundResource(R.color.grey);
                _6MultiC = "Grey";
                break;
            case "9":
                multiple_6b.setBackgroundResource(R.drawable.white_img);
                _6MultiC = "White";
                break;
            case "-1":
                multiple_6b.setBackgroundResource(R.drawable.clr);
                _6MultiC = "Gold";
                break;
            case "-2":
                multiple_6b.setBackgroundResource(R.color.silver);
                _6MultiC = "Silver";
                break;

        }
    }
    public void _color(){
        if (band_vc==4 ){

            if (String.valueOf(multiply_value).length()==1 && multiply_value >= 1 && multiply_value <=9){
                band4Color(String.valueOf(String.valueOf(multiply_value).charAt(0)),"0","-1");
                _4b_disV=String.valueOf(multiply_value)+" Ω";
            }

            if (multiply_value < 100 && multiply_value >= 10){
                band4Color(String.valueOf(String.valueOf(multiply_value).charAt(0)),String.valueOf(String.valueOf(multiply_value).charAt(1)),String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-2))));
                _4b_disV=String.valueOf(multiply_value)+" Ω";
            }

            if (multiply_value >= 100 && multiply_value<1000){
                band4Color(String.valueOf(String.valueOf(multiply_value).charAt(0)),String.valueOf(String.valueOf(multiply_value).charAt(1)),String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-2))));
                _4b_disV =multiply_value +" Ω";

            }

           if (multiply_value>=1000 && multiply_value<1000000){

               band4Color(String.valueOf(String.valueOf(multiply_value).charAt(0)),String.valueOf(String.valueOf(multiply_value).charAt(1)),String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-2))));
               if (String.valueOf(multiply_value).length()==4 && String.valueOf(multiply_value).charAt(1)!='0'){
                   float xb = multiply_value/1000f;
                   String xd=String.valueOf(xb);
                   _4b_disV=xd+" kΩ";
               }
              if (String.valueOf(multiply_value).length() >= 4 && String.valueOf(multiply_value).charAt(1)=='0'){
                  long xxb=multiply_value/1000;
                  _4b_disV=xxb+" kΩ";
              }
               if (String.valueOf(multiply_value).length() >= 4 && String.valueOf(multiply_value).charAt(2)=='0' && String.valueOf(multiply_value).charAt(1)=='0'){
                   long xcb=multiply_value/1000;
                   _4b_disV=xcb+" kΩ";
               }
               if (String.valueOf(multiply_value).length() >= 5 && String.valueOf(multiply_value).charAt(2)=='0' && String.valueOf(multiply_value).charAt(1)!='0'){
                   long xcb=multiply_value/1000;
                   _4b_disV=xcb+" kΩ";
               }
           }
            if (multiply_value>=1000000 && multiply_value<1000000000){

                band4Color(String.valueOf(String.valueOf(multiply_value).charAt(0)),String.valueOf(String.valueOf(multiply_value).charAt(1)),String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-2))));
                double dx= multiply_value/1000000d;
                long lx = multiply_value/ 1000000L;
                String sx= String.valueOf(lx);
                for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                        if (String.valueOf(dx).charAt(i)=='0'){
                            _4b_disV=Long.valueOf(sx)+" MΩ";
                            break;
                        }
                    if (String.valueOf(dx).charAt(i)!=0){
                        _4b_disV=dx+" MΩ";
                    }
                }
            }
            if (multiply_value>=1000000000 && multiply_value < 100000000000L){

                band4Color(String.valueOf(String.valueOf(multiply_value).charAt(0)),String.valueOf(String.valueOf(multiply_value).charAt(1)),String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-2))));
                double dx= multiply_value/1000000000d;
                long lx = multiply_value/1000000000L;
                String sx= String.valueOf(lx);
                for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                    if (String.valueOf(dx).charAt(i)=='0'){
                        _4b_disV=sx+" GΩ";
                        break;
                    }
                    if (String.valueOf(dx).charAt(i)!=0){
                        _4b_disV=dx+" GΩ";
                    }
                }
            }
            if (multiply_value >= 100000000000L){
                inp_vcx.setError("Invalid 4 band value entered!");
                inp_vcx.requestFocus();
            }
        }
        if (band_vc==5){
            if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=100 && Float.parseFloat(inp_vcx.getText().toString()) < 1000){
                band5Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
               _5b_disV = Long.valueOf(multiply_value)+" Ω";
            }

            if (multiply_value >= 100 && multiply_value<1000){

                band5Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));

            }

            if (multiply_value>=1000 && multiply_value<1000000){

                band5Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
                double dx= multiply_value/1000d;
                long lx = multiply_value/1000L;
                String sx= String.valueOf(lx);
                for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                    if (String.valueOf(dx).charAt(i)=='0'){
                        _5b_disV=sx+" kΩ";
                        break;
                    }
                    if (String.valueOf(dx).charAt(i)!=0){
                        _5b_disV=dx+" kΩ";
                    }
                }
            }
            if (multiply_value>=1000000 && multiply_value<1000000000){
                band5Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
                double dx= multiply_value/1000000d;
                long lx = multiply_value/1000000l;
                String sx= String.valueOf(lx);
                for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                    if (String.valueOf(dx).charAt(i)=='0'){
                        _5b_disV=sx+" MΩ";
                        break;
                    }
                    if (String.valueOf(dx).charAt(i)!=0){
                        _5b_disV=dx+" MΩ";
                    }
                }
            }
            if (multiply_value>=1000000000 && multiply_value < 1000000000000L){

                band5Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
                double dx= multiply_value/1000000000d;
                long lx = multiply_value/1000000000L;
                String sx= String.valueOf(lx);
                for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                    if (String.valueOf(dx).charAt(i)=='0'){
                        _5b_disV=sx+" GΩ";
                        break;
                    }
                    if (String.valueOf(dx).charAt(i)!=0){
                        _5b_disV=dx+" GΩ";
                    }
                }
            }
            if (multiply_value >= 1000000000000L){
                inp_vcx.setError("Invalid 5 band value entered!");
                inp_vcx.requestFocus();
            }

        }
        if (band_vc==6){
            if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=100 && Float.parseFloat(inp_vcx.getText().toString()) < 1000){
                band6Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
                _6b_disV = Long.valueOf(multiply_value)+" Ω";
            }

            if (multiply_value >= 100 && multiply_value<1000){

                band6Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
            }

            if (multiply_value>=1000 && multiply_value<1000000){

                band6Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
                double dx= multiply_value/1000d;
                long lx = multiply_value/1000L;
                String sx= String.valueOf(lx);
                for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                    if (String.valueOf(dx).charAt(i)=='0'){
                        _6b_disV=sx+" kΩ";
                        break;
                    }
                    if (String.valueOf(dx).charAt(i)!=0){
                        _6b_disV=dx+" kΩ";
                    }
                }
            }
            if (multiply_value>=1000000 && multiply_value<1000000000){

                band6Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
                double dx= multiply_value/1000000d;
                long lx = multiply_value/1000000l;
                String sx= String.valueOf(lx);
                for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                    if (String.valueOf(dx).charAt(i)=='0'){
                        _6b_disV=sx+" MΩ";
                        break;
                    }
                    if (String.valueOf(dx).charAt(i)!=0){
                        _6b_disV=dx+" MΩ";
                    }
                }
            }
            if (multiply_value>=1000000000 && multiply_value < 1000000000000L){

                band6Color(String.valueOf(String.valueOf(multiply_value).charAt(0)) , String.valueOf(String.valueOf(multiply_value).charAt(1)) , String.valueOf(String.valueOf(multiply_value).charAt(2)) , String.valueOf(Integer.parseInt(String.valueOf(String.valueOf(multiply_value).length()-3))));
                double dx= multiply_value/1000000000d;
                long lx = multiply_value/1000000000L;
                String sx= String.valueOf(lx);
                for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                    if (String.valueOf(dx).charAt(i)=='0'){
                        _6b_disV=sx+" GΩ";
                        break;
                    }
                    if (String.valueOf(dx).charAt(i)!=0){
                        _6b_disV=dx+" GΩ";
                    }
                }
            }
            if (multiply_value >= 1000000000000L){
                inp_vcx.setError("Invalid 6 band value entered!");
                inp_vcx.requestFocus();
            }

        }
        _disVal();
    }

        public void _mult(){

        if (mulit=="Ω" && inp_vcx.getText().toString().contains(".") && inp_vcx.getText().toString()!="." && Float.parseFloat(inp_vcx.getText().toString()) >= 1 && Float.parseFloat(inp_vcx.getText().toString()) <10){
                float fc = Float.parseFloat(inp_vcx.getText().toString()) *1000f;
                float fx = fc * 100;
                float fcx= fx/1000;
                multiply_value=000;
                    if (String.valueOf(fcx).length() > 5){
                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).charAt(2) != '0' ){
                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).charAt(4) != '0'){
                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).length()==5 && String.valueOf(fcx).charAt(3)=='.' && String.valueOf(fcx).charAt(4)=='0' && String.valueOf(fcx).charAt(2)=='0'){
                        band4Color(String.valueOf(String.valueOf(fcx).charAt(0)),String.valueOf(String.valueOf(fcx).charAt(1)),"-1");
                        _4b_disV=String.valueOf(fcx/100)+" Ω";
                    }
        }
            if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>0 && Float.parseFloat(inp_vcx.getText().toString())<1 && inp_vcx.getText().toString().contains(".")){
                float fb = Float.parseFloat(inp_vcx.getText().toString()) *1000f;
                float fx = fb * 100;
                float fbx= fx/1000;
                multiply_value=000;

                if (String.valueOf(fbx).length() > 4 || String.valueOf(fbx).length() <= 3){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                    return;
                }
                if (String.valueOf(fbx).charAt(3) != '0' ){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                    return;
                }

                if (String.valueOf(fbx).length()==4 && String.valueOf(fbx).charAt(2)=='.' && String.valueOf(fbx).charAt(3)=='0'){
                    band4Color(String.valueOf(String.valueOf(fbx).charAt(0)),String.valueOf(String.valueOf(fbx).charAt(1)),"-2");
                    _4b_disV=String.valueOf(fbx/100)+" Ω";
                }

            }


            if (mulit=="Ω" && inp_vcx.getText().toString().length() == 1 && inp_vcx.getText().toString()!="0" && inp_vcx.getText().toString()!="." && Float.parseFloat(inp_vcx.getText().toString()) >= 1 && Float.parseFloat(inp_vcx.getText().toString()) <=9){
                multiply_value= Long.parseLong(inp_vcx.getText().toString());
            }

            if ( mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString()) >= 10 && Float.parseFloat(inp_vcx.getText().toString()) < 100){

                multiply_value= Long.parseLong(inp_vcx.getText().toString());
            }

            if ( mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=100 && Float.parseFloat(inp_vcx.getText().toString())<1000){

                multiply_value= Long.parseLong(inp_vcx.getText().toString());
            }

            if ( mulit=="kΩ"){

                multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())*1000);
            }
            if ( mulit=="MΩ"){
                multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())*1000000);
            }
            if ( mulit=="GΩ"){
                multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())* Math.pow(10, 9));
            }
            if (mulit=="GΩ" && multiply_value > 1000000000L && multiply_value<10000000000L && inp_vcx.getText().toString().contains(".")){
                int x= (int) (Float.parseFloat(inp_vcx.getText().toString())*1000L);
                long lx = x * 1000000000L;
                long dv = lx/1000L;
                multiply_value=dv;
            }
            if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=1000){
                if (inp_vcx.getText().toString().contains(".")){
                    inp_vcx.setError("Result not found!");
                    inp_vcx.requestFocus();
                    return;
                }
                multiply_value= Long.parseLong(inp_vcx.getText().toString());
            }
        }
        private void _5multi(){
            if (band_vc==5){
                if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=100 && Float.parseFloat(inp_vcx.getText().toString()) < 1000){
                    multiply_value=Long.parseLong(inp_vcx.getText().toString());

                }
                if ( mulit=="kΩ" ){

                    multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())*1000);
                }
                if ( mulit=="MΩ"){
                    multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())*1000000);
                }
                if ( mulit=="GΩ"){
                    multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())* Math.pow(10, 9));
                }
                if (mulit=="GΩ" && multiply_value > 1000000000L && multiply_value<1000000000000L && inp_vcx.getText().toString().contains(".")){
                    int x= (int) (Float.parseFloat(inp_vcx.getText().toString())*1000L);
                    long lx = x * 1000000000L;
                    long dv = lx/1000L;
                    multiply_value=dv;
                }
                if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=10 && Float.parseFloat(inp_vcx.getText().toString()) < 100){
                    float fc = Float.parseFloat(inp_vcx.getText().toString()) *1000f;
                    float fx = fc * 100;
                    float fcx= fx/1000;
                    multiply_value=000;
                    if (String.valueOf(fcx).length() > 6){
                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).charAt(3) != '0' ){
                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).charAt(5) != '0'){

                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).length()==6 && String.valueOf(fcx).charAt(4)=='.' && String.valueOf(fcx).charAt(3)=='0' && String.valueOf(fcx).charAt(5)=='0'){
                        band5Color(String.valueOf(String.valueOf(fcx).charAt(0)),String.valueOf(String.valueOf(fcx).charAt(1)) , String.valueOf(String.valueOf(fcx).charAt(2)),"-1");
                        double dx= fcx/100d;
                        long lx = (long) (fcx/100L);
                        String sx= String.valueOf(lx);
                        for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                            if (String.valueOf(dx).charAt(i)=='0'){
                                _5b_disV=sx+" Ω";
                                break;
                            }
                            if (String.valueOf(dx).charAt(i)!=0){
                                _5b_disV=dx+" Ω";
                            }
                        }

                    }
                }

                if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=1 && Float.parseFloat(inp_vcx.getText().toString()) < 10){
                    float fc = Float.parseFloat(inp_vcx.getText().toString()) *1000f;
                    float fx = fc * 100;
                    float fcx= fx/1000;
                    multiply_value=000;
                    if (String.valueOf(fcx).length() > 5){
                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).charAt(4) != '0' ){
                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).charAt(3) != '.'){

                        inp_vcx.setError("Invalid value entered!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    if (String.valueOf(fcx).length()==5 && String.valueOf(fcx).charAt(3)=='.' && String.valueOf(fcx).charAt(4)=='0'){
                        band5Color(String.valueOf(String.valueOf(fcx).charAt(0)) , String.valueOf(String.valueOf(fcx).charAt(1)) , String.valueOf(String.valueOf(fcx).charAt(2)),"-2");
                        double dx= fcx/100d;
                        long lx = (long) (fcx/100L);
                        String sx= String.valueOf(lx);
                        for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                            if (String.valueOf(dx).charAt(i)=='0'){
                                _5b_disV=sx+" Ω";
                                break;
                            }
                            if (String.valueOf(dx).charAt(i)!=0){
                                _5b_disV=dx+" Ω";
                            }
                        }
                    }
                }

                if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=1000){
                    if (inp_vcx.getText().toString().contains(".")){
                        inp_vcx.setError("Result not found!");
                        inp_vcx.requestFocus();
                        return;
                    }
                    multiply_value= Long.parseLong(inp_vcx.getText().toString());
                }
            }
        }
        private void _6multi(){
        if (band_vc==6){
            if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=100 && Float.parseFloat(inp_vcx.getText().toString()) < 1000){
                multiply_value=Long.parseLong(inp_vcx.getText().toString());
            }
            if ( mulit=="kΩ"){

                multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())*1000);
            }
            if ( mulit=="MΩ"){
                multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())*1000000);
            }
            if ( mulit=="GΩ"){
                multiply_value= (long) (Float.parseFloat(inp_vcx.getText().toString())* Math.pow(10, 9));
            }
            if (mulit=="GΩ" && multiply_value > 1000000000L && multiply_value<1000000000000L && inp_vcx.getText().toString().contains(".")){
                int x= (int) (Float.parseFloat(inp_vcx.getText().toString())*1000L);
                long lx = x * 1000000000L;
                long dv = lx/1000L;
                multiply_value=dv;
            }
            if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=10 && Float.parseFloat(inp_vcx.getText().toString()) < 100){
                float fc = Float.parseFloat(inp_vcx.getText().toString()) *1000f;
                float fx = fc * 100;
                float fcx= fx/1000;
                multiply_value=000;
                if (String.valueOf(fcx).length() > 6){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                    return;
                }
                if (String.valueOf(fcx).charAt(3) != '0' ){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                    return;
                }
                if (String.valueOf(fcx).charAt(5) != '0'){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                    return;
                }
                if (String.valueOf(fcx).length()==6 && String.valueOf(fcx).charAt(4)=='.' && String.valueOf(fcx).charAt(3)=='0' && String.valueOf(fcx).charAt(5)=='0'){
                    band6Color(String.valueOf(String.valueOf(fcx).charAt(0)),String.valueOf(String.valueOf(fcx).charAt(1)) , String.valueOf(String.valueOf(fcx).charAt(2)),"-1");
                    double dx= fcx/100d;
                    long lx = (long) (fcx/100L);
                    String sx= String.valueOf(lx);
                    for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                        if (String.valueOf(dx).charAt(i)=='0'){
                            _6b_disV=sx+" Ω";
                            break;
                        }
                        if (String.valueOf(dx).charAt(i)!=0){
                            _6b_disV=dx+" Ω";
                        }
                    }
                }
            }

            if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=1 && Float.parseFloat(inp_vcx.getText().toString()) < 10){
                float fc = Float.parseFloat(inp_vcx.getText().toString()) *1000f;
                float fx = fc * 100;
                float fcx= fx/1000;
                multiply_value=000;
                if (String.valueOf(fcx).length() > 5){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                    return;
                }
                if (String.valueOf(fcx).charAt(4) != '0' ){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                    return;
                }
                if (String.valueOf(fcx).charAt(3) != '.'){
                    inp_vcx.setError("Invalid value entered!");
                    inp_vcx.requestFocus();
                    return;
                }
                if (String.valueOf(fcx).length()==5 && String.valueOf(fcx).charAt(3)=='.' && String.valueOf(fcx).charAt(4)=='0'){
                    band6Color(String.valueOf(String.valueOf(fcx).charAt(0)) , String.valueOf(String.valueOf(fcx).charAt(1)) , String.valueOf(String.valueOf(fcx).charAt(2)),"-2");
                    double dx= fcx/100d;
                    long lx = (long) (fcx/100L);
                    String sx= String.valueOf(lx);
                    for (int i = String.valueOf(dx).indexOf("."); i < String.valueOf(dx).length(); i++){
                        if (String.valueOf(dx).charAt(i)=='0'){
                            _6b_disV=sx+" Ω";
                            break;
                        }
                        if (String.valueOf(dx).charAt(i)!=0){
                            _6b_disV=dx+" Ω";
                        }
                    }
                }
            }
            if (mulit=="Ω" && Float.parseFloat(inp_vcx.getText().toString())>=1000){
                if (inp_vcx.getText().toString().contains(".")){
                    inp_vcx.setError("Result not found!");
                    inp_vcx.requestFocus();
                    return;
                }
                multiply_value= Long.parseLong(inp_vcx.getText().toString());
            }
        }
    }
        private void _disVal(){
            if (band_vc==4){
                dis.setText(""+_4b_disV+" "+tol_val);
            }
            if (band_vc==5){
                dis.setText(""+_5b_disV+" "+tol_val);
            }
            if (band_vc==6){
                dis.setText(""+_6b_disV+" "+tol_val+" "+tmp_val);
            }
        }

        private void def_dis(){
           _4b_disV=1+" kΩ";
            _5b_disV=2+" kΩ";
            _6b_disV=2.2+" kΩ";
            first_4b.setBackgroundResource(R.color.brown);
            second_4b.setBackgroundResource(R.drawable.black_img);
            multiple_4b.setBackgroundResource(R.color.red);

            first_5b.setBackgroundResource(R.color.red);
            second_5b.setBackgroundResource(R.drawable.black_img);
            third_5b.setBackgroundResource(R.drawable.black_img);
            multiple_5b.setBackgroundResource(R.color.brown);

            first_6b.setBackgroundResource(R.color.red);
            second_6b.setBackgroundResource(R.color.red);
            third_6b.setBackgroundResource(R.drawable.black_img);
            multiple_6b.setBackgroundResource(R.color.brown);
        }

        private void cv(){
            Intent i = new Intent(Value_to_Color.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }

    private void txsCa(){
        String sx = null;
        if (band_vc==4){
            sx =4+"b" +" - "+ dis.getText().toString() + "\n"+"["+_4FirstC+","+_4secondC+","+_4MultiC+","+_4tol_vx+"]";
        }
        if (band_vc==5){
            sx =band_vc+"b" +" - "+ dis.getText().toString() + "\n"+"["+_5FirstC+","+_5secondC+","+_5thirdC+","+_5MultiC+","+_4tol_vx+"]";
        }

        if (band_vc==6){
            sx =band_vc+"b" +" - "+ dis.getText().toString() + "\n"+"["+_6FirstC+","+_6secondC+","+_6thirdC+","+_6MultiC+","+_4tol_vx+","+_6tmp_vx+"]";
        }

        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_TEXT,sx);
        startActivity(Intent.createChooser(i,"Rcv Calculator"));
    }

    public void myDilog(View view){
        if (band_vc==4){
            _4bDilog();
        }
        if (band_vc==5){
            _5bDilog();
        }
        if (band_vc==6){
            _6bDilog();
        }
    }

    private void _4bDilog(){
        String ms ="[ "+band_vc+"b"+" ]"+"  -  "+dis.getText().toString()+"\n\n"+"1st Band  -  "+_4FirstC+"\n\n"+"2nd Band  -  "+_4secondC+"\n\n"+"Multiplier  -  "+_4MultiC+"\n\n"+"Tolerance  -  "+_4tol_vx;
        AlertDialog.Builder alert = new AlertDialog.Builder(Value_to_Color.this, R.style.MyDialogTheme);
        alert.setTitle("Rcv Calculator");
        alert.setIcon(R.drawable.resigstor_icon);
        alert.setMessage(ms);
        alert.setCancelable(true);
        alert.setPositiveButton("ok", (dialogInterface, i) -> {
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void _5bDilog(){
        String ms ="[ "+band_vc+"b"+" ]"+"  -  "+dis.getText().toString()+"\n\n"+"1st Band  -  "+_5FirstC+"\n\n"+"2nd Band  -  "+_5secondC+"\n\n"+"3rd Band  -  "+_5thirdC+"\n\n"+"Multiplier  -  "+_5MultiC+"\n\n"+"Tolerance  -  "+_4tol_vx;
        AlertDialog.Builder alert = new AlertDialog.Builder(Value_to_Color.this, R.style.MyDialogTheme);
        alert.setTitle("Rcv Calculator");
        alert.setIcon(R.drawable.resigstor_icon);
        alert.setMessage(ms);
        alert.setCancelable(true);
        alert.setPositiveButton("ok", (dialogInterface, i) -> {
        });
        AlertDialog dialog =alert.create();
        dialog.show();
    }

    private void _6bDilog(){
        String ms ="[ "+band_vc+"b"+" ]"+"  -  "+dis.getText().toString()+"\n\n"+"1st Band  -  "+_5FirstC+"\n\n"+"2nd Band  -  "+_5secondC+"\n\n"+"3rd Band  -  "+_5thirdC+"\n\n"+"Multiplier  -  "+_5MultiC+"\n\n"+"Tolerance  -  "+_4tol_vx+"\n\n"+"Temperature  -  "+_6tmp_vx;
        AlertDialog.Builder alert = new AlertDialog.Builder(Value_to_Color.this, R.style.MyDialogTheme);
        alert.setTitle("Rcv Calculator");
        alert.setIcon(R.drawable.resigstor_icon);
        alert.setMessage(ms);
        alert.setCancelable(true);
        alert.setPositiveButton("ok", (dialogInterface, i) -> {
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public void bck(View view){
        Intent intent = new Intent(Value_to_Color.this, Menu_Page.class);
            startActivity(intent);
            this.finish();
         }

    private void emilfbx(){
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