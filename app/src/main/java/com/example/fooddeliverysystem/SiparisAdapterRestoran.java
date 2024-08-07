package com.example.fooddeliverysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SiparisAdapterRestoran extends FirestoreRecyclerAdapter<OrderModel, SiparisAdapterRestoran.holder> {

    public SiparisAdapterRestoran(@NonNull FirestoreRecyclerOptions<OrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SiparisAdapterRestoran.holder holder, int position, @NonNull OrderModel model) {
        String name, lastname, fullname;
        name = model.getCustomerName();
        lastname = model.getCustomerLastName();
        fullname = name + " " + lastname;
        holder.tvMusteriAdSoyad.setText(fullname);
        holder.tvMusteriAdres.setText(model.getCustomerAddress());
        holder.tvYemek.setText(model.getFood());
        holder.tvFiyat.setText(String.valueOf(model.getPrice()) + " TL");
        holder.tvTarih.setText(model.getDate());
    }

    @NonNull
    @Override
    public SiparisAdapterRestoran.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_restoran_siparisler, parent, false);
        return new holder(view);
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView tvMusteriAdSoyad, tvMusteriAdres, tvYemek ,tvFiyat, tvTarih;
        public holder(@NonNull View itemView) {
            super(itemView);

            tvMusteriAdSoyad = itemView.findViewById(R.id.tvRestoranAdSoyad);
            tvMusteriAdres = itemView.findViewById(R.id.tvRestoranAdres);
            tvYemek = itemView.findViewById(R.id.tvRestoranYemek);
            tvFiyat = itemView.findViewById(R.id.tvRestoranFiyat);
            tvTarih = itemView.findViewById(R.id.tvRestoranTarih);
        }
    }
}
