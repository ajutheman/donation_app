package com.example.donationapp.COMMON;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donationapp.R;

import java.util.List;

public class SingleNameAdaptor extends  ArrayAdapter<FillData> implements Filterable {

        Activity context;
        List<FillData> rest_List;
        String NAME;
        TextView name;

    public SingleNameAdaptor(Activity context, List<FillData> rest_List) {
            super(context, R.layout.activity_single_name_adaptor, rest_List);
            this.context = context;
            this.rest_List = rest_List;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = context.getLayoutInflater();
            View view = inflater.inflate(R.layout.activity_single_name_adaptor, null, true);
            name = view.findViewById(R.id.patientname);
            NAME=rest_List.get(position).getName();
            name.setText(NAME);
            return view;
        }


    }
