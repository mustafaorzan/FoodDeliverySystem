package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.*;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.collections.ArrayDeque;

public class MusteriRestoranMenu extends AppCompatActivity {
    RecyclerView rvMusteriRestoranMenu;
    BottomNavigationView btmNavMusteriRestoranMenu;
    private String restaurantId ,mAd,mSoyad,mAdres,mId, rAd, rId,tarih, urun, fiyat;

    private MyMenuAdapter myMenuAdapter;
    private FirebaseFirestore db;
    private List<MenuModel> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_restoran_menu);

        rvMusteriRestoranMenu = findViewById(R.id.recyclerviewMusteriRestoranMenu);
        btmNavMusteriRestoranMenu = findViewById(R.id.btmNavMusteriMenu);

        Intent intent = getIntent();
        if (intent != null){
            String restaurantId = intent.getStringExtra("restaurantId");
            rId = restaurantId;
            String restoranAd = intent.getStringExtra("restoranAd");
            rAd = restoranAd;
            String musteriAd = intent.getStringExtra("musteriAd");
            mAd = musteriAd;
            String musteriSoyad = intent.getStringExtra("musteriSoyad");
            mSoyad = musteriSoyad;
            String musteriId = intent.getStringExtra("musteriId");
            mId=musteriId;
            String musteriAdres = intent.getStringExtra("musteriAdres");
            mAdres=musteriAdres;
            tarih = getCurrentDateTime().toString();
            String food = intent.getStringExtra("urun");
            urun=food;
            String price = intent.getStringExtra("fiyat");
            fiyat=price;

            String fullName = musteriAd + " " + musteriSoyad;
            getSupportActionBar().setTitle("Müşteri: " + fullName);

            System.out.println(" MUSTERI RESTORAN MENU ");
            System.out.println("Restoran Id: " + rId);
            System.out.println("Restoran Ad: " + rAd);
            System.out.println("Müşteri ID: " + mId);
            System.out.println("Müşteri Ad: " + mAd);
            System.out.println("Müşteri Soyad: " + mSoyad);
            System.out.println("Müşteri Adres: " + mAdres);
            System.out.println("ZAMAN: " + tarih);

            System.out.println("-------------------------------------------");
        }

        rvMusteriRestoranMenu.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        menuList = new ArrayList<>();
        myMenuAdapter = new MyMenuAdapter(menuList);
        rvMusteriRestoranMenu.setAdapter(myMenuAdapter);

        getMenuData();

        myMenuAdapter.setMId(mId);
        myMenuAdapter.setMAd(mAd);
        myMenuAdapter.setMSoyad(mSoyad);
        myMenuAdapter.setMAdres(mAdres);
        myMenuAdapter.setRId(rId);
        myMenuAdapter.setRAd(rAd);
        myMenuAdapter.setTarih(tarih);

        btmNavMusteriRestoranMenu.setSelectedItemId(R.id.itemMusteriRestoranlar);
        btmNavMusteriRestoranMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemMusteriAnasayfa:
                        Intent intentAnasayfa = new Intent(MusteriRestoranMenu.this, MusteriAnaSayfa.class);
                        intentAnasayfa.putExtra("musteriAd", mAd);
                        intentAnasayfa.putExtra("musteriSoyad", mSoyad);
                        intentAnasayfa.putExtra("musteriId", mId);
                        intentAnasayfa.putExtra("musteriAdres", mAdres);
                        startActivity(intentAnasayfa);
                        return true;
                    case R.id.itemMusteriRestoranlar:
                        return true;
                }
                return false;
            }
        });
    }
    private void getMenuData() { //menu yu recycler a getirir
        CollectionReference menuRef = db.collection("Restaurant").document(rId)
                .collection("menu");
        menuRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                menuList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    MenuModel menu = document.toObject(MenuModel.class);
                    menuList.add(menu);
                }
                myMenuAdapter.notifyDataSetChanged();
            }
            else {
                //hata varsa mesajı
            }
        });
    }
    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}