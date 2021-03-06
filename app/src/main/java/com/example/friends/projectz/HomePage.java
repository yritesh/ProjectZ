package com.example.friends.projectz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.example.friends.projectz.R.id.map;


public class HomePage extends FragmentActivity implements LocationListener, OnMapReadyCallback {
    private SharedPreferences pref;
    private static int REQUEST_COARSE_LOCATION = 99;
    private double lat;
    private double lon;
    private static String combination;
    private LocationManager locationManager;
    private boolean canGetLocation;
    private Double latlon;
    private static String usernameSession;
    private GoogleMap mMap;
    private Marker mMarker;
    private ImageView profileImage;
    private AlertDialog alertDialog;
    private String city, credentials;
    private Uri photoUri;
    private TextView cityCurrent, addCurrent;
    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        credentials = intent.getStringExtra("credentials");
        List<String> cArray = Arrays.asList(credentials.split(","));
        photoUri = Uri.parse(cArray.get(5));

        cityCurrent = (TextView)findViewById(R.id.cityCurrent);
        addCurrent = (TextView)findViewById(R.id.addCurrent);

        imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton)findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton)findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton)findViewById(R.id.imageButton5);
        imageButton6 = (ImageButton)findViewById(R.id.imageButton6);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng x = new LatLng(28.704059,77.102490);
                imageClick(x);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng x = new LatLng(27.176670,78.008075);
                imageClick(x);
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng x = new LatLng(17.385044,78.486671);
                imageClick(x);
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng x = new LatLng(27.492413,77.673673);
                imageClick(x);
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng x = new LatLng(12.971599,77.594563);
                imageClick(x);
            }
        });
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng x = new LatLng(22.572646,88.363895);
                imageClick(x);
            }
        });
        pref = getSharedPreferences(BackgroundSigninSignup.MyPref, Context.MODE_PRIVATE);
        usernameSession = pref.getString("usernameSession", "");
        System.out.println("--------------------------------usernameeeeee"+usernameSession);

        alertDialog = new AlertDialog.Builder(HomePage.this,R.style.MyAlertDialogStyle).setPositiveButton("OK", null).setNegativeButton("Cancel", null).create();
        alertDialog.setTitle("Profile");
        profileImage = (ImageView)findViewById(R.id.profileImage);
        Picasso.with(this).load(photoUri).into(profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameSession != null){
                    try {
                        Intent intent1 = new Intent(HomePage.this, ProfilePage.class);
                        intent1.putExtra("credentials",credentials);
                        startActivity(intent1);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else {

                    startActivity(new Intent(HomePage.this, SignInActivity.class));
                }
            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000, 1, this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        String msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


    public String getLocation() {
        Geocoder geocoder;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            locationManager = (LocationManager) getBaseContext()
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                Location location = null;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            2,
                            1, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                2,
                                1, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            combination = address + "," + city + "," + state + "," + country + "," + postalCode + "," + knownName + "," + lat + "," + lon;

            cityCurrent.setText(city);
            addCurrent.setText(state+", "+country);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return combination;
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        mMap = map;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            } else {

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(this, "Location permission required for better property options around you.", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);

            }
        } else {

            String combination = getLocation();
            System.out.println("-----------------------"+combination);
            if(combination == null){
                city = "Delhi";
            }else {
                List<String> locationArray = Arrays.asList(combination.split(","));
                city = locationArray.get(1);
            }
            RequestParams params = new RequestParams();
            params.put("trigger", "listByCity");
            params.put("city", city);
            AsyncHttpClient client = new AsyncHttpClient();
            client.post("http://sumeetfoundation.org/projectz/internal_query.php", params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, String response) {

                    try{
                        System.out.println("-----------------------"+statusCode + "," + response);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("tag");
                        System.out.println(jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject rec = jsonArray.getJSONObject(i);
                            String locationMap = rec.getString("locationMap");
                            String bhk = rec.getString("bedroomCount");
                            String btype = rec.getString("buildingType");
                            String rent = rec.getString("rent");
                            String productId = rec.getString("productId");
                            List<String> locationArray = Arrays.asList(locationMap.split(","));

                            LatLng alpha = new LatLng(Double.valueOf(locationArray.get(0)), Double.valueOf(locationArray.get(1)));
                            if (ActivityCompat.checkSelfPermission(HomePage.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomePage.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            IconGenerator iconGenerator = new IconGenerator(HomePage.this);
                            iconGenerator.setStyle(IconGenerator.STYLE_RED);
                            Bitmap bitmap = iconGenerator.makeIcon('\u20B9' + rent);

                            mMarker = mMap.addMarker(new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                    .title(bhk + " BHK " + btype)
                                    .snippet("Rent:" + rent)
                                    .position(alpha));



                            mMarker.setTag(productId);
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    String productId = (String) marker.getTag();
                                    FlatDetailsBackground flatDetailsBackground = new FlatDetailsBackground(HomePage.this);
                                    flatDetailsBackground.execute("flatDetails", productId);
                                    return false;
                                }
                            });
                            }
                        }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            LatLng alpha = new LatLng(lat, lon);
            System.out.println(alpha);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(alpha,10));

    }

}
public void imageClick(LatLng alpha){
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alpha,10));
    }
}
