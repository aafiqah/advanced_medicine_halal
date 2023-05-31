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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class UserRecyclerAdapter extends FirestoreRecyclerAdapter<User, UserRecyclerAdapter.UserViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final UserRecyclerAdapter.OnItemClickListener listener;

    public Context context;
    private User user;


    UserRecyclerAdapter(FirestoreRecyclerOptions<User> options, UserRecyclerAdapter.OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    UserRecyclerAdapter(FirestoreRecyclerOptions<User> options) {
        super(options);
        this.listener = null;
    }

    @Override
    protected void onBindViewHolder(final UserRecyclerAdapter.UserViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final User model) {
        holder.fullname.setText(model.getFullname());
        holder.role.setText(model.getRole());
        holder.phone.setText(model.getPhone());
        holder.email.setText(model.getEmail());
        holder.password.setText(model.getPassword());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.fullname.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText Efullname=myview.findViewById(R.id.fullnameedit);
                final EditText Erole=myview.findViewById(R.id.roleedit);
                final EditText Ephone=myview.findViewById(R.id.phoneedit);
                final EditText Eemail=myview.findViewById(R.id.emailedit);
                final EditText Epassword=myview.findViewById(R.id.passwordedit);
                Button Esubmit=myview.findViewById(R.id.submitedit);

                Efullname.setText(model.getFullname());
                Erole.setText(model.getRole());
                Ephone.setText(model.getPhone());
                Eemail.setText(model.getEmail());
                Epassword.setText(model.getPassword());

                dialogPlus.show();

                Esubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("fullname",Efullname.getText().toString());
                        map.put("role",Erole.getText().toString());
                        map.put("phone",Ephone.getText().toString());
                        map.put("email",Eemail.getText().toString());
                        map.put("password",Epassword.getText().toString());

                        getSnapshots().getSnapshot(position).getReference().update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        holder.delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.fullname.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Deleting this user account?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getSnapshots().getSnapshot(position).getReference().delete();
                        //FirebaseUser user = FirebaseAuth.getInstance().
                        //user.delete();
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

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView fullname;
        TextView email;
        TextView password;
        TextView phone;
        TextView role;
        ImageView edit;
        ImageView delete;

        public UserViewHolder(@NonNull View v) {
            super(v);
            fullname = v.findViewById(R.id.userfullnameresult);
            role = v.findViewById(R.id.userroleresult);
            phone = v.findViewById(R.id.userphoneresult);
            email = v.findViewById(R.id.useremailrresult);
            password = v.findViewById(R.id.userpasswordresult);
            edit = v.findViewById(R.id.edituserbtn);
            delete = v.findViewById(R.id.deleteuserbtn);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public UserRecyclerAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout3, parent, false);
        return new UserRecyclerAdapter.UserViewHolder(v);
    }
}
