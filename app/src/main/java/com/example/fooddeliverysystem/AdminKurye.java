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

public class AdminKurye extends AppCompatActivity {
    BottomNavigationView btmNavAdminKurye;
    RecyclerView rvAdminKurye;
    FirebaseFirestore fstore;
    CourierAdapter kuryeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_kurye);

        rvAdminKurye = findViewById(R.id.rvAdminKurye);
        rvAdminKurye.setLayoutManager(new LinearLayoutManager(AdminKurye.this));
        fstore = FirebaseFirestore.getInstance();
        Query query = fstore.collection("Courier");

        FirestoreRecyclerOptions<CourierModel> options =
                new FirestoreRecyclerOptions.Builder<CourierModel>()
                        .setQuery(query,CourierModel.class).build();

        kuryeAdapter = new CourierAdapter(options);
        rvAdminKurye.setAdapter(kuryeAdapter);
        kuryeAdapter.startListening();



        btmNavAdminKurye = findViewById(R.id.btmNavAdminKurye);
        btmNavAdminKurye.setSelectedItemId(R.id.itemAdminKurye);
        btmNavAdminKurye.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemAdminKurye:
                        return true;
                    case R.id.itemAdminMusteri:
                        startActivity(new Intent(getApplicationContext(),AdminMusteriler.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.itemAdminRestoran:
                        startActivity(new Intent(getApplicationContext(),AdminPanel.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}