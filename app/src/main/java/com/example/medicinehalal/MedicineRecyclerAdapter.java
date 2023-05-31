package com.example.medicinehalal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class MedicineRecyclerAdapter extends FirestoreRecyclerAdapter<Medicine, MedicineRecyclerAdapter.MedicineViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final OnItemClickListener listener;

    public Context context;


    MedicineRecyclerAdapter(FirestoreRecyclerOptions<Medicine> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    MedicineRecyclerAdapter(FirestoreRecyclerOptions<Medicine> options) {
        super(options);
        this.listener = null;
    }

    @Override
    protected void onBindViewHolder(final MedicineViewHolder holder, @NonNull int position, @NonNull final Medicine model) {
        holder.medicinename.setText(model.getmedicinename());
        holder.medicineserialnumber.setText(model.getseialnumber());
        holder.medicinemalnumber.setText(model.getmalnumber());
        holder.medicinehalalstatus.setText(model.gethalalstatus());
        holder.medicinemanufacturer.setText(model.getmanufacturer());
        holder.medicineactiveingredient.setText(model.getactiveingredient());
        holder.medicineinactiveingredient.setText(model.getinactiveingredient());
        holder.medicineharamngredient.setText(model.getharamingredient());
        holder.medicinemushboohingredient.setText(model.getmushboohingredient());
        Glide.with(holder.medicinepicture.getContext()).load(model.getmedicinepicture()).into(holder.medicinepicture);

        if (listener != null) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            });
        }
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder {
        final CardView view;
        final TextView medicinename;
        final TextView medicineserialnumber;
        final TextView medicinemalnumber;
        final TextView medicinehalalstatus;
        final TextView medicinemanufacturer;
        final TextView medicineactiveingredient;
        final TextView medicineinactiveingredient;
        final TextView medicineharamngredient;
        final TextView medicinemushboohingredient;
        final ImageView medicinepicture;

        MedicineViewHolder(CardView v) {
            super(v);
            view = v;
            medicinename = v.findViewById(R.id.medicinenameresult);
            medicineserialnumber = v.findViewById(R.id.medicinebarcoderesult);
            medicinemalnumber = v.findViewById(R.id.medicinemalnumberresult);
            medicinehalalstatus = v.findViewById(R.id.medicinehalalstatusresult);
            medicinemanufacturer = v.findViewById(R.id.medicinemanufacturerresult);
            medicineactiveingredient = v.findViewById(R.id.medicineactiveingredientresult);
            medicineinactiveingredient = v.findViewById(R.id.medicineinactiveingredientresult);
            medicineharamngredient = v.findViewById(R.id.medicineharamingredientresult);
            medicinemushboohingredient = v.findViewById(R.id.medicinemushbooheingredientresult);
            medicinepicture = v.findViewById(R.id.medicinepictureresult);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new MedicineViewHolder(v);
    }
}
