package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firestore.v1.StructuredQuery;

import org.w3c.dom.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MusteriSepet extends AppCompatActivity {
    BottomNavigationView btmNavMusteriSepet;
    RecyclerView rvSepet;
    FirebaseFirestore fstore;
    SepetAdapter sepetAdapter;
    Button btnSepet;
    private String mAd, mAdres, mId, mSoyad, rId, rAd, urun, fiyat, tarih;
    int ffiyat,adet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_sepet);

        Intent intent = getIntent();
        if (intent!=null){
            mAd= intent.getStringExtra("musteriAd");
            mSoyad = intent.getStringExtra("musteriSoyad");
            mAdres = intent.getStringExtra("musteriAdres");
            mId  = intent.getStringExtra("musteriId");
            urun = intent.getStringExtra("urun");
            fiyat = intent.getStringExtra("fiyat");
            tarih = intent.getStringExtra("tarih");
            rId = intent.getStringExtra("restoranId");
            rAd = intent.getStringExtra("restoranAd");

            String fullName = mAd + " " + mSoyad;
            getSupportActionBar().setTitle("Müşteri: " + fullName);
            ffiyat = Integer.parseInt(fiyat);

            System.out.println(" MUSTERI SEPET ");
            System.out.println("Müşteri ID: " + mId);
            System.out.println("Müşteri Ad: " + mAd);
            System.out.println("Müşteri Soyad: " + mSoyad);
            System.out.println("Müşteri Adres: " + mAdres);
            System.out.println("Restoran ID: " + rId);
            System.out.println("Restoran Ad: " + rAd);
            System.out.println("Ürün: " + urun);
            System.out.println("Fiyat: " + fiyat);
            System.out.println("Tarih: " + tarih);
            System.out.println("-------------------------------------------");

        }

        rvSepet = findViewById(R.id.recyclerview_musteri_sepet);
        btnSepet = findViewById(R.id.btnMusteriSepetOnayla);

        rvSepet.setLayoutManager(new LinearLayoutManager(MusteriSepet.this));
        List<OrderModel> orderList = new ArrayList<OrderModel>();
        OrderModel order = new OrderModel();
        order.setRestaurantId(rId);
        order.setRestaurantName(rAd);
        order.setCustomerId(mId);
        order.setCustomerName(mAd);
        order.setCustomerLastName(mSoyad);
        order.setCustomerAddress(mAdres);
        order.setDate(tarih);
        order.setFood(urun);
        order.setPrice(ffiyat);
        order.setQuantity(adet);

        orderList.add(order);
        sepetAdapter = new SepetAdapter(orderList);
        rvSepet.setAdapter(sepetAdapter);


        btnSepet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder(rId,rAd,mId,mAd,mSoyad,mAdres,tarih,urun,ffiyat,adet);
                System.out.println(" SİPARİŞ ONAYLANDI ");
                System.out.println("Müşteri ID: " + mId);
                System.out.println("Müşteri Ad: " + mAd);
                System.out.println("Müşteri Soyad: " + mSoyad);
                System.out.println("Müşteri Adres: " + mAdres);
                System.out.println("Restoran ID: " + rId);
                System.out.println("Restoran Ad: " + rAd);
                System.out.println("Ürün: " + urun);
                System.out.println("Adet: " + adet);
                System.out.println("Fiyat: " + ffiyat);
                System.out.println("Tarih: " + tarih);
                System.out.println("-------------------------------------------");
            }
        });

        btmNavMusteriSepet = findViewById(R.id.btmNavMusteriSepet);

        btmNavMusteriSepet.setSelectedItemId(R.id.itemMusteriSepet);
        btmNavMusteriSepet.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemMusteriSepet:
                        return true;
                    case R.id.itemMusteriAnasayfa:
                        Intent intentAnasayfa = new Intent(MusteriSepet.this, MusteriAnaSayfa.class);
                        intentAnasayfa.putExtra("musteriAd", mAd);
                        intentAnasayfa.putExtra("musteriSoyad", mSoyad);
                        intentAnasayfa.putExtra("musteriId", mId);
                        intentAnasayfa.putExtra("musteriAdres", mAdres);
                        startActivity(intentAnasayfa);
                        return true;
                    case R.id.itemMusteriRestoranlar:
                        Intent intentRestoranlar = new Intent(MusteriSepet.this, MusteriRestoranlar.class);
                        intentRestoranlar.putExtra("musteriAd", mAd);
                        intentRestoranlar.putExtra("musteriSoyad", mSoyad);
                        intentRestoranlar.putExtra("musteriId", mId);
                        intentRestoranlar.putExtra("musteriAdres", mAdres);
                        startActivity(intentRestoranlar);
                        return true;
                    case R.id.itemMusteriGecmis:
                        Intent intentGecmis = new Intent(MusteriSepet.this, MusteriSiparisler.class);
                        intentGecmis.putExtra("musteriAd", mAd);
                        intentGecmis.putExtra("musteriSoyad", mSoyad);
                        intentGecmis.putExtra("musteriId", mId);
                        intentGecmis.putExtra("musteriAdres", mAdres);
                        startActivity(intentGecmis);
                }
                return false;
            }
        });
    }
    private void addOrder(String rId, String rAd, String mId, String mAd, String mSoyad,
                          String mAdres, String tarih, String urun, int fiyat, int adet){
        fstore = FirebaseFirestore.getInstance();
        CollectionReference orderRef = fstore.collection("Order");
        DocumentReference newOrderRef = orderRef.document();

        adet = sepetAdapter.getSayi();
        fiyat = fiyat * adet;
        tarih = String.valueOf(getCurrentDateTime());
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("restaurantId", rId);
        orderData.put("restaurantName", rAd);
        orderData.put("customerName", mAd);
        orderData.put("customerId", mId);
        orderData.put("customerLastName", mSoyad);
        orderData.put("customerAddress", mAdres);
        orderData.put("date", tarih);
        orderData.put("food", urun);
        orderData.put("price", fiyat);
        orderData.put("quantity", adet);

        sepetAdapter.notifyDataSetChanged();

        newOrderRef.set(orderData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MusteriSepet.this, "Siparişiniz başarıyla alındı",
                        Toast.LENGTH_SHORT).show();
                Intent intentDonus = new Intent(MusteriSepet.this, MusteriAnaSayfa.class);
                intentDonus.putExtra("musteriAd", mAd);
                intentDonus.putExtra("musteriSoyad", mSoyad);
                intentDonus.putExtra("musteriId", mId);
                intentDonus.putExtra("musteriAdres", mAdres);
                startActivity(intentDonus);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MusteriSepet.this, "Sepette hata oluştu: "
                        + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}