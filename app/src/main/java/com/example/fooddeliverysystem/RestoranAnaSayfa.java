package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RestoranAnaSayfa extends AppCompatActivity {

    private BottomNavigationView bottomNavRestoran;
    private Button btnMenuEkle;
    String restaurantIdMenu, rAd;
    RecyclerView rvMenu;
    FirebaseFirestore fstore;
    RestoranMenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_ana_sayfa);

        //MainActivity den veri çekme ve ActionBar Title değiştirme
        Intent intent = getIntent();
        if (intent != null){
            String restoranName = intent.getStringExtra("restoranAd");
            rAd = restoranName;
            String restoranId = intent.getStringExtra("restoranId");
            restaurantIdMenu = restoranId;
            getSupportActionBar().setTitle("Restoran: " + restoranName);

            System.out.println(" RESTORAN ANASAYFA ");
            System.out.println("Müşteri ID: " + restoranId);
            System.out.println("Müşteri Ad: " + restoranName);

            System.out.println("-------------------------------------------");
        }

        rvMenu = findViewById(R.id.recyclerView_Restoran_Menu);
        rvMenu.setLayoutManager(new LinearLayoutManager(RestoranAnaSayfa.this));
        fstore = FirebaseFirestore.getInstance();
        Query query = fstore.collection("Restaurant").document(restaurantIdMenu).collection("menu");
        FirestoreRecyclerOptions<MenuModel> options =
                new FirestoreRecyclerOptions.Builder<MenuModel>()
                        .setQuery(query,MenuModel.class).build();
        menuAdapter = new RestoranMenuAdapter(options);
        rvMenu.setAdapter(menuAdapter);
        menuAdapter.startListening();

        bottomNavRestoran = (BottomNavigationView) findViewById(R.id.btmNavRestoran);
        btnMenuEkle = (Button) findViewById(R.id.btnMenuEkle);

        bottomNavRestoran.setSelectedItemId(R.id.itemRestoranMenu);

        btnMenuEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMenuEkle = new Intent(getApplicationContext(), RestoranMenu.class);
                intentMenuEkle.putExtra("restoranIdMenu", restaurantIdMenu);
                startActivity(intentMenuEkle);
            }
        });

        bottomNavRestoran.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemRestoranSiparis:
                        Intent intent = new Intent(getApplicationContext(), RestoranSiparisler.class);
                        intent.putExtra("restoranId", restaurantIdMenu);
                        intent.putExtra("restoranAd", rAd);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}