package com.example.friends.projectz;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddLocation extends AppCompatActivity {
    private static final int PLACE_PICKER_REQUEST = 1;
    private Button placePick, postProperty;
    private EditText mName, mAddress, mAttributions, bedroomCount, bathroomCount, foodPref, mobileNum, areaSqft, rentInput, depositInput;
    private TextView hiddenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        placePick = (Button)findViewById(R.id.placePick);
        postProperty = (Button)findViewById(R.id.postProperty);
        placePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();

                    startActivityForResult(intentBuilder.build(AddLocation.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        mName = (EditText)findViewById(R.id.mName);
        mAddress = (EditText)findViewById(R.id.mAddress);
        mAttributions = (EditText)findViewById(R.id.mAttributions);
        bedroomCount = (EditText)findViewById(R.id.bedroomCount);
        bathroomCount = (EditText)findViewById(R.id.bathroomCount);
        foodPref = (EditText)findViewById(R.id.foodPref);
        mobileNum = (EditText)findViewById(R.id.mobileNum);
        areaSqft = (EditText)findViewById(R.id.areaSqft);
        rentInput = (EditText)findViewById(R.id.rentInput);
        depositInput = (EditText)findViewById(R.id.depositInput);
        hiddenView = (TextView)findViewById(R.id.hiddenView);

        postProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              submitData();
            }
        });

    }

    private void submitData() {
        BackgroundAddProperty backgroundAddProperty = new BackgroundAddProperty(AddLocation.this);
        backgroundAddProperty.execute("addLocation",mName.getText().toString(), mAddress.getText().toString(), mAttributions.getText().toString(), bathroomCount.getText().toString(), bathroomCount.getText().toString(), foodPref.getText().toString(), mobileNum.getText().toString(), areaSqft.getText().toString(), rentInput.getText().toString(), depositInput.getText().toString(), hiddenView.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final LatLng latLng = place.getLatLng();
            final CharSequence address = place.getAddress();
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            Geocoder geocoder;
            geocoder = new Geocoder(this, Locale.getDefault());
            final Double lat = latLng.latitude;
            final Double lon = latLng.longitude;
            List<Address> addresses = null; // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            try {
                addresses = geocoder.getFromLocation(lat,lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String addressX = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            String combination = addressX + "," + city + "," + state + "," + country + "," + postalCode + "," + knownName + "," + lat + "," + lon;

            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

            mName.setText(name);
            mAddress.setText(address);
            mAttributions.setText(String.valueOf(latLng.latitude)+","+String.valueOf(latLng.longitude));
            hiddenView.setText(combination);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
