package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminPanel extends AppCompatActivity {

    RecyclerView rvAdminRestoran;
    FirebaseFirestore fstore;
    RestaurantAdapter restaurantAdapter;
    BottomNavigationView btmNavAdminRestoran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        rvAdminRestoran = findViewById(R.id.recyclerview_admin_restoran);
        rvAdminRestoran.setLayoutManager(new LinearLayoutManager(AdminPanel.this));

        fstore = FirebaseFirestore.getInstance();
        Query query = fstore.collection("Restaurant");

        FirestoreRecyclerOptions<RestaurantModel> options =
                new FirestoreRecyclerOptions.Builder<RestaurantModel>()
                        .setQuery(query, RestaurantModel.class)
                        .build();
        restaurantAdapter = new RestaurantAdapter(options);
        rvAdminRestoran.setAdapter(restaurantAdapter);
        restaurantAdapter.startListening();

        btmNavAdminRestoran = findViewById(R.id.btmNavAdmin);
        btmNavAdminRestoran.setSelectedItemId(R.id.itemAdminRestoran);
        btmNavAdminRestoran.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemAdminRestoran:
                        return true;
                    case R.id.itemAdminMusteri:
                        startActivity(new Intent(getApplicationContext(),AdminMusteriler.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.itemAdminKurye:
                        startActivity(new Intent(getApplicationContext(),AdminKurye.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


}