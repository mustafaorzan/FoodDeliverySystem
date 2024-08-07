package com.example.fooddeliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MusteriAnaSayfa extends AppCompatActivity {

    BottomNavigationView btmNavMusteriAnasayfa;
    RecyclerView rvRestoran;
    FirebaseFirestore fstore;
    RestaurantAdapterCustomer restaurantAdapterCustomer;
    ArrayList<CategoryModel> categoryModel = new ArrayList<CategoryModel>();
    ArrayList<RestaurantModel> restaurantModel = new ArrayList<>();
    ImageView ivFiltre;
    TextView tv2;
    String mAd, mSoyad, mId, mAdres, rId, rAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_ana_sayfa);

        tv2 = findViewById(R.id.textView2);

        //MainActivity den veri çekme ve ActionBar Title değiştirme
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
            System.out.println(" MUSTERI ANASAYFA ");
            System.out.println(" HOŞGELDİNİZ ");
            System.out.println("Müşteri ID: " + mId);
            System.out.println("Müşteri Ad: " + mAd);
            System.out.println("Müşteri Soyad: " + mSoyad);
            System.out.println("Müşteri Adres: " + mAdres);

            System.out.println("-------------------------------------------");

            String fullName = musteriAd + " " + musteriSoyad;
            tv2.setText("Adres: " + mAdres);

            getSupportActionBar().setTitle("Müşteri: " + fullName);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview_anasayfa_kategori);
        rvRestoran = findViewById(R.id.recyclerview_anasayfa_tumRestoranlar);
        btmNavMusteriAnasayfa = findViewById(R.id.btmNavMusteri);

        rvRestoran.setLayoutManager(new LinearLayoutManager(MusteriAnaSayfa.this));

        fstore= FirebaseFirestore.getInstance();
        Query query = fstore.collection("Restaurant");

        FirestoreRecyclerOptions<RestaurantModel> options =
                new FirestoreRecyclerOptions.Builder<RestaurantModel>()
                        .setQuery(query, RestaurantModel.class)
                        .build();

        restaurantAdapterCustomer = new RestaurantAdapterCustomer(options);
        rvRestoran.setAdapter(restaurantAdapterCustomer);
        restaurantAdapterCustomer.startListening();

        ivFiltre = findViewById(R.id.ivFiltre);

        setCategoryModel();

        CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(this, categoryModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ivFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFiltre = new Intent(getApplicationContext(),MusteriFiltre.class);
                startActivity(intentFiltre);
            }
        });

        btmNavMusteriAnasayfa.setSelectedItemId(R.id.itemMusteriAnasayfa);

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
                                    Toast.makeText(MusteriAnaSayfa.this, "Tıklama başarılı", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MusteriRestoranMenu.class);
                                    intent.putExtra("restaurantId", restaurantId);
                                    intent.putExtra("musteriId", mId);
                                    intent.putExtra("musteriAd", mAd);
                                    intent.putExtra("musteriSoyad", mSoyad);
                                    intent.putExtra("restoranAd", restaurantName);
                                    intent.putExtra("musteriAdres", mAdres);
                                    startActivity(intent);
                                    /*for (QueryDocumentSnapshot document : task.getResult()){
                                        //"menu" alt belgesindeki verileri al ve kullan
                                        //ör, menü ögelerini RecyclerView da göstermek için liste oluştur
                                    }*/
                                } else{
                                    //Sorgu basarısız olduysa hata işleme yapılabilir
                                }
                            }
                        });
            }
        });

        btmNavMusteriAnasayfa.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.itemMusteriAnasayfa:
                        return true;
                    case R.id.itemMusteriRestoranlar:
                        Intent intent = new Intent(getApplicationContext(),MusteriRestoranlar.class);
                        intent.putExtra("restaurantId", rId);
                        intent.putExtra("musteriId", mId);
                        intent.putExtra("musteriAd", mAd);
                        intent.putExtra("musteriSoyad", mSoyad);
                        intent.putExtra("musteriAdres", mAdres);
                        intent.putExtra("restoranAd", rAd);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.itemMusteriGecmis:
                        Intent intentGecmis = new Intent(getApplicationContext(),MusteriSiparisler.class);
                        intentGecmis.putExtra("musteriId", mId);
                        intentGecmis.putExtra("musteriAd", mAd);
                        intentGecmis.putExtra("musteriSoyad", mSoyad);
                        intentGecmis.putExtra("musteriAdres", mAdres);
                        startActivity(intentGecmis);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private void setCategoryModel(){
        String[] categoryNames = getResources().getStringArray(R.array.categories_txt);
        for (int i=0; i<categoryNames.length; i++){
            categoryModel.add(new CategoryModel(categoryNames[i]));
        }
    }

}