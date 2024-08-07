package com.example.fooddeliverysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RestaurantAdapter extends FirestoreRecyclerAdapter<RestaurantModel, RestaurantAdapter.holder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RestaurantAdapter(@NonNull FirestoreRecyclerOptions<RestaurantModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RestaurantAdapter.holder holder, int position, @NonNull RestaurantModel model) {
        holder.restName.setText(model.getName());
        holder.restAddress.setText(model.getAddress());
        holder.restEmail.setText(model.getEmail());
        holder.restPass.setText(model.getPassword());
        holder.restPhone.setText(model.getPhone());
    }

    @NonNull
    @Override
    public RestaurantAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_admin_restoran, parent, false);

        return new holder(view);
    }

    class holder extends RecyclerView.ViewHolder{

        TextView restName, restAddress,restPhone, restPass, restEmail;


        public holder(@NonNull View itemView) {
            super(itemView);
            View view = itemView;

            restName = view.findViewById(R.id.tvAdminRestoranAd);
            restAddress = view.findViewById(R.id.tvAdminRestoranAdres);
            restPhone = view.findViewById(R.id.tvAdminRestoranTel);
            restPass = view.findViewById(R.id.tvAdminRestoranSifre);
            restEmail = view.findViewById(R.id.tvAdminRestoranMail);
        }
    }
}
