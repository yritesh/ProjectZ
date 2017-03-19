package com.example.friends.projectz;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;


public class FlatDetail extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] images = {R.drawable.photo1, R.drawable.photo2, R.drawable.photo3};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flat_detail);

        ImageView imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FlatDetail.this, RegisterActivity.class));
            }
        });
        slidingImage();
    }

    //Sliding Images//
    public void slidingImage() {
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

    }
}
