package com.example.doan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.activity.ChiTietSanPhamActivity;
import com.example.doan.activity.SamSungActivity;
import com.example.doan.activity.iPhoneActivity;
import com.example.doan.inteface.ItemClick;
import com.example.doan.model.loaisanpham;

import java.text.DecimalFormat;
import java.util.List;

public class loaisanphamAdapter extends RecyclerView.Adapter<loaisanphamAdapter.MyViewHolder> {
    Context context;
    List<loaisanpham> array;

    public loaisanphamAdapter(Context context, List<loaisanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisanpham, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        loaisanpham loaisanpham = array.get(position);
        holder.txttenloai.setText(loaisanpham.getTenloai());
        Glide.with(context).load(loaisanpham.getHinhanh()).into(holder.imagehinhanhloai);
        holder.setItemClick(new ItemClick() {
            @Override
            public void onClick(View view, int pos, boolean isLongclick) {
                if (!isLongclick){
                    switch (pos) {
                        case 0:
                            Intent iphone = new Intent(context, iPhoneActivity.class);
                            iphone.putExtra("idloai", 1);
                            iphone.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(iphone);
                            break;
                        case 1:
                            Intent intent = new Intent(context, SamSungActivity.class);
                            intent.putExtra("idloai", 2);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txttenloai;
        ImageView imagehinhanhloai;
        private ItemClick itemClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenloai = itemView.findViewById(R.id.txt_tenloaisanpham);
            imagehinhanhloai = itemView.findViewById(R.id.image_loaisanpham);
            itemView.setOnClickListener(this);
        }

        public void setItemClick(ItemClick itemClick) {
            this.itemClick = itemClick;
        }

        @Override
        public void onClick(View view) {
            itemClick.onClick(view,getAdapterPosition(),false);
        }
    }
}