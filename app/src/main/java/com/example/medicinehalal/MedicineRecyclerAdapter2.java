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

public class MedicineRecyclerAdapter2 extends FirestoreRecyclerAdapter<Medicine, MedicineRecyclerAdapter2.MedicineViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final MedicineRecyclerAdapter2.OnItemClickListener listener;

    public Context context;


    MedicineRecyclerAdapter2(FirestoreRecyclerOptions<Medicine> options, MedicineRecyclerAdapter2.OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    MedicineRecyclerAdapter2(FirestoreRecyclerOptions<Medicine> options) {
        super(options);
        this.listener = null;
    }

    @Override
    protected void onBindViewHolder(final MedicineRecyclerAdapter2.MedicineViewHolder holder, @NonNull int position, @NonNull final Medicine model) {
        holder.medicinename2.setText(model.getmedicinename());
        holder.medicineserialnumber2.setText(model.getseialnumber());
        holder.medicinemalnumber2.setText(model.getmalnumber());
        holder.medicinecounterfeitstatus2.setText(model.getcounterfeitstatus());
        holder.medicinemanufacturer2.setText(model.getmanufacturer());
        Glide.with(holder.medicinepicture2.getContext()).load(model.getmedicinepicture()).into(holder.medicinepicture2);

        if (listener != null) {
            holder.view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            });
        }
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder {
        final CardView view2;
        final TextView medicinename2;
        final TextView medicineserialnumber2;
        final TextView medicinemalnumber2;
        final TextView medicinecounterfeitstatus2;
        final TextView medicinemanufacturer2;
        final ImageView medicinepicture2;

        MedicineViewHolder(CardView v) {
            super(v);
            view2 = v;
            medicinename2 = v.findViewById(R.id.medicinenameresult2);
            medicineserialnumber2 = v.findViewById(R.id.medicinebarcoderesult2);
            medicinemalnumber2 = v.findViewById(R.id.medicinemalnumberresult2);
            medicinecounterfeitstatus2 = v.findViewById(R.id.medicinecounterfeitstatusresult2);
            medicinemanufacturer2 = v.findViewById(R.id.medicinemanufacturerresult2);
            medicinepicture2 = v.findViewById(R.id.medicinepictureresult2);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MedicineRecyclerAdapter2.MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout2, parent, false);
        return new MedicineRecyclerAdapter2.MedicineViewHolder(v);
    }
}
