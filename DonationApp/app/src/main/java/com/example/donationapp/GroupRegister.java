package com.example.donationapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.COMMON.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class GroupRegister extends AppCompatActivity {

    EditText name, email, phone, pass,ADD;
    Button regbtns;
    TextView txtid;
    RadioGroup radiogb;
    RadioButton selectbtn;
    String NAME, EMAIL, PHONE, PASS, add, GEN;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_group_register);
        ActionBar bar=getSupportActionBar();

        bar.hide();
        ADD=findViewById(R.id.addressid);
        name = findViewById(R.id.nameid);
        email = findViewById(R.id.emailid);
        phone = findViewById(R.id.phoneid);
        pass = findViewById(R.id.passwordid);
        txtid= findViewById(R.id.linktooption);
        regbtns = findViewById(R.id.bbtn);
        txtid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        regbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add=ADD.getText().toString();
                NAME = name.getText().toString();
                EMAIL = email.getText().toString();
                PHONE = phone.getText().toString();
                PASS = pass.getText().toString();

                Log.v("jij", PASS);

                if (NAME.isEmpty()) {
                    Snackbar.make(name, "Fill Name", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (EMAIL.isEmpty()) {
                    Snackbar.make(email, "Fill Email Id", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!EMAIL.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                    Snackbar.make(email, "Fill Correct Format Mail Id", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (PHONE.isEmpty()) {
                    Snackbar.make(phone, "Fill  Phone Number", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (PHONE.length() != 10) {
                    Snackbar.make(phone, "Fill Correct Phone Number", Snackbar.LENGTH_LONG)
                            .setAction("DISMISS", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {
                    Register();

                }
            }
        });

    }

    public void Register() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.d("******", response);

                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(GroupRegister.this, "Register Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed" + response, Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "groupuser");
                map.put("name", NAME.trim());
                map.put("email", EMAIL.trim());
                map.put("phone", PHONE.trim());
                map.put("pass", PASS.trim());
                map.put("add", add.trim());

                return map;
            }
        };
        queue.add(request);
    }
}
