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
import com.example.donationapp.COMMON.ComplaintAdaptor;
import com.example.donationapp.COMMON.DonatorAdaptor;
import com.example.donationapp.COMMON.FillData;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.R;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Complaints extends AppCompatActivity {

    List<FillData> blist;
    String  username,cid;
    ListView listv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_complaints);
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
                    ComplaintAdaptor adapter= new ComplaintAdaptor(Complaints.this, blist);
                    listv.setAdapter(adapter);
                    registerForContextMenu(listv);
                    listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(Complaints.this);
                            dialog.setCancelable(false);
                            username = blist.get(position).getName();
                            cid = blist.get(position).getUid();
                            dialog.setTitle("Action ");
                            dialog.setMessage("Block The User Reported ID :"+username);
                            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                    blockuserreporter();

                                }
                            }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = dialog.create();
                            alert.show();
                        }
                    });//

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
                map.put("requestType", "getcomplaints");
                return map;
            }
        };
        queue.add(request);
    }
    public void blockuserreporter(){
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.d("******", response);

                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
startActivity(new Intent(getApplicationContext(),Complaints.class));
                } else {
                    Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();

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
                map.put("requestType", "Blockreporting");
                map.put("cid", cid);

                return map;
            }
        };
        queue.add(request);
    }



}