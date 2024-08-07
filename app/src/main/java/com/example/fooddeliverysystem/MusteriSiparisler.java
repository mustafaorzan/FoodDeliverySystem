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

public class MusteriSiparisler extends AppCompatActivity {
    BottomNavigationView btmNavMusteriSiparisler;
    RecyclerView rvSiparis;
    FirebaseFirestore fstore;
    SiparisAdapterMusteri siparisAdapterMusteri;
    private String mAd, mSoyad, mAdres, mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_siparisler);

        Intent intent = getIntent();
        if (intent != null) {
            String musteriAd = intent.getStringExtra("musteriAd");
            mAd = musteriAd;
            String musteriSoyad = intent.getStringExtra("musteriSoyad");
            mSoyad = musteriSoyad;
            String musteriAdres = intent.getStringExtra("musteriAdres");
            mAdres = musteriAdres;
            String musteriEmail = intent.getStringExtra("musteriMail");
            String musteriId = intent.getStringExtra("musteriId");
            mId = musteriId;

            String fullName = musteriAd + " " + musteriSoyad;
            getSupportActionBar().setTitle("Müşteri: " + fullName);

            System.out.println(" MUSTERI SİPARİŞLER ");
            System.out.println("Müşteri ID: " + mId);
            System.out.println("Müşteri Ad: " + mAd);
            System.out.println("Müşteri Soyad: " + mSoyad);
            System.out.println("Müşteri Adres: " + mAdres);
            System.out.println("-------------------------------------------");


            btmNavMusteriSiparisler = findViewById(R.id.btmNavMusteriSiparisler);
            rvSiparis = findViewById(R.id.recyclerview_musteri_siparisler);
            rvSiparis.setLayoutManager(new LinearLayoutManager(MusteriSiparisler.this));
            fstore = FirebaseFirestore.getInstance();
            Query query = fstore.collection("Order").whereEqualTo("customerId", mId).orderBy("date", Query.Direction.DESCENDING);

            FirestoreRecyclerOptions<OrderModel> options =
                    new FirestoreRecyclerOptions.Builder<OrderModel>()
                            .setQuery(query, OrderModel.class).build();
            siparisAdapterMusteri = new SiparisAdapterMusteri(options);
            rvSiparis.setAdapter(siparisAdapterMusteri);
            siparisAdapterMusteri.startListening();

            btmNavMusteriSiparisler.setSelectedItemId(R.id.itemMusteriGecmis);
            btmNavMusteriSiparisler.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.itemMusteriAnasayfa:
                            Intent intentAnasayfa = new Intent(MusteriSiparisler.this, MusteriAnaSayfa.class);
                            intentAnasayfa.putExtra("musteriAd", mAd);
                            intentAnasayfa.putExtra("musteriSoyad", mSoyad);
                            intentAnasayfa.putExtra("musteriId", mId);
                            intentAnasayfa.putExtra("musteriAdres", mAdres);
                            startActivity(intentAnasayfa);
                            return true;
                        case R.id.itemMusteriRestoranlar:
                            Intent intentRestoran = new Intent(MusteriSiparisler.this, MusteriRestoranlar.class);
                            intentRestoran.putExtra("musteriAd", mAd);
                            intentRestoran.putExtra("musteriSoyad", mSoyad);
                            intentRestoran.putExtra("musteriId", mId);
                            intentRestoran.putExtra("musteriAdres", mAdres);
                            startActivity(intentRestoran);
                            return true;
                    }
                    return false;
                }
            });
        }
    }
}