package com.example.friends.projectz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;


public class ProfilePage extends AppCompatActivity {
    private SharedPreferences pref;
    private String credentials, usernameSession;
    private Toolbar toolbar;
    private ImageView titleImage, backHome;
    private TextView nameProfile, emailProfile, usernameProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        pref = getSharedPreferences(BackgroundSigninSignup.MyPref, Context.MODE_PRIVATE);
        usernameSession = pref.getString("usernameSession", "");

        Intent intent = getIntent();
        credentials = intent.getStringExtra("credentials");
        List<String> credentialsArray = Arrays.asList(credentials.split(","));

        nameProfile = (TextView)findViewById(R.id.nameProfile);
        emailProfile = (TextView)findViewById(R.id.emailProfile);
        usernameProfile = (TextView)findViewById(R.id.usernameProfile);
        backHome = (ImageView)findViewById(R.id.backHome);

        nameProfile.setText(credentialsArray.get(1));
        emailProfile.setText(credentialsArray.get(0));
        usernameProfile.setText(usernameSession);

        titleImage = (ImageView)findViewById(R.id.titleImage);
        Picasso.with(this).load(credentialsArray.get(5)).fit().centerCrop().into(titleImage);
        titleImage.setColorFilter(Color.argb(50, 0, 0, 0));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(credentialsArray.get(3));
        setSupportActionBar(toolbar);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePage.this, HomePage.class);
                intent.putExtra("credentials", credentials);
                startActivity(intent);
            }
        });

    }

}
