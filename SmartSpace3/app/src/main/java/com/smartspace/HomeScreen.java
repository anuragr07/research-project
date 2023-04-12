package com.smartspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    private ArrayList<String> roomNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Retrieve username and user id from the intent
        String username = getIntent().getStringExtra("userName");
        String userIdIntent = getIntent().getStringExtra("user_id");
        Log.i("Anu","Hello"+username+userIdIntent);
        // Set the welcome message
        TextView textView = findViewById(R.id.username);
        textView.setText("Welcome " + username + "!");

        // Set up the recycler view with linear layout manager
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Parse the JSON data to retrieve room names
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("LatestScriptMongo.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String jsonData = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonData);
            List<String[]> roomDataList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userId = jsonObject.getString("user_id");
                if (userId.equals(userIdIntent)) {
                    JSONArray roomArray = jsonObject.getJSONArray("room");
                    for (int j = 0; j < roomArray.length(); j++) {
                        JSONObject roomObject = roomArray.getJSONObject(j);
                        String roomId = roomObject.getString("room_id");
                        String roomName = roomObject.getString("room_name");
                        String[] roomData = {roomId, roomName};
                        roomDataList.add(roomData);
                    }
                }
            }

            for (String[] roomData : roomDataList) {
                String roomId = roomData[0];
                String roomName = roomData[1];
                Log.i("Room ID: ", roomId);
                Log.i("Room Name: ", roomName);
            }

            // Create an ArrayList of room names from roomDataList
            for (String[] roomData : roomDataList) {
                String roomName = roomData[1];
                roomNames.add(roomName);
            }
        } catch (IOException e) {
            Log.e("HomeScreen", "Failed to read JSON data", e);
        } catch (JSONException e) {
            Log.e("HomeScreen", "Failed to parse JSON data", e);
        }

        // Set up the recycler view adapter
        HomeAdapter adapter = new HomeAdapter(HomeScreen.this, roomNames, userIdIntent,username);

        recyclerView.setAdapter(adapter);

        // Set up the settings button
        ImageView imgBack = findViewById(R.id.settings);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, Settings.class);
                intent.putExtra("user_id", userIdIntent);
                intent.putExtra("userName",username);
                startActivity(intent);
            }
        });
    }
}
