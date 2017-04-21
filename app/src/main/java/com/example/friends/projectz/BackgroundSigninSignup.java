package com.example.friends.projectz;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Friends on 21-04-2017.
 */

public class BackgroundSigninSignup extends AsyncTask<String, Integer, String> {
    private String combination;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public static final String MyPref = "MyPref";
    private Context context;

    public BackgroundSigninSignup(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        combination = params[0];
        List<String> credentials = Arrays.asList(combination.split(","));
        //[0]=email, [1]=displayName, [2]=familyName, [3]=givenName, [4]=Id, [5]=PhotoUrl

        pref = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();

        RequestParams mParams = new RequestParams();
        mParams.put("trigger", "checkSignupStatus");
        mParams.put("email", credentials.get(0));
        mParams.put("array", credentials.get(1));
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.0.100/internal_query.php", mParams, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("tag");

                        final JSONObject rec = jsonArray.getJSONObject(0);

                        editor.putString("usernameSession", rec.getString("usernameSession"));
                        editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return combination;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Intent intent = new Intent(context, HomePage.class);
        intent.putExtra("credentials", combination);
        context.startActivity(intent);
    }
}
