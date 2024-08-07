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

public class AdminMusteriler extends AppCompatActivity {
    BottomNavigationView btmNavAdminMusteriler;
    RecyclerView rvMusteriler;
    FirebaseFirestore fstore;
    CustomerAdapter musteriAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_musteriler);

        btmNavAdminMusteriler = findViewById(R.id.btmNavAdminMusteriler);
        btmNavAdminMusteriler.setSelectedItemId(R.id.itemAdminMusteri);
        btmNavAdminMusteriler.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemAdminMusteri:
                        return true;
                    case R.id.itemAdminRestoran:
                        startActivity(new Intent(getApplicationContext(),AdminPanel.class));
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

        rvMusteriler = findViewById(R.id.rvAdminMusteriler);
        rvMusteriler.setLayoutManager(new LinearLayoutManager(AdminMusteriler.this));
        fstore = FirebaseFirestore.getInstance();
        Query query = fstore.collection("Customer");

        FirestoreRecyclerOptions<CustomerModel> options =
                new FirestoreRecyclerOptions.Builder<CustomerModel>()
                        .setQuery(query,CustomerModel.class).build();
        musteriAdapter = new CustomerAdapter(options);
        rvMusteriler.setAdapter(musteriAdapter);
        musteriAdapter.startListening();


    }
}