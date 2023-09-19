package com.example.donationapp.DHome;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donationapp.COMMON.Base64;
import com.example.donationapp.COMMON.FillData;
import com.example.donationapp.R;

import java.io.IOException;


public class ViewFullDetails extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_view_full_details, container, false);
        final Bundle b = getArguments();
        FillData model = b.getParcelable("clicked_item");

        TextView name = (TextView) view.findViewById(R.id.firstid);
        TextView origin = (TextView) view.findViewById(R.id.secondid);
        TextView season = (TextView) view.findViewById(R.id.thirdid);
        ImageView p_image = (ImageView) view.findViewById(R.id.imagid);
        TextView qantity = (TextView) view.findViewById(R.id.fiveid);

        name.setText(model.getName().toString().trim());
        season.setText("Quantity :"+model.getQty().toString().trim());
        origin.setText(model.getAddress().toString().trim());
        String base = model.getImg().trim();
        qantity.setText(model.getDetail().toString().trim());

        try {
            byte[] imageAsBytes = Base64.decode(base.getBytes());
            p_image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
//             const_img.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)));
        } catch (IOException e) {

            e.printStackTrace();
        }
        //start track
        ImageView img=view.findViewById(R.id.tracking);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                SharedPreferences shobjmail = getActivity().getSharedPreferences("getfoodshred", MODE_PRIVATE);
                String uid = shobjmail.getString("item", "");
                if (uid.equals("food")) {
                    Bundle bundle = new Bundle();
                    final Bundle b = getArguments();
                    FillData model = b.getParcelable("clicked_item");
                    bundle.putParcelable("clicked_item", (Parcelable) model);
                    TrackFood plantDetails = new TrackFood();
                    plantDetails.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment_content_donator_home, plantDetails).commit();
                } else {
                    Bundle bundle = new Bundle();
                    final Bundle b = getArguments();
                    FillData model = b.getParcelable("clicked_item");
                    bundle.putParcelable("clicked_item", (Parcelable) model);
                    TrackMapUserRqst plantDetails = new TrackMapUserRqst();
                    plantDetails.setArguments(bundle);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment_content_donator_home, plantDetails).commit();
//
                }
            }
        });

        return view;
    }
}