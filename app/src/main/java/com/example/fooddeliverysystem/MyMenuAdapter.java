package com.example.fooddeliverysystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyMenuAdapter extends RecyclerView.Adapter<MyMenuAdapter.ViewHolder> {
    private List<MenuModel> menuList;
    private String mId, mAd, mSoyad, mAdres, rId, rAd, tarih;

    public MyMenuAdapter(List<MenuModel> menuList) {
        this.menuList = menuList;
    }
    public void setMId(String mId) {
        this.mId = mId;
    }

    public void setMAd(String mAd) {
        this.mAd = mAd;
    }

    public void setMSoyad(String mSoyad) {
        this.mSoyad = mSoyad;
    }

    public void setMAdres(String mAdres) {
        this.mAdres = mAdres;
    }

    public void setRId(String rId) {
        this.rId = rId;
    }

    public void setRAd(String rAd) {
        this.rAd = rAd;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_musteri_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMenuAdapter.ViewHolder holder, int position) {
        MenuModel menu = menuList.get(position);

        Intent intent;
        //String mId, mAd, mSoyad, mAdres, rId, rAd, tarih;

        String urun = menu.getUrunAdi(); //MenuModel -> urunAdi bilgisini çek
        holder.tvUrunAdi.setText(urun); // Burada değişiklik yaptık. (menu.getUrunAdi()) değişti
        holder.tvMutfak.setText(menu.getMutfak());
        holder.tvAciklama.setText(menu.getAciklama());
        String fiyat = Integer.toString(menu.getFiyat()); //MenuModel -> fiyat bilgisini String olarak çek
        holder.tvFiyat.setText(fiyat);

        holder.btnMusteriMenuEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusteriSepet.class);
                intent.putExtra("urun", urun);
                intent.putExtra("fiyat", fiyat);
                intent.putExtra("restoranId", rId);
                intent.putExtra("restoranAd", rAd);
                intent.putExtra("musteriId", mId);
                intent.putExtra("musteriAd", mAd);
                intent.putExtra("musteriSoyad", mSoyad);
                intent.putExtra("musteriAdres", mAdres);
                intent.putExtra("tarih", tarih);
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "Düğme tıklandı: ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUrunAdi, tvMutfak, tvAciklama, tvFiyat;
        Button btnMusteriMenuEkle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUrunAdi = itemView.findViewById(R.id.tvMusteriMenuYemek);
            tvMutfak = itemView.findViewById(R.id.tvMusteriMenuKategori);
            tvAciklama = itemView.findViewById(R.id.tvMusteriMenuAciklama);
            tvFiyat = itemView.findViewById(R.id.tvMusteriMenuFiyat);
            btnMusteriMenuEkle = itemView.findViewById(R.id.btnMusteriMenuEkle);
        }
    }
}
