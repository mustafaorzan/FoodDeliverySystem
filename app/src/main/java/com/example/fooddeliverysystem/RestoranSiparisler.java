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

public class RestoranSiparisler extends AppCompatActivity {

    RecyclerView rvSiparis;
    FirebaseFirestore fstore;
    SiparisAdapterRestoran siparisAdapterRestoran;
    String rId,restoranName;
    BottomNavigationView btmNavRestoranSiparis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_siparisler);

        Intent intent = getIntent();
        if (intent != null){
            rId = intent.getStringExtra("restoranId");
            restoranName = intent.getStringExtra("restoranAd");
            System.out.println(" RESTORAN SIPARISLER ");
            System.out.println("Müşteri ID: " + rId);
            System.out.println("Müşteri Ad: " + restoranName);

            System.out.println("-------------------------------------------");
        }

        rvSiparis = findViewById(R.id.recyclerview_restoran_siparisler);
        rvSiparis.setLayoutManager(new LinearLayoutManager(RestoranSiparisler.this));
        fstore = FirebaseFirestore.getInstance();
        Query query = fstore.collection("Order").whereEqualTo("restaurantId", rId).orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<OrderModel> options =
                new FirestoreRecyclerOptions.Builder<OrderModel>()
                        .setQuery(query, OrderModel.class).build();
        siparisAdapterRestoran = new SiparisAdapterRestoran(options);
        rvSiparis.setAdapter(siparisAdapterRestoran);
        siparisAdapterRestoran.startListening();

        btmNavRestoranSiparis = findViewById(R.id.btmNavRestoranSiparisler);
        btmNavRestoranSiparis.setSelectedItemId(R.id.itemRestoranSiparis);
        btmNavRestoranSiparis.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemRestoranMenu:
                        Intent intentRestoran = new Intent(RestoranSiparisler.this, RestoranAnaSayfa.class);
                        intentRestoran.putExtra("restoranId", rId);
                        intentRestoran.putExtra("restoranAd", restoranName);
                        startActivity(intentRestoran);
                        return true;
                    case R.id.itemRestoranSiparis:
                        return true;
                }
                return false;
            }
        });
    }
}