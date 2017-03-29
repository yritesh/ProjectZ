package com.example.friends.projectz;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class FlatDetail extends RegisterActivity implements View.OnClickListener {
    CardView callView;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] images = {R.drawable.photo1, R.drawable.photo2, R.drawable.photo3};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flat_detail);
        Intent callingIntent = getIntent();
        String alphaTest = callingIntent.getExtras().getString("mainObject");
        alertDialog = new AlertDialog.Builder(FlatDetail.this).create();
        alertDialog.setTitle("Data from internal_query.php (JSON)");
        alertDialog.setMessage(alphaTest);
        alertDialog.show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView)findViewById(R.id.textView11);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlatDetail.this,RegisterActivity.class));
            }
        });
        //slidingImage();
        callView = (CardView) findViewById(R.id.cardView4);
        callView.setOnClickListener(this);
        ImageView imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView7.setOnClickListener(this);

    }

    //Sliding Images//
   /*public void slidingImage() {
        for (int i = 0; i < images.length; i++) {
            ImagesArray.add(images[i]);
        }
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImageAdapter(FlatDetail.this, ImagesArray));

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {

            @Override
            public void run() {
                if (currentPage > images.length - 1) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardView4:
                if(Build.VERSION.SDK_INT >= 23) {
                    permission();
                } else {
                    try {
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:9540959683")));
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
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:9540959683")));
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
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:8506807268")));
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
