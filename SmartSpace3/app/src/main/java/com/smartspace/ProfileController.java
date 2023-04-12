package com.smartspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class ProfileController extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProfileControlAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_controller);

        recyclerView = findViewById(R.id.recycler_grid1);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ProfileControlAdapter(ProfileController.this, new ArrayList<>(),new ArrayList<String>());
        recyclerView.setAdapter(adapter);

        ImageView imgBack = findViewById(R.id.back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ArrayList<ProfileItem> fetchedData = new ArrayList<>();
        new ProfileListFetcher() {
            @Override
            protected void onPostExecute(ArrayList<ProfileItem> profileList) {
                super.onPostExecute(profileList);
                ArrayList<ProfileItem> fetchedData = profileList;

                new ProfileListFetcher2() {
                    @Override
                    protected void onPostExecute(ArrayList<String> profileList2) {
                        super.onPostExecute(profileList2);
                        adapter.setData(fetchedData, profileList2);
                    }
                }.execute();
            }
        }.execute();

}
}