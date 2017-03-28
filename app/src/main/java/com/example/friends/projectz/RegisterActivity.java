package com.example.friends.projectz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class RegisterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextInputLayout tilName,tilEmail,tilMobile,tilPass,tilAddress;
    EditText name,email,mobile,pass,address;
    RadioGroup userTypeGroup;
    RadioButton usertype;
    Button btn_signup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //transparent statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        btn_signup = (Button)findViewById(R.id.btn_signup);
        tilName = (TextInputLayout) findViewById(R.id.nameLayout);
        tilEmail = (TextInputLayout) findViewById(R.id.emailLayout);
        tilMobile = (TextInputLayout) findViewById(R.id.mobileLayout);
        tilPass = (TextInputLayout) findViewById(R.id.passLayout);
        tilAddress = (TextInputLayout) findViewById(R.id.addressLayout);

        name = (EditText)findViewById(R.id.input_name);
        email = (EditText)findViewById(R.id.input_email);
        mobile = (EditText)findViewById(R.id.input_mobile);
        pass = (EditText)findViewById(R.id.input_password);
        address = (EditText)findViewById(R.id.input_address);
        userTypeGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int selectedId = userTypeGroup.getCheckedRadioButtonId();
        usertype = (RadioButton)findViewById(selectedId);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().length()>5)
                    if(address.getText().toString().length()>20)
                        if(email.getText().toString().length()>0)
                            if(email.getText().toString().trim().matches(emailPattern))
                                if(mobile.getText().toString().matches("[0-9]{10}"))
                                    if(pass.getText().toString().length()>6) {
                                        String query_type = "signup";
                                        BackgroundWorker backgroundWorker = new BackgroundWorker(RegisterActivity.this);
                                        backgroundWorker.execute(query_type,name.getText().toString(), email.getText().toString().trim(), mobile.getText().toString(), pass.getText().toString(), address.getText().toString(), usertype.getText().toString());
                                    }
                                    else tilPass.setError("Password must be atleast 6 characters long and this field can't be left blank");
                                else tilMobile.setError("Please enter a valid mobile number ");
                            else tilEmail.setError("Please enter a valid email id");
                        else tilEmail.setError("Email field can't be left blank");
                    else tilAddress.setError("Address must be atleast 20 characters long and this field can't be left blank");
                else tilName.setError("Name must be atleast 6 characters long");
            }
        });
        /*
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkOtherReq(String.valueOf(v.getId()));
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkOtherReq(String.valueOf(v.getId()));
            }
        });
        mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkOtherReq(String.valueOf(v.getId()));
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkOtherReq(String.valueOf(v.getId()));
            }
        });
        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkOtherReq(String.valueOf(v.getId()));
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


  /*  private void OnEditClick(View view){
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkOtherReq(String.valueOf(v.getId()));
            }
        });
    }


    private void checkOtherReq(String type){
        type = getResources().getResourceEntryName(Integer.parseInt(type));
        switch (type){
            case "input_name":
                if(name.getText().toString().length()>5)
                    tilName.setError(null);
                else tilName.setError("Name must be atleast 6 characters long");
                break;
            case "input_email":if(email.getText().toString().length()>0)
            {
                if(email.getText().toString().trim().matches(emailPattern))
                    tilEmail.setError(null);
                else tilEmail.setError("Please enter a valid email id");
            }
            else tilEmail.setError("Email field can't be left blank");
                break;

            case "input_mobile":if(mobile.getText().toString().matches("[0-9]{10}"))
                tilMobile.setError(null);
            else tilMobile.setError("Please enter a valid mobile number ");
                break;
            case "input_password":if(pass.getText().toString().length()>6)
                tilPass.setError(null);
            else tilPass.setError("Password must be atleast 6 characters long and this field can't be left blank");
                break;
            case "input_address":if(address.getText().toString().length()>20)
                tilAddress.setError(null);
            else tilAddress.setError("Address must be atleast 20 characters long and this field can't be left blank");
                break;
        }
    }*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.buy) {
            // handle buy action
        } else if (id == R.id.rent) {

        } else if (id == R.id.fav_properties) {

        } else if (id == R.id.contacted) {

        } else if (id == R.id.contact_agent) {

        } else if (id == R.id.login_signup) {
           Context context = getApplicationContext();
           startActivity(new Intent(context, RegisterActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
