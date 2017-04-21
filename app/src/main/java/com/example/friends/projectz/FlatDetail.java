package com.example.friends.projectz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FlatDetail extends FragmentActivity implements View.OnClickListener {
    private CardView callView;
    private TextView noOfRooms, address, rent, deposite, size, houseId, type, houseFor, bedrooms, bathrooms, foodPrefernce, location,
            ownersName, ownerNumber;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flat_detail);

        noOfRooms = (TextView) findViewById(R.id.textView11);
        address = (TextView) findViewById(R.id.textView10);
        rent = (TextView) findViewById(R.id.textView4);
        deposite = (TextView) findViewById(R.id.textView38);
        size = (TextView) findViewById(R.id.textView14);
        houseId = (TextView) findViewById(R.id.textView19);
        type = (TextView) findViewById(R.id.textView23);
        houseFor = (TextView) findViewById(R.id.textView26);
        bedrooms = (TextView) findViewById(R.id.textView29);
        bathrooms = (TextView) findViewById(R.id.textViewbathno);
        foodPrefernce = (TextView) findViewById(R.id.textView33);
        location = (TextView) findViewById(R.id.textView36);
        ownersName = (TextView) findViewById(R.id.textView6);
        ownerNumber = (TextView) findViewById(R.id.textView16);

        callView = (CardView) findViewById(R.id.cardView4);
        callView.setOnClickListener(this);
        ImageView imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView7.setOnClickListener(this);

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("mainObject"));
            noOfRooms.setText(jsonObject.getString("bedroomCount")+"BHK"+" "+jsonObject.getString("buildingType"));
            address.setText(jsonObject.getString("postedByLocation"));
            rent.setText(jsonObject.getString("rent"));
            deposite.setText(jsonObject.getString("deposit"));
            size.setText(jsonObject.getString("sizeSqft")+"Sqft");
            type.setText(jsonObject.getString("buildingType"));
            houseFor.setText(jsonObject.getString("availableFor"));
            bedrooms.setText(jsonObject.getString("bedroomCount"));
            bathrooms.setText(jsonObject.getString("bathroomCount"));
            foodPrefernce.setText(jsonObject.getString("foodPreferences"));
            location.setText(jsonObject.getString("locality"));
            ownersName.setText(jsonObject.getString("postedByName"));
            ownerNumber.setText(jsonObject.getString("postedByMobile"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Json Object--------------->>>"+jsonObject);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardView4:
                if(Build.VERSION.SDK_INT >= 23) {
                    permission();
                } else {
                    try {
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+ownerNumber.getText().toString())));
                    } catch(SecurityException sex) {
                        sex.printStackTrace();
                    }
                }
        }
    }

    public void permission() {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
            permissionsNeeded.add("Phone");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 124);
                }
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 124);
            }
            return;
        }
    }
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            } else {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ownerNumber.getText().toString())));
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        switch (requestCode) {
            case 124: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResult[i]);
                if (perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(this, "All Permissions Granted!!", Toast.LENGTH_SHORT).show();
                    try {
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ownerNumber.getText().toString())));
                    } catch(SecurityException sex) {
                        sex.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Some Permissions Denied!!", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        }
    }

}
