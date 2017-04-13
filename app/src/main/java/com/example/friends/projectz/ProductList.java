package com.example.friends.projectz;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {
    ArrayList<ProductDetailPojo> list;
    private ProductListAdapter adapter;
    private String usernameSession;
    private AlertDialog alertDialog;
    ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        usernameSession = "574612";
        productList = (ListView) findViewById(R.id.listView1);

        Intent callingIntent = getIntent();
        String alphaTest = callingIntent.getExtras().getString("mainObject");
        try {
            JSONObject jsonObject = new JSONObject(alphaTest);
            System.out.println("string--------------->>>"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("tag");
            for(int i=0; i<jsonArray.length(); i++){

            }
        } catch(Exception exe) {
            exe.printStackTrace();
        }


    }

    /*private void fun() {
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FlatDetailsBackground flatDetailsBackground = new FlatDetailsBackground(ProductList.this);
                flatDetailsBackground.execute("flatDetails",usernameSession, "12345");

            }
        });
    }*/
}

