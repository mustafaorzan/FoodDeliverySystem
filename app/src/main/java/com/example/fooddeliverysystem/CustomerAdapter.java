package com.example.fooddeliverysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CustomerAdapter extends FirestoreRecyclerAdapter<CustomerModel, CustomerAdapter.holder> {

    public CustomerAdapter(@NonNull FirestoreRecyclerOptions<CustomerModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CustomerAdapter.holder holder, int position, @NonNull CustomerModel model) {
        holder.tvMusteriAd.setText(model.getName());
        holder.tvMusteriSoyad.setText(model.getLastName());
        holder.tvMusteriAdres.setText(model.getAddress());
        holder.tvMusteriTel.setText(model.getPhone());
        holder.tvMusteriMail.setText(model.getEmail());
        holder.tvMusteriSifre.setText(model.getPassword());
    }

    @NonNull
    @Override
    public CustomerAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclervier_admin_musteriler,parent, false);
        return new holder(view);
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView tvMusteriAd, tvMusteriSoyad, tvMusteriAdres, tvMusteriTel, tvMusteriMail, tvMusteriSifre;
        public holder(@NonNull View itemView) {
            super(itemView);

            tvMusteriAd = itemView.findViewById(R.id.tvAdminMusteriAd);
            tvMusteriSoyad = itemView.findViewById(R.id.tvAdminMusteriSoyad);
            tvMusteriAdres = itemView.findViewById(R.id.tvAdminMusteriAdres);
            tvMusteriTel = itemView.findViewById(R.id.tvAdminMusteriTel);
            tvMusteriMail = itemView.findViewById(R.id.tvAdminMusteriMail);
            tvMusteriSifre = itemView.findViewById(R.id.tvAdminMusteriSifre);
        }
    }
}
