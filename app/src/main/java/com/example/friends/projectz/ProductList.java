package com.example.friends.projectz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProductList extends AppCompatActivity {
    private ImageView imageView10;
    private String usernameSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_detail);
        imageView10 = (ImageView)findViewById(R.id.imageView10);
        usernameSession = "574612";
        fun();
    }

    private void fun() {
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FlatDetailsBackground flatDetailsBackground = new FlatDetailsBackground(ProductList.this);
                flatDetailsBackground.execute(usernameSession, "12345");

            }
        });
    }
}

