package com.example.friends.projectz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shivam on 04-04-2017.
 */

public class ProductListAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ProductDetailPojo> data;
    private static LayoutInflater inflater=null;
    UserHolder holder;
    public ProductListAdapter(Activity a, ArrayList<ProductDetailPojo> d) {
        activity=a;
        data=d;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        holder = null;
        if(row==null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.product_list_detail, null);
            holder = new UserHolder();
            holder.productName = (TextView) row.findViewById(R.id.textView7);
            holder.productAddress = (TextView) row.findViewById(R.id.textView8);
            holder.productImage = (ImageView) row.findViewById(R.id.imageView10);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        holder.productName.setText(data.get(position).getProductName());
        holder.productImage.setImageBitmap(data.get(position).getProductImage());
        holder.productAddress.setText(data.get(position).getProductAdress());
        return row;
    }

    static class UserHolder {
        TextView productName, productAddress;
        ImageView productImage;
    }
}
