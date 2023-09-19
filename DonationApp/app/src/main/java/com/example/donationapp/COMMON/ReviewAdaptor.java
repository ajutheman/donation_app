package com.example.donationapp.COMMON;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.donationapp.R;

import java.io.IOException;
import java.util.List;

public class ReviewAdaptor extends ArrayAdapter<FillData> {

    String name,status,email,phone,dtae,RR;

    TextView NAME,STATUS,EMAIL,PHONE,DTAE;

    List<FillData> prodList;
    Activity context;
    RatingBar rr;

    public ReviewAdaptor(Activity context, List<FillData> prodList) {
        super(context, R.layout.activity_review_adaptor, prodList);
        this.prodList = prodList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_review_adaptor, null, true);
        NAME = view.findViewById(R.id.username);
        EMAIL = view.findViewById(R.id.useremail);
        rr=view.findViewById(R.id.rat);

        name = prodList.get(position).getName();
        email = prodList.get(position).getEmail();
        RR=prodList.get(position).getPass();

        NAME.setText(name);
        EMAIL.setText(email);
             rr.setRating(Float.parseFloat(RR));


        return view;

    }
}
