package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RestoranKayit extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnMusteriKayit, btnKuryeKayit, btnRestoranKayit;
    private EditText txtRestoranAd, txtRestoranAdres, txtRestoranTel, txtRestoranMail, txtRestoranSifre;
    FirebaseFirestore dbRestoranKayit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_kayit);

        mAuth = FirebaseAuth.getInstance();

        btnMusteriKayit = (Button) findViewById(R.id.btnMusteriKayit2);
        btnKuryeKayit = (Button) findViewById(R.id.btnKuryeKayit2);
        btnRestoranKayit = (Button) findViewById(R.id.btnRestoranKayit2);
        txtRestoranAd = (EditText) findViewById(R.id.txtRestoranAd);
        txtRestoranAdres = (EditText) findViewById(R.id.txtRestoranAdres);
        txtRestoranTel = (EditText) findViewById(R.id.txtRestoranTel);
        txtRestoranMail = (EditText) findViewById(R.id.txtRestoranMail);
        txtRestoranSifre = (EditText) findViewById(R.id.txtRestoranSifre);

        dbRestoranKayit = FirebaseFirestore.getInstance();


        btnMusteriKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), UyeOl.class);
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

        btnRestoranKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRestoranKayit();
            }
        });
    }
    public void insertRestoranKayit(){
        String restAd, restAdres, restTel,restSifre,restMail ;

        restAd = txtRestoranAd.getText().toString();
        restAdres = txtRestoranAdres.getText().toString();
        restTel = txtRestoranTel.getText().toString();
        restSifre = txtRestoranSifre.getText().toString();
        restMail = txtRestoranMail.getText().toString();

        Map<String,String> itemRestoranKayit = new HashMap<>();

        if ( (TextUtils.isEmpty(restAd)) || (TextUtils.isEmpty(restAdres)) || (TextUtils.isEmpty(restTel)) || (TextUtils.isEmpty(restMail)) ||
                ( (TextUtils.isEmpty(restSifre)) ) ){
            Toast.makeText(RestoranKayit.this,"LÜTFEN BOŞ BIRAKMAYINIZ", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(restMail,restSifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        itemRestoranKayit.put("name", restAd);
                        itemRestoranKayit.put("address", restAdres);
                        itemRestoranKayit.put("phone", restTel);
                        itemRestoranKayit.put("password", restSifre);
                        itemRestoranKayit.put("email", restMail);
                        dbRestoranKayit.collection("Restaurant").add(itemRestoranKayit).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(RestoranKayit.this, "Üye kaydı başarılı", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
            });

        }
    }
}