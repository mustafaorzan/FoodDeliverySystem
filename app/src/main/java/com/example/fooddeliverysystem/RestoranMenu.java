package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RestoranMenu extends AppCompatActivity {
    private Spinner spinnerMutfak;
    private ArrayAdapter<CharSequence> adapterMutfak;
    FirebaseFirestore dbRestoranMenu;
    Button btnMenuEkle;
    EditText menuUrun, menuAciklama, menuFiyat;
    String restaurantId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_menu);

        //Firestore daki restoran collectionundan giriş yapılan restoranId yi çeker
        //Menu kayıt kısmında veritabanına bağlamak için
        Intent intentMenu = getIntent();
        if (intentMenu != null){
            restaurantId= intentMenu.getStringExtra("restoranIdMenu");
            System.out.println("Restoran ID = " + restaurantId);
        }

        spinnerMutfak = (Spinner) findViewById(R.id.spinnerRestoranMutfak);
        btnMenuEkle = findViewById(R.id.btnMenuEkle);
        menuUrun = findViewById(R.id.editTextRestoranUrun);
        menuFiyat = findViewById(R.id.editTextRestoranFiyat);
        menuAciklama = findViewById(R.id.etMenuAciklama);
        dbRestoranMenu = FirebaseFirestore.getInstance();

        adapterMutfak = ArrayAdapter.createFromResource(this, R.array.categories_txt, android.R.layout.simple_spinner_dropdown_item);
        adapterMutfak.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerMutfak.setAdapter(adapterMutfak);

        //Menu Ekleme Butonu tıklandığında
        btnMenuEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuEkle();
            }
        });
    }
    public void menuEkle(){
        String menuUrunAdi, menuMutfak, aciklama, fiyat;
        int finalFiyat;
        menuUrunAdi = menuUrun.getText().toString();
        menuMutfak = spinnerMutfak.getSelectedItem().toString();
        aciklama = menuAciklama.getText().toString();
        fiyat = menuFiyat.getText().toString();// Integer türde veri al(layouttan)
        finalFiyat = Integer.parseInt(fiyat);//Int e çevir

        Map<String, Object> itemRestoranMenuKayit = new HashMap<>();

        if ( (TextUtils.isEmpty(menuUrunAdi)) || (TextUtils.isEmpty(aciklama)) || (TextUtils.isEmpty(fiyat)) )
            Toast.makeText(RestoranMenu.this,"LÜTFEN BOŞ BIRAKMAYINIZ", Toast.LENGTH_SHORT).show();
        else{
            itemRestoranMenuKayit.put("urunAdi", menuUrunAdi);
            itemRestoranMenuKayit.put("mutfak", menuMutfak);
            itemRestoranMenuKayit.put("aciklama", aciklama);
            itemRestoranMenuKayit.put("fiyat", finalFiyat);
            dbRestoranMenu.collection("Restaurant").document(restaurantId)
                    .collection("menu")
                    .add(itemRestoranMenuKayit)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RestoranMenu.this, "Yemek başarıyla eklendi", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),RestoranAnaSayfa.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RestoranMenu.this, "Menü öğesi eklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}