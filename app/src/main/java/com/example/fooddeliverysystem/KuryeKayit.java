package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class KuryeKayit extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnMusteriKayit, btnRestoranKayit, btnKuryeKayit;
    private EditText txtKuryeAd, txtKuryeSoyad, txtKuryeEmail, txtKuryeSifre;
    private Spinner spinnerKanGrubu;
    private CheckBox cbMotosiklet, cbBisiklet;
    ArrayAdapter<CharSequence> adapterKan;
    FirebaseFirestore dbKuryeKayit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurye_kayit);

        mAuth = FirebaseAuth.getInstance();

        btnMusteriKayit = (Button) findViewById(R.id.btnMusteriKayit3);
        btnRestoranKayit = (Button) findViewById(R.id.btnRestoranKayit3);
        btnKuryeKayit = (Button) findViewById(R.id.btnKuryeKayit3);
        txtKuryeAd = (EditText) findViewById(R.id.txtKuryeAd);
        txtKuryeSoyad = (EditText) findViewById(R.id.txtKuryeSoyad);
        spinnerKanGrubu = (Spinner) findViewById(R.id.spinnerKanGrubu);
        cbMotosiklet = (CheckBox) findViewById(R.id.cbMotosiklet);
        cbBisiklet = (CheckBox) findViewById(R.id.cbBisiklet);
        txtKuryeEmail = (EditText) findViewById(R.id.txtKuryeMail);
        txtKuryeSifre = (EditText) findViewById(R.id.txtKuryeSifre);

        adapterKan = ArrayAdapter.createFromResource(this,R.array.kanGrubu_txt,android.R.layout.simple_spinner_dropdown_item);
        adapterKan.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerKanGrubu.setAdapter(adapterKan);

        dbKuryeKayit = FirebaseFirestore.getInstance();

        btnMusteriKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), UyeOl.class);
                startActivity(intent);
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
                btnKuryeKayit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertKuryeKayit();
                    }
                });
            }
        });
    }
    public void insertKuryeKayit(){

        String kuryeAd,kuryeSoyad,kanGrup,cbCikti, kuryeMail, kuryeSifre;
        kuryeAd = txtKuryeAd.getText().toString();
        kuryeSoyad = txtKuryeSoyad.getText().toString();
        kuryeMail = txtKuryeEmail.getText().toString();
        kuryeSifre = txtKuryeSifre.getText().toString();
        kanGrup = spinnerKanGrubu.getSelectedItem().toString();

        Map<String,String> itemKuryeKayit = new HashMap<>();

        if (cbMotosiklet.isChecked()  && cbBisiklet.isChecked() ){
            cbCikti = "Hepsi";
        }
        else if (cbBisiklet.isChecked()){
            cbCikti = "Bisiklet";
        }
        else if ( cbMotosiklet.isChecked()) {
            cbCikti = "Motosiklet";
        }
        else{
            cbCikti = "Yok";
        }

        if ((TextUtils.isEmpty(kuryeAd)) || (TextUtils.isEmpty(kuryeSoyad)) ||
                (TextUtils.isEmpty(kuryeMail)) || (TextUtils.isEmpty(kuryeSifre)) )
        {
            Toast.makeText(KuryeKayit.this,"LÜTFEN BOŞ BIRAKMAYINIZ", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(kuryeMail,kuryeSifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        itemKuryeKayit.put("blood", kanGrup);
                        itemKuryeKayit.put("email", kuryeMail);
                        itemKuryeKayit.put("lastName", kuryeSoyad);
                        itemKuryeKayit.put("name", kuryeAd);
                        itemKuryeKayit.put("password", kuryeSifre);
                        itemKuryeKayit.put("vehicle", cbCikti);
                        dbKuryeKayit.collection("Courier").add(itemKuryeKayit).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(KuryeKayit.this, "Kurye kaydı başarılı", Toast.LENGTH_SHORT).show();
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