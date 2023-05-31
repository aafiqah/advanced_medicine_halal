package com.example.medicinehalal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MedicineRecyclerAdapter3 extends FirestoreRecyclerAdapter<Medicine, MedicineRecyclerAdapter3.MedicineViewHolder3> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final MedicineRecyclerAdapter3.OnItemClickListener listener;

    public Context context;
    private Medicine medicine;


    MedicineRecyclerAdapter3(FirestoreRecyclerOptions<Medicine> options, MedicineRecyclerAdapter3.OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    MedicineRecyclerAdapter3(FirestoreRecyclerOptions<Medicine> options) {
        super(options);
        this.listener = null;
    }

    @Override
    protected void onBindViewHolder(@NonNull MedicineRecyclerAdapter3.MedicineViewHolder3 holder, @SuppressLint("RecyclerView") int position, @NonNull Medicine model) {
        holder.medicinename.setText(model.getmedicinename());
        holder.medicineserialnumber.setText(model.getseialnumber());
        holder.medicinemalnumber.setText(model.getmalnumber());
        holder.medicinehalalstatus.setText(model.gethalalstatus());
        holder.medicinecounterfeitstatus.setText(model.getcounterfeitstatus());
        holder.medicinemanufacturer.setText(model.getmanufacturer());
        holder.medicineactiveingredient.setText(model.getactiveingredient());
        holder.medicineinactiveingredient.setText(model.getinactiveingredient());
        holder.medicineharamngredient.setText(model.getharamingredient());
        holder.medicinemushboohingredient.setText(model.getmushboohingredient());
        Glide.with(holder.medicinepicture.getContext()).load(model.getmedicinepicture()).into(holder.medicinepicture);


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.medicinename.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent2))
                        .setExpanded(true,1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText Emedicinename=myview.findViewById(R.id.medicinenameedit);
                final EditText Eserialnumber=myview.findViewById(R.id.serialnumberedit);
                final EditText Emalnumber=myview.findViewById(R.id.malnumberedit);
                final EditText Ehalalstatus=myview.findViewById(R.id.halalstatusedit);
                final EditText Ecounterfeitstatus=myview.findViewById(R.id.counterfeitstatusedit);
                final EditText Emanufacturer=myview.findViewById(R.id.manufactureredit);
                final EditText Eactiveingredient=myview.findViewById(R.id.activeingredientedit);
                final EditText Einactiveingredient=myview.findViewById(R.id.inactiveingredientedit);
                final EditText Eharamingredient=myview.findViewById(R.id.haramingredientedit);
                final EditText Emushboohingredient=myview.findViewById(R.id.mushboohingredientedit);
                final EditText Emedicinepicure=myview.findViewById(R.id.medicinepictureedit);
                Button Esubmit=myview.findViewById(R.id.submitedit2);

                Emedicinename.setText(model.getmedicinename());
                Eserialnumber.setText(model.getseialnumber());
                Emalnumber.setText(model.getmalnumber());
                Ehalalstatus.setText(model.gethalalstatus());
                Ecounterfeitstatus.setText(model.getcounterfeitstatus());
                Emanufacturer.setText(model.getmanufacturer());
                Eactiveingredient.setText(model.getactiveingredient());
                Einactiveingredient.setText(model.getinactiveingredient());
                Eharamingredient.setText(model.getharamingredient());
                Emushboohingredient.setText(model.getmushboohingredient());
                Emedicinepicure.setText(model.getmedicinepicture());

                dialogPlus.show();

                Esubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("medicinename",Emedicinename.getText().toString());
                        map.put("serialnumber",Eserialnumber.getText().toString());
                        map.put("malnumber",Emalnumber.getText().toString());
                        map.put("halalstatus",Ehalalstatus.getText().toString());
                        map.put("counterfeitstatus",Ecounterfeitstatus.getText().toString());
                        map.put("manufacturer",Emanufacturer.getText().toString());
                        map.put("activeingredient",Eactiveingredient.getText().toString());
                        map.put("inactiveingredient",Einactiveingredient.getText().toString());
                        map.put("haramingredient",Eharamingredient.getText().toString());
                        map.put("mushboohingredient",Emushboohingredient.getText().toString());
                        map.put("medicinepicture",Emedicinepicure.getText().toString());

                        getSnapshots().getSnapshot(position).getReference().update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                            }
                        });
                    }
                });
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.medicinename.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Deleting this user account?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getSnapshots().getSnapshot(position).getReference().delete();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                builder.show();
            }
        });
    }

    class MedicineViewHolder3 extends RecyclerView.ViewHolder{
        TextView medicinename;
        TextView medicineserialnumber;
        TextView medicinemalnumber;
        TextView medicinehalalstatus;
        TextView medicinecounterfeitstatus;
        TextView medicinemanufacturer;
        TextView medicineactiveingredient;
        TextView medicineinactiveingredient;
        TextView medicineharamngredient;
        TextView medicinemushboohingredient;
        ImageView medicinepicture;
        ImageView edit;
        ImageView delete;

        public MedicineViewHolder3(@NonNull View v) {
            super(v);
            medicinename = v.findViewById(R.id.medicinenameresult3);
            medicineserialnumber = v.findViewById(R.id.medicinebarcoderesult3);
            medicinemalnumber = v.findViewById(R.id.medicinemalnumberresult3);
            medicinehalalstatus = v.findViewById(R.id.medicinehalalresult3);
            medicinecounterfeitstatus = v.findViewById(R.id.medicinecounterfeitresult3);
            medicinemanufacturer = v.findViewById(R.id.medicinemanufacturerresult3);
            medicineactiveingredient = v.findViewById(R.id.medicineactiveingredientresult3);
            medicineinactiveingredient = v.findViewById(R.id.medicineinactiveingredientresult3);
            medicineharamngredient = v.findViewById(R.id.medicineharamingredientresult3);
            medicinemushboohingredient = v.findViewById(R.id.medicinemushbooheingredientresult3);
            medicinepicture = v.findViewById(R.id.medicinepictureresult3);
            edit = v.findViewById(R.id.editmedicinebtn3);
            delete = v.findViewById(R.id.deletemedicinebtn3);
        }
    }

    @Override
    public MedicineRecyclerAdapter3.MedicineViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout4, parent, false);
        return new MedicineRecyclerAdapter3.MedicineViewHolder3(v);
    }
}
