package com.example.friends.projectz;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class HomePage extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        pref = getSharedPreferences(BackgroundWorker.MyPref,Context.MODE_PRIVATE);

        editor = pref.edit();
        String usernameSession = pref.getString("usernameSession","");

    }
}
