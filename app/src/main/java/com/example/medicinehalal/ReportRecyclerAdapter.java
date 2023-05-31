package com.example.medicinehalal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.core.Repo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReportRecyclerAdapter extends RecyclerView.Adapter<ReportRecyclerAdapter.ViewHolder> {

    private Context mcontext;
    private List<Report> mreports;

    public ReportRecyclerAdapter(Context context, List<Report> reports)
    {
        mcontext = context;
        mreports = reports;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(mcontext).inflate(R.layout.list_layout5,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        Report reportcurrent = mreports.get(position);
        holder.reportemail.setText(reportcurrent.getreportemail());
        holder.reportfullname.setText(reportcurrent.getreportfullname());
        holder.reportphone.setText(reportcurrent.getreportphone());
        holder.reportmedicinename.setText(reportcurrent.getreportmedicinename());
        holder.reportmedicineissue.setText(reportcurrent.getreportmedicineissue());
        holder.reportmedicinelocation.setText(reportcurrent.getreportmedicinelocation());
        holder.reportmedicineadditional.setText(reportcurrent.getReportadditional());
    }

    @Override
    public int getItemCount() {
        return mreports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img1;
        public  TextView reportemail;
        public TextView reportfullname;
        public TextView reportphone;
        public TextView reportmedicinename;
        public TextView reportmedicineissue;
        public TextView reportmedicinelocation;
        public TextView reportmedicineadditional;

        public ViewHolder(View itemView){
            super(itemView);
            img1=itemView.findViewById(R.id.medicinepictureresult3);
            reportemail=itemView.findViewById(R.id.reportmailresult);
            reportfullname=itemView.findViewById(R.id.reportfullnameresult);
            reportphone=itemView.findViewById(R.id.reportphoneresult);
            reportmedicinename=itemView.findViewById(R.id.reportmedicinenameresult);
            reportmedicineissue=itemView.findViewById(R.id.reportmedicineissueresult);
            reportmedicinelocation=itemView.findViewById(R.id.reportmedicinelocationresult);
            reportmedicineadditional=itemView.findViewById(R.id.reportmedicineaddionalresult);
        }
    }
}
