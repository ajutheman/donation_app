package com.example.donationapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.ADmin.AdminHome;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.DHome.DonatorHome;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
public class Login extends AppCompatActivity {

    TextView lin;
    TextInputEditText unam,email;
    String UN,EM;
    Button bb;
    TextView forgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        unam=findViewById(R.id.uname);
        bb=findViewById(R.id.bbtn);
        email=findViewById(R.id.passs);

        ActionBar bar=getSupportActionBar();
        bar.hide();
        lin=findViewById(R.id.linktooption);
        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SElectOption.class));
            }
        });
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_PHONE_STATE

        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }





        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validate();
            }
        });
    }

    private void Validate() {

        UN = unam.getText().toString();
        EM = email.getText().toString();

        if (UN.isEmpty()) {

            Snackbar.make(unam, "Enter name", Snackbar.LENGTH_LONG)
                    .setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
        }

        else if (!UN.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

            Snackbar.make(unam, "Invalid Email Id ", Snackbar.LENGTH_LONG)
                    .setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
        }
        else if (EM.isEmpty()) {
            Snackbar.make(unam, "Enter Password", Snackbar.LENGTH_LONG)
                    .setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();

        }
        else {
            login();
        }
    }

    private void login() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);

                if (!response.trim().equals("failed")) {

                    String data = response;
                    String respArr[] = data.trim().split("#");

                    SharedPreferences.Editor editor = getSharedPreferences("profilepefer", MODE_PRIVATE).edit();
                    editor.putString("u_id", "" + respArr[0]);
                    editor.putString("type", "" + respArr[1]);
                    editor.commit();

                    if (respArr[1].trim().equals("user")) {
                        startActivity(new Intent(getApplicationContext(), DonatorHome.class));
                        Toast.makeText(getApplicationContext(), "HOD Login", Toast.LENGTH_SHORT).show();
                    }


                    else if (respArr[1].trim().equals("admin")) {
                        startActivity(new Intent(getApplicationContext(), AdminHome.class));
                        Toast.makeText(getApplicationContext(), "Student Login", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Login.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my Error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> map = new HashMap<String, String>();
//                SharedPreferences sp=getSharedPreferences("booking_info", Context.MODE_PRIVATE);
                map.put("requestType", "login");
                map.put("U_name", UN);
                map.put("P_swd", EM);

                return map;
            }
        };
        queue.add(request);
    }

    public static boolean hasPermissions(Login context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "App is going to exit", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(1000);
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }catch(Exception e){

        }
    }
}