package com.example.donationapp.DHome;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.COMMON.FillData;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.R;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class preference extends Fragment {

    List<FillData> blist;
    ImageView nopro;
    TextView Totalval,getdata;
    String reportmail,rid,maildidpass,addresspass,cakename,gg,shopm;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_preference, container, false);
        getdata = root.findViewById(R.id.listdata);
        Totalval=root.findViewById(R.id.Totalval);
        fakeproduct();

        return root;
    }
    public void fakeproduct() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String val="";
                Log.d("response : ", response);
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    blist = Arrays.asList(gson.fromJson(response, FillData[].class));
                    int i=0;
                    for(FillData s:blist){
                        String nn= s.getName().toString();
                        String bb= s.getId().toString();
                        Integer ss=Integer.valueOf(bb)/2;
                        val=val+"\n\n\n"+nn+"  \t\t\t\t\t Person Count   :"+ss;

                    }
                    getdata.setText(val);

                } else {
                    Toast.makeText(getContext(), "No Fake Product", Toast.LENGTH_SHORT).show();
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
                SharedPreferences shobjmail = getActivity().getSharedPreferences("profilepefer", MODE_PRIVATE);
                shopm=shobjmail.getString("loginname","");

                String uid=shobjmail.getString("id","");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "getpreference");
                map.put("shopp", shopm.toString().trim());
                map.put("sid",uid);
                return map;
            }
        };
        queue.add(request);
    }

}
