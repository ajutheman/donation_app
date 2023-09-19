package com.example.donationapp.ADmin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.COMMON.DonatorAdaptor;
import com.example.donationapp.COMMON.FillData;
import com.example.donationapp.COMMON.SingleNameAdaptor;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationList extends AppCompatActivity {
    ListView myreqyest, New_request;
    List<FillData> arrayList, arrayList2;
    SingleNameAdaptor adpater, adpater2;
    String rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(com.example.donationapp.R.layout.activity_donation_list);
        New_request = findViewById(R.id.mypatienntlist);
        arrayList = new ArrayList<FillData>();
        arrayList2 = new ArrayList<FillData>();
        ActionBar bar = getSupportActionBar();
        bar.hide();
        adpater = new SingleNameAdaptor(DonationList.this, arrayList);
        adpater2 = new SingleNameAdaptor(DonationList.this, arrayList);

        myreqyest =findViewById(R.id.ssss);


        donationrequest();
       foodrequest();

    }
    public void donationrequest() {
        RequestQueue queue = Volley.newRequestQueue(DonationList.this);
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
//                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    arrayList = Arrays.asList(gson.fromJson(response, FillData[].class));
                    DonatorAdaptor adapter = new DonatorAdaptor(DonationList.this, arrayList);
                    myreqyest.setAdapter(adapter);
                    registerForContextMenu(myreqyest);
                } else {
                    Toast.makeText(DonationList.this, "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DonationList.this, "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("requestType", "donationrequestAdmin");
                return map;
            }
        };
        queue.add(request);
    }


    public void foodrequest() {
        RequestQueue queue = Volley.newRequestQueue(DonationList.this);
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
//                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    arrayList2 = Arrays.asList(gson.fromJson(response, FillData[].class));
                    DonatorAdaptor adapter2 = new DonatorAdaptor(DonationList.this, arrayList2);
                    New_request.setAdapter(adapter2);
                    registerForContextMenu(New_request);
                } else {
                    Toast.makeText(DonationList.this, "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DonationList.this, "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "foodrequest_Admin");

                return map;
            }
        };
        queue.add(request);
    }

}