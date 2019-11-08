package com.anhthi.tracuudiem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final ArrayList<ChiTietDiem> mMon;
    int i =1;
    public DataAdapter(ArrayList<ChiTietDiem> mMon) {
        this.mMon = mMon;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monhoc_g,parent,
                    false);
            return new MonHocHolder(view);
        }
        else if(viewType == 2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hocki_g,parent,
                    false);
            return new HocKiHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MonHocHolder)
            guiDuLieuMonHoc((MonHocHolder)holder,position);
        else if(holder instanceof HocKiHolder){
            LayHocKi((HocKiHolder)holder,position);
        }
    }
    @Override
    public int getItemCount() {
        return mMon.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMon.get(position).getHocki() != 0?2:1;
    }

    class MonHocHolder extends RecyclerView.ViewHolder{
        private final TextView txtMon,txtDiem;
        public MonHocHolder(@NonNull View itemView) {
            super(itemView);
            txtMon = itemView.findViewById(R.id.txtSubject);
            txtDiem = itemView.findViewById(R.id.txtMatch);
        }
    }
    class HocKiHolder extends RecyclerView.ViewHolder{
        private final TextView txtHK;

        public HocKiHolder(@NonNull View itemView) {
            super(itemView);
            txtHK = itemView.findViewById(R.id.txtHocKi);
        }
    }

    void guiDuLieuMonHoc(MonHocHolder holder, int position){
        holder.txtMon.setText(mMon.get(position).getTenMon());
        holder.txtDiem.setText(mMon.get(position).getDiem());
    }
    void LayHocKi(HocKiHolder holder, int position){
        holder.txtHK.setText("HỌC KÌ "+mMon.get(position).getHocki());
    }
}
