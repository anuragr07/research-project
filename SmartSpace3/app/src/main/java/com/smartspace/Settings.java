package com.smartspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ImageView imgBack = (ImageView) findViewById(R.id.backsetting);
        Intent intent = getIntent();
       String selectedUserId = intent.getStringExtra("user_id");
        String username = getIntent().getStringExtra("username");
        TextView textView= (TextView) findViewById(R.id.userSettings);
        textView.setText(username);
        TextView textView2= (TextView) findViewById(R.id.userSettings);
        textView2.setText(selectedUserId);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                finish();

            }
        });

        TextView txtContact1 = (TextView) findViewById(R.id.Portal);
        txtContact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings.this, ProfileController.class);
                startActivity(i); // invoke the SecondActivity.
                finish();

            }});
        TextView txtContact = (TextView) findViewById(R.id.txtContact);
        txtContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings.this, ContactUs.class);
                startActivity(i); // invoke the SecondActivity.
                finish();

            }
        });

        TextView logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings.this, Login.class);
                startActivity(i); // invoke the SecondActivity.
                finish();

            }
        });

    }
}