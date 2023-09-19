package com.example.donationapp.DHome;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Food_Request extends Fragment {

    ListView myreqyest, New_request;
    List<FillData> arrayList, arrayList2;
    SingleNameAdaptor adpater, adpater2;
    String rid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food__request, container, false);
        New_request = view.findViewById(R.id.mypatienntlist);
        arrayList = new ArrayList<FillData>();
        arrayList2 = new ArrayList<FillData>();

        adpater = new SingleNameAdaptor(getActivity(), arrayList);
        adpater2 = new SingleNameAdaptor(getActivity(), arrayList);

        myreqyest = view.findViewById(R.id.ssss);

        New_request.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rid = arrayList2.get(position).getUid();
//                blockuserreporter();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are You Confirm Request "+arrayList2.get(position).getName())
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                blockuserreporter();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Donation Request");
                alert.show();
            }
        });

        myreqyest();
        New_request();
        return view;
    }

    public void myreqyest() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
//                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    arrayList = Arrays.asList(gson.fromJson(response, FillData[].class));
                    DonatorAdaptor adapter = new DonatorAdaptor(getActivity(), arrayList);
                    myreqyest.setAdapter(adapter);
                    registerForContextMenu(myreqyest);
                } else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences shobjmail = getActivity().getSharedPreferences("profilepefer", MODE_PRIVATE);
                String uid = shobjmail.getString("u_id", "");

                map.put("requestType", "Foodmyreqyest");
                map.put("id", uid);
                return map;
            }
        };
        queue.add(request);
    }

    //
    public void New_request() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
//                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson = new Gson();
                    arrayList2 = Arrays.asList(gson.fromJson(response, FillData[].class));
                    DonatorAdaptor adapter2 = new DonatorAdaptor(getActivity(), arrayList2);
                    New_request.setAdapter(adapter2);
                    registerForContextMenu(New_request);
                } else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "FoodNew_request");
                SharedPreferences shobjmail = getActivity().getSharedPreferences("profilepefer", MODE_PRIVATE);
                String uid = shobjmail.getString("u_id", "");
                map.put("id", uid);

                return map;
            }
        };
        queue.add(request);
    }

    public void blockuserreporter() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.d("******", response);

                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Food_Request fragment = new Food_Request();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment_content_donator_home, fragment)
                            .commit();
                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "" + response, Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "my Error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "foodupdatedProductItems");
                map.put("rid", rid);

                return map;
            }
        };
        queue.add(request);
    }

}
