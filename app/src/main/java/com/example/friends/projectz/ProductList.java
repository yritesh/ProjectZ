package com.example.friends.projectz;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProductList extends AppCompatActivity {
    private ImageView imageView10;
    private String usernameSession;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_detail);
        imageView10 = (ImageView)findViewById(R.id.imageView10);
        usernameSession = "574612";
        fun();
        Intent callingIntent = getIntent();
        String alphaTest = callingIntent.getExtras().getString("mainObject");
        alertDialog = new AlertDialog.Builder(ProductList.this).create();
        alertDialog.setTitle("Data from internal_query.php (JSON)");
        alertDialog.setMessage(alphaTest);
        alertDialog.show();
    }

    private void fun() {
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FlatDetailsBackground flatDetailsBackground = new FlatDetailsBackground(ProductList.this);
                flatDetailsBackground.execute("flatDetails",usernameSession, "12345");

            }
        });
    }
}

