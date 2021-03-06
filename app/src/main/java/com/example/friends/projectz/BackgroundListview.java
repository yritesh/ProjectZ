package com.example.friends.projectz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
 * Created by Friends on 02-04-2017.
 */

    public class BackgroundListview extends AsyncTask<String,String,String> {
    private AlertDialog alertDialog;
    private Context context;
    BackgroundListview(Context ctx) {
        context = ctx;

    }
    @Override
    protected String doInBackground(String... params) {

        String login_url = "http://sumeetfoundation.org/projectz/internal_query.php";
        String result = null;
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("trigger", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&" + URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&" + URLEncoder.encode("postalCode", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8") + "&" + URLEncoder.encode("knownName", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8");
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
        Intent intent = new Intent(context, ProductList.class);
        intent.putExtra("mainObject",result.toString());
        context.startActivity(intent);

    }
}
