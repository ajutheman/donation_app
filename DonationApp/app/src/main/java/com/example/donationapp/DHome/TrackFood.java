package com.example.donationapp.DHome;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.COMMON.FillData;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackFood extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match

    private GoogleMap mMap;
    FillData requestPojo;
    double sel_lati, sel_longi;
    String rqst_id, FUEL_PRICE;
    Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_food_track, container, false);

        final Bundle b = this.getArguments();

        requestPojo = b.getParcelable("clicked_item");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }
//    ************


    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        sel_lati = Double.parseDouble(requestPojo.getLat());
        sel_longi = Double.parseDouble(requestPojo.getLot());

        int height = 120;
        int width = 120;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.contact);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        mMap.addMarker(new MarkerOptions().position(new LatLng(sel_lati, sel_longi)).title(requestPojo.getName()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(sel_lati, sel_longi)).zoom(18).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do You Want to Do Request ? "+requestPojo.getName())
                        .setCancelable(false)
                        .setPositiveButton("Send Request", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                approveRequest();
                            }
                        }).setNegativeButton("Calling", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
              calling();

                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("Food Request");
                alert.show();


//                int position=Integer.parseInt(marker.getId().replaceAll("[m]",""))-1;
//                Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();

            }
        });
    }

void calling(){
    Intent callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse("tel:"+requestPojo.getPhone()));
    startActivity(callIntent);
    }


    public void approveRequest() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {

                   Toast.makeText(getContext(), "Request Sented", Toast.LENGTH_SHORT).show();
//

                } else {
//                    nodata.setBackgroundResource(no_data_found);
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();
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
                String uid=shobjmail.getString("u_id","");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "foodrequest");
                map.put("u_id", uid);
                map.put("dnid", requestPojo.getDntid());


                return map;
            }
        };
        queue.add(request);
    }


}
