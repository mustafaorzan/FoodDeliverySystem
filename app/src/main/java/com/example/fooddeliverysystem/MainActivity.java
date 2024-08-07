package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    //FirebaseAuth mAuth;
    FirebaseUser user;
    private EditText txtUser, txtPass;
    private Button btnLogin,btnSignUp;
    private RadioButton rbMusteri, rbRestoran;
    private String userMail, pass, userID;

    private FirebaseFirestore fstore;
    //CollectionReference customerRef = db.collection("Customer");
    //CollectionReference restaurantRef = db.collection("Restaurant");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = (EditText)findViewById(R.id.txtUser);
        txtPass = (EditText)findViewById(R.id.txtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        rbMusteri = (RadioButton)findViewById(R.id.radioMusteriGiris);
        rbRestoran = (RadioButton)findViewById(R.id.radioRestoranGiris);

        fstore = FirebaseFirestore.getInstance();
        //mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userMail = txtUser.getText().toString(); //kullanıcı emailleri buradan alınacak (Customer, Restaurant, Courier)
                pass = txtPass.getText().toString();

                if (userMail.isEmpty() || pass.isEmpty())
                    Toast.makeText(MainActivity.this, "Lütfen e-mail ve şifre girin.", Toast.LENGTH_SHORT).show();
                else{
                    if (rbMusteri.isChecked())
                        login(userMail,pass);
                    else if(rbRestoran.isChecked())
                        loginRestaurant(userMail,pass);
                    else
                        Toast.makeText(MainActivity.this,"Lütfen giriş türünü seçin!", Toast.LENGTH_SHORT).show();
                }

                //Admin girişi için...
                if (userMail.equals("admin") && (pass.equals("123456"))){
                    if (rbMusteri.isChecked()){
                        Toast.makeText(MainActivity.this,"Giriş Başarılı. Müşteri Ekranına Hoşgeldin", Toast.LENGTH_SHORT).show();
                        Intent intentMusteriGiris = new Intent (getApplicationContext(), MusteriAnaSayfa.class);
                        startActivity(intentMusteriGiris);
                    }
                    else if (rbRestoran.isChecked()){
                        Toast.makeText(MainActivity.this,"Giriş Başarılı. Restoran Ekranına Hoşgeldin", Toast.LENGTH_SHORT).show();
                        Intent intentRestoranGiris = new Intent (getApplicationContext(), RestoranAnaSayfa.class);
                        startActivity(intentRestoranGiris);
                    }
                    else{
                        Intent intentAdminPanel = new Intent (getApplicationContext(), AdminPanel.class);
                        startActivity(intentAdminPanel);
                        Toast.makeText(MainActivity.this,"HOŞGELDİNİZ YÜCE ADMİN", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), UyeOl.class);
                startActivity(intent);
            }
        });
    }
    private void login(final String email, final String password){
        fstore.collection("Customer").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot!= null && !querySnapshot.isEmpty()){
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        String musteriId = document.getId();
                        String storedPassword = document.getString("password");
                        if (storedPassword != null && storedPassword.equals(password)){
                            String musteriAd = document.getString("name");
                            String musteriSoyad = document.getString("lastName");
                            String musteriAdres = document.getString("address");
                            String musteriMail = document.getString("email");

                            Intent intent = new Intent(MainActivity.this, MusteriAnaSayfa.class);
                            intent.putExtra("musteriAd", musteriAd);
                            intent.putExtra("musteriSoyad", musteriSoyad);
                            intent.putExtra("musteriAdres", musteriAdres);
                            intent.putExtra("musteriMail", musteriMail);
                            intent.putExtra("musteriId", musteriId);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "HATALI ŞİFRE!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Kullanıcı Bulunamadı!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "HATA -> " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loginRestaurant (final String email, final String password){
        fstore.collection("Restaurant").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot!= null && !querySnapshot.isEmpty()){
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        String restoranId = document.getId();
                        String storedPassword = document.getString("password");
                        if (storedPassword != null && storedPassword.equals(password)){
                            String restoranAd = document.getString("name");
                            String restoranAdres = document.getString("address");
                            String restoranMail = document.getString("email");

                            Intent intent = new Intent(MainActivity.this, RestoranAnaSayfa.class);
                            intent.putExtra("restoranAd", restoranAd);
                            intent.putExtra("restoranAdres", restoranAdres);
                            intent.putExtra("restoranMail", restoranMail);
                            intent.putExtra("restoranId", restoranId);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "HATALI ŞİFRE!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Kullanıcı Bulunamadı!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "HATA -> " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}