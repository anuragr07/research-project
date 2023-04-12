package com.smartspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class MyDevice extends AppCompatActivity {
    ArrayList deviceName = new ArrayList<>(Arrays.asList("Yellow Light", "Yeelight Color Bulb", "Sunset Rise Tube","Nightlight"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_device);
        ImageView imgBack = (ImageView) findViewById(R.id.back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
               finish();

            }
        });
// set a LinearLayoutManager with default orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        MyDeviceAdapter customAdapter = new MyDeviceAdapter(MyDevice.this, deviceName);
        recyclerView.setAdapter(customAdapter);

    }
}