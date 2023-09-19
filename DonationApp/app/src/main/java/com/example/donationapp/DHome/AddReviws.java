package com.example.donationapp.DHome;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.R;

import java.util.HashMap;
import java.util.Map;


public class AddReviws extends Fragment {

    String proof[] = {"seller", "delivery"};
    String feedto,fn,fd,rba,ba;
    EditText fname,fdes;
    Button fbtn;
    RatingBar rb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_reviws, container, false);
        fname = root.findViewById(R.id.fname);
        fdes = root.findViewById(R.id.feeddes);
        fbtn = root.findViewById(R.id.feedbtn);
        rb = root.findViewById(R.id.starid);

        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), ""+down, Toast.LENGTH_SHORT).show();

                fn = fname.getText().toString();
                fd = fdes.getText().toString();
                float rbar = rb.getRating();
                ba=String.valueOf(rbar);
                if (fn.isEmpty()) {
                    fname.requestFocus();
                    fname.setError("Enter Feeder Name");
                } else if (fd.isEmpty()) {
                    fdes.requestFocus();
                    fdes.setError("Enter Description");
                } else {
                    sendfed();

                }
            }
        });

        return root;
    }
    public void sendfed(){
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.d("******", response);

                if (!response.trim().equals("failed")) {
                    Log.d("******", response);
                    Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
//                    Fragment frag = new AddReviws();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_donator_home, frag).commit();

                    startActivity(new Intent(getContext(),DonatorHome.class));
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
                map.put("requestType", "Feedback");
                SharedPreferences shobjmail = getActivity().getSharedPreferences("profilepefer", MODE_PRIVATE);
                String uid=shobjmail.getString("u_id","");

                map.put("uid", uid.trim());

                map.put("fname", fn.trim());
                map.put("fdes", fd.trim());
                map.put("star", ba.toString().trim());

                return map;
            }
        };
        queue.add(request);
    }
}
