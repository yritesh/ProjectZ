package com.example.friends.projectz;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by shivam.dubey on 2/2/2017.
 */

public class SlidingImageAdapter extends PagerAdapter{
    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Activity activity;

    public SlidingImageAdapter(Activity activity, ArrayList<Integer> images) {
        this.activity = activity;
        this.images = images;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public int getCount() {
        return images.size();
    }
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout,null);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        imageView.setImageResource(images.get(position));
        view.addView(imageLayout, 0);
        return imageLayout;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
