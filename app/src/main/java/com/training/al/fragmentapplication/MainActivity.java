package com.training.al.fragmentapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import static android.R.attr.fragment;
import static android.R.attr.onClick;


public class MainActivity extends AppCompatActivity {
    String times;
    int numOfVisits = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToggleButton toggle = (ToggleButton)findViewById(R.id.frag);
        toggle.setText("Show First Fragment");
        toggle.setTextOff("Show First Fragment");
        toggle.setTextOn("Show Second Fragment");
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                BlankFragment firstFragment = new BlankFragment();
                SecondFragment secondFragment = new SecondFragment();
                if (isChecked) {
                    transaction.replace(R.id.main_activity_id, firstFragment);
                    transaction.commit();
                } else {
                    transaction.replace(R.id.main_activity_id, secondFragment);
                    transaction.commit();
                }
            }
        });

        SharedPreferences preferences = getSharedPreferences("mypreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        numOfVisits++;
        editor.putInt(times , numOfVisits);
        editor.apply();

        int visit = preferences.getInt(times, numOfVisits);

        Log.d("Altamish", "onResume called");
        Log.d("Altamish", numOfVisits+"");
        Log.d("Altamish", visit+"");

        if(visit == 3){
            AlertDialog.Builder builder  = new AlertDialog.Builder(this);
            builder.setTitle("How do you like our app?");
            builder.setMessage("Please rate us and let us know how we are doing.");
            builder.setPositiveButton("Rate Us!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = "http://www.google.com";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }
}
