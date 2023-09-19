package com.example.donationapp.ADmin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.donationapp.Login;
import com.example.donationapp.R;

public class AdminHome extends AppCompatActivity {
ImageView tologout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_home);

        ActionBar bar = getSupportActionBar();
        bar.hide();
        tologout=findViewById(R.id.logoutadmin);
        tologout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;

                builder = new AlertDialog.Builder(AdminHome.this);

                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminHome.this);
                builder.setMessage("Logout").setTitle("Logout");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to close this application ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                startActivity(new Intent(getApplicationContext(), Login.class));

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Logout");
                alert.show();

            }
        });
    }
    public void first(View view){
        startActivity(new Intent(getApplicationContext(),DonationList.class));
    }
    public void second(View view){
        startActivity(new Intent(getApplicationContext(),Complaints.class));
    }
    public void third(View view){
        startActivity(new Intent(getApplicationContext(),Donators.class));
    }
    public void four(View view){
        startActivity(new Intent(getApplicationContext(),Reviews.class));
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(AdminHome.this, "Plz Logout", Toast.LENGTH_SHORT).show();
    }


}