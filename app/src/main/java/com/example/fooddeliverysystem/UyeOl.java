package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UyeOl extends AppCompatActivity {
//MÜŞTERİ KAYIT
    private FirebaseAuth mAuth;
    private EditText  musteriAd, musteriSoyad, musteriAdres, musteriPass, musteriMail,musteriTel;
    private Button btnMusteriKayit, btnKuryeKayit, btnRestoranKayit;
    FirebaseFirestore dbMusteriKayit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_ol);

        mAuth = FirebaseAuth.getInstance();

        musteriAd = (EditText)findViewById(R.id.txtMusteriAd);
        musteriSoyad = (EditText)findViewById(R.id.txtMusteriSoyad);
        musteriAdres = (EditText)findViewById(R.id.txtMusteriAdres);
        musteriPass = (EditText)findViewById(R.id.txtMusteriPass);
        musteriMail = (EditText)findViewById(R.id.txtMusteriMail);
        musteriTel = (EditText)findViewById(R.id.txtMusteriTel);
        btnMusteriKayit = (Button) findViewById(R.id.btnMusteriKayit);
        btnKuryeKayit = (Button) findViewById(R.id.btnKuryeKayit);
        btnRestoranKayit = (Button) findViewById(R.id.btnRestoranKayit);
        dbMusteriKayit = FirebaseFirestore.getInstance();


        btnMusteriKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertMusteriKayit();
            }
        });

        btnRestoranKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), RestoranKayit.class);
                startActivity(intent);
            }
        });

        btnKuryeKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), KuryeKayit.class);
                startActivity(intent);
            }
        });
    }
    public void insertMusteriKayit()
    {
        String ad,soyad, tel, pass,adres,mail;
        ad = musteriAd.getText().toString();
        soyad = musteriSoyad.getText().toString().trim();
        pass = musteriPass.getText().toString();
        mail = musteriMail.getText().toString().trim();
        adres = musteriAdres.getText().toString();
        tel = musteriTel.getText().toString();

        Map<String, String> itemMusteriKayit = new HashMap<>();

        if((TextUtils.isEmpty(ad)) || (TextUtils.isEmpty(soyad)) ||  (TextUtils.isEmpty(pass)) ||
                (TextUtils.isEmpty(mail)) || (TextUtils.isEmpty(adres)) || (TextUtils.isEmpty(tel)))
        {
            Toast.makeText(UyeOl.this,"LÜTFEN BOŞ BIRAKMAYINIZ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    itemMusteriKayit.put("name", ad);
                    itemMusteriKayit.put("lastName", soyad);
                    itemMusteriKayit.put("email", mail);
                    itemMusteriKayit.put("password", pass);
                    itemMusteriKayit.put("address", adres);
                    itemMusteriKayit.put("phone", tel);
                    dbMusteriKayit.collection("Customer").add(itemMusteriKayit).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(UyeOl.this, "Üye kaydı başarılı", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            });

        }

    }
}