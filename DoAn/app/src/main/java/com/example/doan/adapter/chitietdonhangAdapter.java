package com.example.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.Utils.Utils;
import com.example.doan.model.item;

import java.text.DecimalFormat;
import java.util.List;

public class chitietdonhangAdapter extends RecyclerView.Adapter<chitietdonhangAdapter.MyViewHolder> {
    Context context;
    List<item> itemList;

    public chitietdonhangAdapter(Context context, List<item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitietdonhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        item item = itemList.get(position);
        holder.txtten.setText(""+item.getTensanpham()+"");
        holder.txtsoluong.setText("Số lượng:"+item.getSoluong()+"");
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgia.setText("Đơn giá: " +decimalFormat.format(Double.parseDouble(item.getGiasp()))+"VND");
        if (item.getHinhanh().contains("http")){
            Glide.with(context).load(item.getHinhanh()).into(holder.imganh);
        }else {
            String hinh = Utils.BASE_URL+"images/"+item.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imganh);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtten,txtgia,txtsoluong;
        ImageView imganh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imganh = itemView.findViewById(R.id.item_chitiet_imageview);
            txtten = itemView.findViewById(R.id.item_chitiet_tensanpham);
            txtsoluong = itemView.findViewById(R.id.item_chitiet_soluong);
            txtgia = itemView.findViewById(R.id.item_chitiet_dongia);
        }
    }
}
