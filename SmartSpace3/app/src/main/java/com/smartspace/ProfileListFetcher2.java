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

public class ProfileListFetcher2 extends AsyncTask<Void, Void, ArrayList<String>> {
    private static final String API_URL = "http://174.6.73.177:3000/";

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        ArrayList<String> profileList2 = new ArrayList<>();
        try {
            URL url = new URL(API_URL + "Laundry/" + "total");
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
                profileList2.add(readings);
                Log.d("Arpit700", username+"   "+ readings);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("Hello", String.valueOf(e));
        }

        return profileList2;
    }

    @Override
    protected void onPostExecute(ArrayList<String> profileList) {
        // Handle the API response, e.g. update the UI with the profile list
        for (String profileItem : profileList) {
            String readings = profileItem;
            Log.d("Arpit700", "Username: " + "Readings+: " + readings);
        }
    }
}
