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

public class DeviceListFetcher extends AsyncTask<Void, Void, ArrayList<String[]>> {
    private static final String API_URL = "http://174.6.73.177:3000/";
    private String userId;
    private String roomId;

    public DeviceListFetcher(String userId, String roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    @Override
    protected ArrayList<String[]> doInBackground(Void... params) {
        ArrayList<String[]> deviceList = new ArrayList<>();
        ArrayList<String[]> shellyData = new ArrayList<>();
        try {
            URL url = new URL(API_URL + "profile/" + userId + "/" + roomId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Log.d("Hello", String.valueOf(url));
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
            JSONObject jsonObject = new JSONObject(response);

            JSONArray devices = jsonObject.getJSONArray("device");
            for (int i = 0; i < devices.length(); i++) {
                JSONObject device = devices.getJSONObject(i);
                String deviceName = device.getString("device_name");
                String status = device.getString("status");
                String[] deviceData = {deviceName, status, "",""}; // Adding an empty string to store the value of `bright` or `Brightness`

                JSONArray settings = device.getJSONArray("settings");
//                Log.i("check", String.valueOf(settings));
                for (int j = 0; j < settings.length(); j++) {
                    JSONObject setting = settings.getJSONObject(j);

                    Log.i("Result", String.valueOf(setting));
                    if (setting.has("result")) {
                        Log.i("Result:","inside");
                      //  JSONObject resultObject = jsonObject.getJSONObject("result");
                       // double apower = resultObject.getDouble("apower");
//                        double voltage = resultObject.getDouble("voltage");
//                        double current = resultObject.getDouble("current");
//                        double tC = resultObject.getJSONObject("temperature").getDouble("tC");
//                        Log.i("Result:",apower+" "+voltage+" "+current+" "+tC);
                       // Log.i("Result: ",String.valueOf(apower));
                    }
                    else{}


                    if (setting.has("bright")) {
                        int bright = Integer.parseInt(setting.getString("bright"));
                        deviceData[2] = String.valueOf(bright);
                        Log.i("check", "Len:"+ String.valueOf(settings.length()));
                        Log.i("check", "inside bright: " + j);
                        Log.i("final", String.valueOf(setting));
                    } else if (setting.has("Brightness")) {
                        Log.i("final", String.valueOf(setting));
                        int brightness = Integer.parseInt(setting.getString("Brightness"));
                        deviceData[2] = String.valueOf(brightness);
                        Log.i("check", "inside Brightness: " + j);
//                        Log.i("check", "inside Brightness: " + brightness);
                    }
                    else{Log.i("final","Error");}
                    int r,g,b;
                    if (setting.has("rgbv")) {
                        JSONArray rgbArray = setting.getJSONArray("rgbv");
                         r = rgbArray.getInt(0);
                         g = rgbArray.getInt(1);
                         b = rgbArray.getInt(2);
                        deviceData[3] = String.format("%d,%d,%d", r, g, b);
                        Log.i("final", "Len:"+ j+ "  "+String.valueOf(settings.length()));
                        Log.i("final", "inside rgb: " + r + ", " + g + ", " + b);
                    }
                    else { Log.i("final", "Nothing");}

//                    JSONArray settings1 = device.getJSONArray("settings");
//                    for (int k = 0; k < settings1.length(); k++) {
//                        JSONObject setting1 = settings1.getJSONObject(k);
//                    if (setting1.has("XY color")) {
//                        JSONArray xyColorArray = setting1.getJSONArray("XY color");
//                        double x = xyColorArray.getDouble(0);
//                        double y = xyColorArray.getDouble(1);
//                        deviceData[3] = String.format("%.4f,%.4f", x, y);
//                        Log.i("check", "Len:"+ String.valueOf(settings1.length()));
//                        Log.i("check", "itter: " + k);
//                        Log.i("check", "inside XY color: " + x + ", " + y);
//                    } else {
//                        Log.i("check1", "agya");
//                        JSONArray rgbArray = setting1.getJSONArray("rgbv");
//                        int r = rgbArray.getInt(0);
//                        int g = rgbArray.getInt(1);
//                        int b = rgbArray.getInt(2);
//                        deviceData[3] = String.format("%d,%d,%d", r, g, b);
//                        Log.i("check", "Len:"+ String.valueOf(settings.length()));
//                        Log.i("check", "itter: " + k);
//                        Log.i("check", "inside rgb: " + r + ", " + g + ", " + b);
//                    }
//                }
                }
                deviceList.add(deviceData);
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("Hello", String.valueOf(e));
        }

        return deviceList;
    }

    @Override
    protected void onPostExecute(ArrayList<String[]> deviceList) {
        // Handle the API response, e.g. update the UI with the device list
        for (String[] deviceData : deviceList) {
            String deviceName = deviceData[0];
            String status = deviceData[1];
            Log.d("Hello", "Device name: " + deviceName + ", status: " + status);
        }
    }
}
