package com.example.friends.projectz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginSignupActivity extends FragmentActivity {
    EditText emailLoginEv, passwordLoginEv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        TextView companyName = (TextView) findViewById(R.id.companyName);
        Typeface face= Typeface.createFromAsset(getAssets(), "font/segoeuil.ttf");
        companyName.setTypeface(face);

        TextView registerTv = (TextView) findViewById(R.id.registerTv);
        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignupActivity.this, SignupForm.class));
            }
        });

        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignupActivity.this, HomePage.class));
            }
        });

        emailLoginEv = (EditText) findViewById(R.id.emailLoginEv);
        passwordLoginEv = (EditText) findViewById(R.id.passwordLoginEv);

        Button signIn = (Button)findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBackground loginBackground = new loginBackground(LoginSignupActivity.this);
                loginBackground.execute(emailLoginEv.getText().toString().trim(), passwordLoginEv.getText().toString());
            }
        });

    }
}
