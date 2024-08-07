package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MusteriRestoranlar extends AppCompatActivity {

    private BottomNavigationView btmNavMusteriRestoranlar;
    RecyclerView rvRestoran;
    FirebaseFirestore fstore;
    RestaurantAdapterCustomer restaurantAdapterCustomer;
    private String mAd,mSoyad,mAdres,mId, rId, rAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_restoranlar);

        Intent intent = getIntent();
        if (intent!=null){
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

            System.out.println(" MUSTERI RESTORANLAR ");
            System.out.println("Müşteri ID: " + mId);
            System.out.println("Müşteri Ad: " + mAd);
            System.out.println("Müşteri Soyad: " + mSoyad);
            System.out.println("Müşteri Adres: " + mAdres);
            System.out.println("-------------------------------------------");
        }

        rvRestoran = findViewById(R.id.recyclerview_musteri_restoranlar);
        rvRestoran.setLayoutManager(new LinearLayoutManager(MusteriRestoranlar.this));
        fstore = FirebaseFirestore.getInstance();
        Query query = fstore.collection("Restaurant");
        FirestoreRecyclerOptions<RestaurantModel> options =
                new FirestoreRecyclerOptions.Builder<RestaurantModel>()
                        .setQuery(query, RestaurantModel.class)
                        .build();
        restaurantAdapterCustomer = new RestaurantAdapterCustomer(options);
        rvRestoran.setAdapter(restaurantAdapterCustomer);
        restaurantAdapterCustomer.startListening();

        btmNavMusteriRestoranlar = findViewById(R.id.btmNavMusteriRestoranlar);
        btmNavMusteriRestoranlar.setSelectedItemId(R.id.itemMusteriRestoranlar);

        restaurantAdapterCustomer.setOnItemClickListener(new RestaurantAdapterCustomer.OnItemClickListener() {
            @Override
            public void OnItemClick(DocumentSnapshot documentSnapshot, int position) {
                String restaurantId = documentSnapshot.getId();
                rId = restaurantId;
                View view = rvRestoran.getLayoutManager().findViewByPosition(position);
                TextView tvMusteriRestoranAdi = view.findViewById(R.id.tvMusteriRestoranAdi);
                String restaurantName = tvMusteriRestoranAdi.getText().toString();
                rAd = restaurantName;
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Restaurant").document(restaurantId).collection("menu")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(MusteriRestoranlar.this, "Tıklama başarılı", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MusteriRestoranMenu.class);
                                    intent.putExtra("restaurantId", restaurantId);
                                    intent.putExtra("musteriId", mId);
                                    intent.putExtra("musteriAd", mAd);
                                    intent.putExtra("musteriSoyad", mSoyad);
                                    intent.putExtra("musteriAdres", mAdres);
                                    intent.putExtra("restoranAd", restaurantName);
                                    startActivity(intent);
                                    /*for (QueryDocumentSnapshot document : task.getResult()){
                                        //"menu" alt belgesindeki verileri al ve kullan
                                        //ör, menü ögelerini RecyclerView da göstermek için liste oluştur
                                    }*/
                                }
                                else { //hata mesajı
                                }
                            }
                        });
            }
        });

        btmNavMusteriRestoranlar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemMusteriAnasayfa:
                        Intent intentAnasayfa = new Intent(MusteriRestoranlar.this, MusteriAnaSayfa.class);
                        intentAnasayfa.putExtra("musteriAd", mAd);
                        intentAnasayfa.putExtra("musteriSoyad", mSoyad);
                        intentAnasayfa.putExtra("musteriId", mId);
                        intentAnasayfa.putExtra("musteriAdres", mAdres);
                        startActivity(intentAnasayfa);
                        return true;
                    case R.id.itemMusteriRestoranlar:
                        return true;
                    case R.id.itemMusteriGecmis:
                        Intent intentGecmis = new Intent(MusteriRestoranlar.this, MusteriSiparisler.class);
                        intentGecmis.putExtra("musteriAd", mAd);
                        intentGecmis.putExtra("musteriSoyad", mSoyad);
                        intentGecmis.putExtra("musteriId", mId);
                        intentGecmis.putExtra("musteriAdres", mAdres);
                        startActivity(intentGecmis);
                        return true;
                }
                return false;
            }
        });

    }
}