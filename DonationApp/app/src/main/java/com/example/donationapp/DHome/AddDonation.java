package com.example.donationapp.DHome;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donationapp.COMMON.Base64;
import com.example.donationapp.COMMON.GPSTracker;
import com.example.donationapp.COMMON.Utility;
import com.example.donationapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddDonation extends Fragment {


    ImageView imgGal,imgCam,imgView;
    Uri imageUri;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;
    private Bitmap bitmap;
    String bal="";
    Spinner family,season;
    Button btnadd;
    String NAME = "", DETAILS = "", FEAUTURE = "", ORIGIN ,AMOUNT;
    boolean switch_statue = true;
    EditText name, details, features, origin,address;

    GPSTracker gps;
    double latitude;
    double longitude;
    TextView lati, longi, mapaddress,Amount;
    ImageView getlattlongg;
    String ADDRESS;
    Button btn;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_donation, container, false);

        imgGal=root.findViewById(R.id.add_imgGal);
        imgCam=root.findViewById(R.id.add_imgCam);
        imgView=root.findViewById(R.id.add_image);

        name = root.findViewById(R.id.addplant_name);
        details = root.findViewById(R.id.addplant_description);
        features = root.findViewById(R.id.addplant_features);
        origin = root.findViewById(R.id.addplant_origin);
        season = root.findViewById(R.id.addplant_season);
        family = root.findViewById(R.id.addplant_family);
        address = root.findViewById(R.id.addplant_loc_details);
        btnadd = root.findViewById(R.id.addplant_btnadd);

//        final String CAT_LIST[] = {"Apiaceae", "Bignoniaceae", "Campanulaceae", "Ericaceae ","Geraniaceae","Hydrophyllacea","Iridaceae","Lamiaceae"};
//        ArrayAdapter ar = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, CAT_LIST);
//        family.setAdapter(ar);
//        family.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                FAMILY= CAT_LIST[position].trim();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//      final  String SEASON_LIST[] = {"All", "Spring", "Summer", "Fall ","Winter"};
//        ArrayAdapter ar2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, SEASON_LIST);
//        season.setAdapter(ar2);
//        season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                SEASON= SEASON_LIST[position].trim();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        getlattlongg = root.findViewById(R.id.addplant_getlattlongg);
        lati = root.findViewById(R.id.latval);
        longi = root.findViewById(R.id.longval);
        gps = new GPSTracker(getContext());
        getlattlongg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPop();
            }
        });


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NAME = name.getText().toString().trim();
                DETAILS = details.getText().toString().trim();
                FEAUTURE = features.getText().toString().trim();
                ORIGIN = origin.getText().toString().trim();
                ADDRESS = address.getText().toString().trim();
                if(NAME.isEmpty()){
                    name.requestFocus();
                    name.setError("Enter Name");
                }else if(FEAUTURE.isEmpty()){
                    features.requestFocus();
                    features.setError("Enter Features");
                }else if(ADDRESS.isEmpty()){
                    address.requestFocus();
                    address.setError("Enter Address");
                }

                else{

                    add_Plant();
                }


            }
        });
        imgCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                String fileName = "new-photo-name.jpg";
                //create parameters for Intent with filename
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by camera");
                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)\Context.
                imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //create new Intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, PICK_Camera_IMAGE);
            }
        });

        imgGal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // TODO Auto-generated method stub
                try {
                    Intent gintent = new Intent();
                    gintent.setType("image/*");
                    gintent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gintent, "Select Picture"), PICK_IMAGE);

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        });
        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;
		 		    	/*Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if(selectedImageUri != null){
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        imgView.setImageBitmap(bitmap);

        //...
        Base64.InputStream is;
        BitmapFactory.Options bfo;
        Bitmap bitmapOrg;
        ByteArrayOutputStream bao ;

        bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 2;
        //bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);

        bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte [] ba = bao.toByteArray();
        bal= Base64.encodeBytes(ba);


        //..

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    public void dialogPop() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /////make map clear
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(R.layout.custom_map_lattlongg);////your custom content
        MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
        mapaddress = dialog.findViewById(R.id.custom_map_address);
        btn = dialog.findViewById(R.id.custom_map_btnadd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ADDRESS.isEmpty()) {
                    Toast.makeText(getContext(), "Plaese Select location", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                }

            }
        });

        MapsInitializer.initialize(getContext());

        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {


                LatLng posisiabsen = new LatLng(gps.getLatitude(), gps.getLongitude()); ////your lat lng
                googleMap.addMarker(new MarkerOptions().position(posisiabsen).title("Yout title"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisiabsen));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 16.5f));


                //                ....
                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {

                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("New Location"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latLng.latitude, latLng.longitude)));
                        latitude = latLng.latitude;
                        longitude = latLng.longitude;

                        lati.setText("Latitude      : " + latitude);
                        longi.setText("Longitude   : " + longitude);

                        //getAddress strat
                        try {

                            Geocoder geocoder;
                            List<Address> yourAddresses;
                            geocoder = new Geocoder(getContext(), Locale.getDefault());
                            yourAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                            if (yourAddresses.size() > 0) {
                                String yourAddress = yourAddresses.get(0).getAddressLine(0);
                                String yourCity = yourAddresses.get(0).getAddressLine(1);
                                String yourCountry = yourAddresses.get(0).getAddressLine(2);
                                Log.d("***", yourAddress);

                                ADDRESS = yourAddress;
                                address.setText(yourAddress);
                                mapaddress.setText(yourAddress);

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //getAddress end

                    }
                });
//                ....


            }
        });


        dialog.show();
    }

    public void add_Plant() {

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                if (!response.trim().equals("failed")) {
                    AddFooditem fragment = new AddFooditem();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment_content_donator_home, fragment)
                            .commit();
                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "ERROR  !", Toast.LENGTH_LONG).show();
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
                map.put("requestType", "adddonations");
                map.put("uid", uid);
                map.put("brandname",NAME);
                map.put("details",DETAILS);
                map.put("address",ADDRESS);
                map.put("insurance",FEAUTURE);
                map.put("latt", ""+latitude);
                map.put("longg", ""+longitude);
                map.put("image",bal);

                return map;
            }
        };
        queue.add(request);
    }

}