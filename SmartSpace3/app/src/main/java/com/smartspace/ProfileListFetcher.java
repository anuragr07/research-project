package com.smartspace;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProfileListFetcher extends AsyncTask<Void, Void, ArrayList<ProfileItem>> {
    private static final String API_URL = "http://174.6.73.177:3000/";

    @Override
    protected ArrayList<ProfileItem> doInBackground(Void... params) {
        ArrayList<ProfileItem> profileList = new ArrayList<>();
        try {
            URL url = new URL(API_URL + "Kitchen/" + "total");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Log.d("Arpit700", String.valueOf(url));
            // Add headers or authentication token if required

            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            String response = sb.toString();
            JSONArray users = new JSONArray(response);
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                String username = user.getString("username");
                String readings = user.getString("consumption");
                ProfileItem profileItem = new ProfileItem(username, readings);
                profileList.add(profileItem);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("Hello", String.valueOf(e));
        }

        return profileList;
    }

    @Override
    protected void onPostExecute(ArrayList<ProfileItem> profileList) {
        // Handle the API response, e.g. update the UI with the profile list
        for (ProfileItem profileItem : profileList) {
            String username = profileItem.getUserName();
            String readings = profileItem.getReadings();
            Log.d("Anurag10", "Username: " + username + ", Readings: " + readings);
        }
    }
}
