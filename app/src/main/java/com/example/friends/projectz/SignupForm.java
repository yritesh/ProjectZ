package com.example.friends.projectz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignupForm extends FragmentActivity {
    private ImageView backArrowSignupToolbar;
    private EditText signupEmailEv, signuppPhoneEv, signupPasswordEv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try{
           setContentView(R.layout.activity_signup_form);
       }
       catch (Exception e){
           e.printStackTrace();
       }
        signupEmailEv = (EditText)findViewById(R.id.signupEmailEv);
        signuppPhoneEv = (EditText)findViewById(R.id.signupPhoneEv);
        signupPasswordEv = (EditText)findViewById(R.id.signupPasswordEv);

        Button signupButton = (Button)findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupBackground signupBackground = new SignupBackground(SignupForm.this);
                signupBackground.execute(signupEmailEv.getText().toString().trim(), signuppPhoneEv.getText().toString(), signupPasswordEv.getText().toString());

            }
        });

        backArrowSignupToolbar = (ImageView)findViewById(R.id.backArrowSignupToolbar);
                backArrowSignupToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupForm.this,LoginSignupActivity.class));
            }
        });
    }
}
