package com.example.donationapp.DHome;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.example.donationapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewDonationList extends Fragment {

    ListView patientlist,ssss;
    List<FillData> arrayList,arrayList2;
    SingleNameAdaptor adpater,adpater2;
    String familyval;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_donation_list, container, false);
        patientlist = view.findViewById(R.id.mypatienntlist);
        arrayList=new ArrayList<FillData>();
        arrayList2=new ArrayList<FillData>();

        adpater=new SingleNameAdaptor(getActivity(), arrayList);
        adpater2=new SingleNameAdaptor(getActivity(), arrayList);

        ssss=view.findViewById(R.id.ssss);
        ssss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences.Editor editor =getActivity(). getSharedPreferences("getfoodshred", MODE_PRIVATE).edit();
                editor.putString("item", "food");
                editor.commit();

                Bundle bundle=new Bundle();
                FillData patientdetai = arrayList2.get(position);
                bundle.putParcelable("clicked_item", (Parcelable) patientdetai);
                ViewFullDetails plantDetails=new ViewFullDetails();
                plantDetails.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_content_donator_home,plantDetails).commit();

            }
        });
        patientlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor =getActivity(). getSharedPreferences("getfoodshred", MODE_PRIVATE).edit();
                editor.putString("item", "");
                editor.commit();
                Bundle bundle=new Bundle();
                FillData patientdetai = arrayList.get(position);
                bundle.putParcelable("clicked_item", (Parcelable) patientdetai);
                ViewFullDetails plantDetails=new ViewFullDetails();
                plantDetails.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_content_donator_home,plantDetails).commit();

            }
        });

        GetPlantsByFamily();
        getfooddetails();
        return view;
    }

    public void GetPlantsByFamily()
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
                map.put("requestType","GetallnamesList");
                SharedPreferences shobjmail = getActivity().getSharedPreferences("profilepefer", MODE_PRIVATE);
                String uid=shobjmail.getString("u_id","");
                map.put("id",uid);
                return map;
            }
        };
        queue.add(request);
    }
//
public void getfooddetails()
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
            SharedPreferences shobjmail = getActivity().getSharedPreferences("profilepefer", MODE_PRIVATE);
            String uid=shobjmail.getString("u_id","");

            Map<String, String> map = new HashMap<String, String>();
            map.put("requestType","getfooddetails");
            map.put("id",uid);
            return map;
        }
    };
    queue.add(request);
}
}
