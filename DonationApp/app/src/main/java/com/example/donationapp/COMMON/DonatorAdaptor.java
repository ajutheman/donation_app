package com.example.donationapp.COMMON;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.donationapp.R;

import java.util.List;

public class DonatorAdaptor extends ArrayAdapter<FillData> {

    String name,status,email,phone,dtae;

    TextView NAME,STATUS,EMAIL,PHONE,DTAE;

    List<FillData> prodList;
    Activity context;

    public DonatorAdaptor(Activity context, List<FillData> prodList) {
        super(context, R.layout.activity_donator_adaptor, prodList);
        this.prodList = prodList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_donator_adaptor, null, true);
        NAME = view.findViewById(R.id.username);
        EMAIL = view.findViewById(R.id.useremail);
        PHONE = view.findViewById(R.id.userphone);
        DTAE = view.findViewById(R.id.userdate);
        STATUS = view.findViewById(R.id.sattusid);

        name = prodList.get(position).getName();
        email = prodList.get(position).getEmail();
        phone=prodList.get(position).getPhone();
        dtae = prodList.get(position).getAddress();
        status=prodList.get(position).getPass();

        NAME.setText(name);
        EMAIL.setText(email);
        PHONE.setText(phone);
        DTAE.setText(dtae);
        if(status.equals("no")) {
            STATUS.setText("Not Accepted");
            STATUS.setTextColor(Color.rgb(255,55,0));
        }else if(status.equals("yes")){
            STATUS.setText("Accepted");
            STATUS.setTextColor(Color.rgb(10,255,0));


        }else {
            STATUS.setText(status);
        }
        return view;

    }
}
