package com.example.fooddeliverysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SiparisAdapterMusteri extends FirestoreRecyclerAdapter<OrderModel, SiparisAdapterMusteri.holder> {


    public SiparisAdapterMusteri(@NonNull FirestoreRecyclerOptions<OrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SiparisAdapterMusteri.holder holder, int position, @NonNull OrderModel model) {
        holder.tvRestoranAd.setText(model.getRestaurantName());
        holder.tvTarih.setText(model.getDate());
        holder.tvFiyat.setText(String.valueOf(model.getPrice()) + " TL");
        holder.tvUrun.setText(model.getFood());
    }

    @NonNull
    @Override
    public SiparisAdapterMusteri.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_musteri_siparisler, parent, false);
        return new holder(view);
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView tvRestoranAd, tvUrun, tvFiyat, tvTarih;
        public holder(@NonNull View itemView) {
            super(itemView);

            tvRestoranAd = itemView.findViewById(R.id.tvMusteriSiparisRestoranAd);
            tvUrun = itemView.findViewById(R.id.tvMusteriSiparisUrun);
            tvFiyat = itemView.findViewById(R.id.tvMusteriSiparisFiyat);
            tvTarih = itemView.findViewById(R.id.tvMusteriSiparisTarih);
        }
    }
}
