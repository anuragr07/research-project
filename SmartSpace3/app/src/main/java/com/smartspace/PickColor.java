package com.smartspace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;

public class PickColor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_color);
        ColorPickerView  colorPickerView = (ColorPickerView) findViewById(R.id.colorPickerView);
        LinearLayout  liner = (LinearLayout) findViewById(R.id.linearlayout);
        liner.setBackgroundColor(getColor(R.color.startcolor));
        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, boolean fromUser) {
                liner.setBackgroundColor(color);

            }
        });

        ImageView imgBack = (ImageView) findViewById(R.id.back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                finish();

            }
        });
    }
}