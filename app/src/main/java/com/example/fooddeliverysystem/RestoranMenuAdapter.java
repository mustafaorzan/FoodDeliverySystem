package com.example.fooddeliverysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RestoranMenuAdapter extends FirestoreRecyclerAdapter<MenuModel, RestoranMenuAdapter.holder> {

    public RestoranMenuAdapter(@NonNull FirestoreRecyclerOptions<MenuModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull MenuModel model) {
        holder.tvYemek.setText(model.getUrunAdi());
        holder.tvAciklama.setText(model.getAciklama());
        holder.tvKategori.setText(model.getMutfak());
        String fiyat = Integer.toString(model.getFiyat());
        holder.tvFiyat.setText(fiyat);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_restoran_menu, parent, false);
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{

        TextView tvYemek, tvAciklama, tvKategori, tvFiyat;
        public holder (View itemview){
            super (itemview);

            tvYemek = itemview.findViewById(R.id.tvMusteriMenuYemek);
            tvAciklama = itemview.findViewById(R.id.tvYemekAciklama);
            tvKategori = itemview.findViewById(R.id.tvYemekKategori);
            tvFiyat = itemview.findViewById(R.id.tvYemekFiyat);
        }
    }
}
