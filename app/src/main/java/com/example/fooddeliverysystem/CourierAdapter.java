package com.example.fooddeliverysystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class CourierAdapter extends FirestoreRecyclerAdapter<CourierModel, CourierAdapter.holder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CourierAdapter(@NonNull FirestoreRecyclerOptions<CourierModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CourierAdapter.holder holder, int position, @NonNull CourierModel model) {
        holder.kuryeAd.setText(model.getName());
        holder.kuryeSoyad.setText(model.getLastName());
        holder.kuryeMail.setText(model.getEmail());
        holder.kuryeSifre.setText(model.getPassword());
        holder.kuryeArac.setText(model.getVehicle());
        holder.kuryeKan.setText(model.getBlood());
    }

    @NonNull
    @Override
    public CourierAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_admin_kurye,parent, false);
        return new holder(view);
    }

    public class holder extends RecyclerView.ViewHolder {

        TextView kuryeAd, kuryeSoyad, kuryeKan, kuryeMail, kuryeSifre, kuryeArac;
        public holder(@NonNull View itemView) {
            super(itemView);

            kuryeAd = itemView.findViewById(R.id.tvAdminKuryeAd);
            kuryeSoyad = itemView.findViewById(R.id.tvAdminKuryeSoyad);
            kuryeKan = itemView.findViewById(R.id.tvAdminKuryeKan);
            kuryeArac = itemView.findViewById(R.id.tvAdminKuryeArac);
            kuryeMail = itemView.findViewById(R.id.tvAdminKuryeMail);
            kuryeSifre = itemView.findViewById(R.id.tvAdminKuryeSifre);

        }
    }
}
