package com.example.donationapp.ADmin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.COMMON.DonatorAdaptor;
import com.example.donationapp.COMMON.FillData;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.R;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Donators extends AppCompatActivity {
    List<FillData> blist;
    String uid, username;
ListView listv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_donators);
listv=findViewById(R.id.userlidtlviewid);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        CustomerList();
    }

    public void CustomerList() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response : ", response);
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    blist = Arrays.asList(gson.fromJson(response, FillData[].class));
                    Log.d("Data", "" + blist.get(0).getEmail());
                    DonatorAdaptor adapter= new DonatorAdaptor(Donators.this, blist);
                    listv.setAdapter(adapter);
                    registerForContextMenu(listv);


                } else {

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
                map.put("requestType", "getdonatorlist");
                return map;
            }
        };
        queue.add(request);
    }



}