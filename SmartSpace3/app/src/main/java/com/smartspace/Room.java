package com.smartspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room extends AppCompatActivity {


    ArrayList<String> deviceNames = new ArrayList<>();
    ArrayList<Boolean> deviceStatus = new ArrayList<>();
    ArrayList<String> deviceBright = new ArrayList<>();
    //    ArrayList<String> sceneName = new ArrayList<>(Arrays.asList("White", "Relax", "Sunset", "Multicolor", "Autumn gold", "Read", "Cool" , "Nightlight"));
    String selectedRoomName="";
    ArrayList <String> selectedColor=new ArrayList<>();
    String selectedUserId="";
    ArrayList<String> myList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        TextView myTextView = (TextView) findViewById(R.id.scene);
        ArrayList<ColorItem> colorList = new ArrayList<>();
        colorList.add(new ColorItem("Red", R.drawable.red));
        colorList.add(new ColorItem("Blue", R.drawable.blue));
        colorList.add(new ColorItem("Green", R.drawable.green));
        colorList.add(new ColorItem("Yellow", R.drawable.yellow));
        colorList.add(new ColorItem("Brown", R.drawable.brown));
        colorList.add(new ColorItem("Black", R.drawable.black));
        colorList.add(new ColorItem("Dark Blue", R.drawable.dblue));
        colorList.add(new ColorItem("Pink", R.drawable.pink));
        colorList.add(new ColorItem("orange", R.drawable.orange));
// Add more colors here...
        Intent intent = getIntent();
        selectedRoomName = intent.getStringExtra("roomName");
        selectedUserId = intent.getStringExtra("user_id");
        String username = getIntent().getStringExtra("userName");
        Log.i("Anu","Hello"+username);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recycler_grid);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(gridLayoutManager);
        Log.i("Saksham2", String.valueOf(colorList.get(0)));
        Log.i("saakar",selectedRoomName);
        if (selectedRoomName.equals("Laundry")){
            colorList.clear();
            myTextView.setText("");
        } else if (selectedRoomName.equals("Kitchen")) {
            colorList.clear();
            myTextView.setText("");
        }
       // Log.i("Saksham2", String.valueOf(colorList.get(0)));
        DetailSceneAdapter customAdapter2 = new DetailSceneAdapter(Room.this, colorList);
        recyclerView2.setAdapter(customAdapter2);




        Log.i("test", selectedRoomName + "   " + selectedUserId);

        TextView roomNameTextView = findViewById(R.id.text_roomname);
        roomNameTextView.setText(selectedRoomName);










        // Call API to fetch device data
        String user_id = selectedUserId; // Replace with your user id
        new DeviceListFetcher(user_id, selectedRoomName) {
            @Override
            protected void onPostExecute(ArrayList<String[]> deviceDataList) {
                super.onPostExecute(deviceDataList);

                if (deviceDataList.size() > 0) {
                    // Update UI elements with device data
                    for (String[] data : deviceDataList) {
                        String device = data[0];
                        String status = data[1];
                        String bright =data[2];
                        String color=data[3];
                        deviceBright.add(bright);
                        deviceNames.add(device);
                        selectedColor.add(color);
                        Log.i("final2",color);
                        if (status.equals("On")) {
                            deviceStatus.add(Boolean.TRUE);
                        } else {
                            deviceStatus.add(Boolean.FALSE);
                        }
                        Log.i("Hello", String.valueOf(status));
                        Log.i("Hello", String.valueOf(deviceStatus));
                    }
                    DetailRoomAdapter customAdapter = new DetailRoomAdapter(Room.this, deviceNames, deviceStatus,deviceBright,selectedColor,selectedRoomName,username,myList);
                    recyclerView.setAdapter(customAdapter);

                } else {
                    Toast.makeText(Room.this, "No devices found", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();











        ImageView imgBack = (ImageView) findViewById(R.id.back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                finish();
            }
        });
        ImageView imgSettings = findViewById(R.id.settings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Room.this, Settings.class);
                intent.putExtra("user_id", selectedUserId);
                intent.putExtra("userName",username);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onItemClick(String color) {
//        selectedColor.clear();
//        selectedColor.add(color);
//        Log.i("selectedColor", color);
//    }
}
