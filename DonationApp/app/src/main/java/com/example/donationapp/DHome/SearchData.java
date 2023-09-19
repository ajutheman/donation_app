package com.example.donationapp.DHome;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.COMMON.FillData;
import com.example.donationapp.COMMON.SingleNameAdaptor;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.Login;
import com.example.donationapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchData extends Fragment {

    ListView patientlist,ssss;
    List<FillData> arrayList,arrayList2;
    SingleNameAdaptor adpater,adpater2;
    EditText ed;
    ImageView imgicon;
    String familyval;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.search_data, container, false);
        patientlist = view.findViewById(R.id.mypatienntlist);
        arrayList=new ArrayList<FillData>();
        arrayList2=new ArrayList<FillData>();

        adpater=new SingleNameAdaptor(getActivity(), arrayList);
        adpater2=new SingleNameAdaptor(getActivity(), arrayList);

        ssss=view.findViewById(R.id.ssss);




        ed=view.findViewById(R.id.searchid);
        imgicon=view.findViewById(R.id.seaid);
        imgicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ser=ed.getText().toString();
                if(ser.isEmpty()) {
                    Toast.makeText(getContext(), "Do Search in box", Toast.LENGTH_SHORT).show();
                }
                else {
                    GetPlantsByFamily(ser);
                    getfooddetails(ser);
                }
            }
        });
        return view;
    }

    public void GetPlantsByFamily(String ser)
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******",response);
                if(!response.trim().equals("failed"))
                {
//                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson=new Gson();
                    arrayList = Arrays.asList(gson.fromJson(response, FillData[].class));
                    SingleNameAdaptor adapter = new SingleNameAdaptor(getActivity(),arrayList);
                    patientlist.setAdapter(adapter);
                    registerForContextMenu(patientlist);
                }
                else
                {
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
                map.put("requestType","SearchDonation");
                map.put("search",ser);
                return map;
            }
        };
        queue.add(request);
    }
    //
    public void getfooddetails(String ser)
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******",response);
                if(!response.trim().equals("failed"))
                {
//                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    Gson gson=new Gson();
                    arrayList2 = Arrays.asList(gson.fromJson(response, FillData[].class));
                    SingleNameAdaptor adapter2 = new SingleNameAdaptor(getActivity(),arrayList2);
                    ssss.setAdapter(adapter2);
                    registerForContextMenu(ssss);
                }
                else
                {
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

                map.put("requestType","SearchFood");
                map.put("search",ser);

                return map;
            }
        };
        queue.add(request);
    }
}
