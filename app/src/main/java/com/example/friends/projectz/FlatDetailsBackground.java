package com.example.friends.projectz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Friends on 29-03-2017.
 */

public class FlatDetailsBackground  extends AsyncTask<String,String,String> {
    private AlertDialog alertDialog;
    private Context context;
    private SharedPreferences pref;
    private String usernameSession;
    FlatDetailsBackground(Context ctx) {
        context = ctx;
        pref = ctx.getSharedPreferences(BackgroundWorker.MyPref, Context.MODE_PRIVATE);
        usernameSession = pref.getString("usernameSession", "");
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "http://192.168.43.105/internal_query.php";
        String result = null;
        try {

            Log.d("ERRRRROOOOORRRRRRR=====",params[0]+params[1]+usernameSession);
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("trigger", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(usernameSession, "UTF-8") + "&" + URLEncoder.encode("product_id", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }
    @Override
    protected void onPostExecute(String result) {

        Log.d("alphabetagamma",result);
        Intent intent = new Intent(context, FlatDetail.class);
        intent.putExtra("mainObject",result.toString());
        context.startActivity(intent);

    }
}
