package com.example.fooddeliverysystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<MenuModel> menuList;
    private MenuBuilder menuBuilder;
    private LayoutInflater inflater;
    private boolean isRestaurantMenu;
    private int itemCount;

    public MenuAdapter(MenuBuilder menuBuilder, LayoutInflater inflater, boolean isRestaurantMenu, int itemCount) {
        this.menuBuilder = menuBuilder;
        this.inflater = inflater;
        this.isRestaurantMenu = isRestaurantMenu;
        this.itemCount = itemCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_musteri_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuModel menu = menuList.get(position);

        holder.tvUrunAdi.setText(menu.getUrunAdi());
        holder.tvMutfak.setText(menu.getMutfak());
        holder.tvAciklama.setText(menu.getAciklama());
        String fiyat = Integer.toString(menu.getFiyat());
        holder.tvFiyat.setText(fiyat);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUrunAdi, tvMutfak, tvAciklama, tvFiyat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUrunAdi = itemView.findViewById(R.id.tvMusteriMenuYemek);
            tvMutfak = itemView.findViewById(R.id.tvMusteriMenuKategori);
            tvAciklama = itemView.findViewById(R.id.tvMusteriMenuAciklama);
            tvFiyat = itemView.findViewById(R.id.tvMusteriMenuFiyat);
        }
    }
}

