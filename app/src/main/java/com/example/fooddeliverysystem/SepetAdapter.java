package com.example.fooddeliverysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SepetAdapter extends RecyclerView.Adapter<SepetAdapter.ViewHolder> {

    private List<OrderModel> orderList;
    private String quantity, price;
    private int sayi, newFiyat, oldFiyat;

    public SepetAdapter(List<OrderModel> orderList) {this.orderList=orderList;}

    public int getSayi() {
        return sayi;
    }

    public void setSayi(int sayi) {
        this.sayi = sayi;
    }

    @NonNull
    @Override
    public SepetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_sepet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SepetAdapter.ViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.restoranAd.setText(order.getRestaurantName());

        holder.urun.setText(order.getFood());

        holder.fiyat.setText(String.valueOf(order.getPrice()));
        price = holder.fiyat.getText().toString();//burada kaldık

        holder.adet.setText("1");

        quantity = holder.adet.getText().toString();
        sayi= Integer.parseInt(quantity);

        holder.btnArti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayi = sayi + 1 ;
                holder.adet.setText(String.valueOf(sayi));
                oldFiyat = Integer.parseInt(price);
                newFiyat = oldFiyat * sayi;
                holder.fiyat.setText(String.valueOf(newFiyat));
                setSayi(sayi);//Activvity e gönder
            }
        });

        holder.btnEksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayi = sayi - 1 ;
                holder.adet.setText(String.valueOf(sayi));
                oldFiyat = Integer.parseInt(price);
                newFiyat = oldFiyat * sayi;
                holder.fiyat.setText(String.valueOf(newFiyat));
                setSayi(sayi);//Activvity e gönder
            }
        });
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnArti, btnEksi;
        TextView adet, restoranAd, urun, fiyat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adet = itemView.findViewById(R.id.tvSepetAdet);
            restoranAd = itemView.findViewById(R.id.tvSepetRestoranAd);
            urun = itemView.findViewById(R.id.tvSepetYemek);
            fiyat = itemView.findViewById(R.id.tvSepetFiyat);
            btnArti = itemView.findViewById(R.id.buttonSepetArti);
            btnEksi = itemView.findViewById(R.id.buttonSepetEksi);
        }

    }
}
