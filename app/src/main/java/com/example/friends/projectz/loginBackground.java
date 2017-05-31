package com.example.friends.projectz;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Friends on 14-04-2017.
 */

public class loginBackground extends AsyncTask<String, Integer, String> {
    Context context;
    AlertDialog alertDialog;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public static final String MyPref = "MyPref";

    private Context applicationContext;

    loginBackground(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        pref = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();

        String login_url = "http://sumeetfoundation.org/projectz/login.php";
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
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
        JSONObject mainObject = null;
        try {
            mainObject = new JSONObject(result);

            if(mainObject.getString("status").contentEquals("Successful")) {
                editor.putString("usernameSession", mainObject.getString("usernameSession"));
                editor.commit();
                Toast.makeText(context,"Login Successful", Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, HomePage.class);
                context.startActivity(i);

            }else {
                alertDialog.setMessage("Sign In unsuccessful");
                alertDialog.show();

            }
            Intent intent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            Notification noti = new Notification.Builder(context)
                    .setTicker("Project Z")
                    .setContentTitle("Signing In")
                    .setContentText("Sign In status: "+ mainObject.getString("status"))
                    .setSmallIcon(R.drawable.noti)
                    .setLargeIcon(((BitmapDrawable)context.getResources().getDrawable(R.drawable.noti)).getBitmap())
                    .setContentIntent(pendingIntent).getNotification();

            noti.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0,noti);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
