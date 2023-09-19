package com.example.donationapp.COMMON;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donationapp.R;

import java.io.IOException;
import java.util.List;

public class ComplaintAdaptor extends ArrayAdapter<FillData> {

    String name,status,email,phone,dtae,imge;

    TextView NAME,STATUS,EMAIL,PHONE,DTAE;

    List<FillData> prodList;
    Activity context;
    ImageView img;

    public ComplaintAdaptor(Activity context, List<FillData> prodList) {
        super(context, R.layout.activity_complaint_adaptor, prodList);
        this.prodList = prodList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_complaint_adaptor, null, true);
        NAME = view.findViewById(R.id.username);
        EMAIL = view.findViewById(R.id.useremail);
        PHONE = view.findViewById(R.id.userphone);
        DTAE = view.findViewById(R.id.userdate);
        STATUS = view.findViewById(R.id.sattusid);
img=view.findViewById(R.id.imageView);
        name = prodList.get(position).getName();
        email = prodList.get(position).getEmail();
        phone=prodList.get(position).getPhone();
        dtae = prodList.get(position).getAddress();
        status=prodList.get(position).getPass();
imge=prodList.get(position).getImg();

        NAME.setText(name);
        EMAIL.setText(email);
        PHONE.setText(phone);
        DTAE.setText(dtae);
        STATUS.setText(status);
        try {
            byte[] imageAsBytes = Base64.decode(imge.getBytes());
            img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
//             const_img.setBackground(new BitmapDrawable(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;

    }
}
