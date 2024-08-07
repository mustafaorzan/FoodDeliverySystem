package com.example.fooddeliverysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class RestaurantAdapterCustomer extends FirestoreRecyclerAdapter<RestaurantModel, RestaurantAdapterCustomer.holder> {

    private OnItemClickListener listener;
    public RestaurantAdapterCustomer(@NonNull FirestoreRecyclerOptions<RestaurantModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull RestaurantModel model) {
        //holder.tvRestoranKategori.setText(model.getCategory());
        holder.tvRestoranAd.setText(model.getName());
        holder.tvRestoranAdres.setText(model.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && listener!=null){
                    DocumentSnapshot snapshot = getSnapshots().getSnapshot(adapterPosition);
                    listener.OnItemClick(snapshot, adapterPosition);
                }
            }
        });
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_musteri_tumrestoranlar,parent,false);
        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder {
        TextView tvRestoranAd, tvRestoranAdres, tvRestoranKategori;

        public holder(@NonNull View itemView) {
            super(itemView);

            tvRestoranAd = itemView.findViewById(R.id.tvMusteriRestoranAdi);
            tvRestoranAdres = itemView.findViewById(R.id.tvMusteriRestoranAdres);
            //tvRestoranKategori = itemView.findViewById(R.id.tvMusteriRestoranKategori);
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
