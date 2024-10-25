package com.example.doan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.Utils.Utils;
import com.example.doan.activity.ChiTietSanPhamActivity;
import com.example.doan.inteface.ItemClick;
import com.example.doan.model.sanpham;

import java.text.DecimalFormat;
import java.util.List;

public class timkiemAdapter extends RecyclerView.Adapter<timkiemAdapter.MyViewHolder> {
    Context context;
    List<sanpham> sanphamList;

    public timkiemAdapter(Context context, List<sanpham> sanphamList) {
        this.context = context;
        this.sanphamList = sanphamList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timkiem,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        sanpham sanpham=sanphamList.get(position);
        holder.txttentimkiem.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiatimkiem.setText("" +decimalFormat.format(Double.parseDouble(sanpham.getGiasp()))+"VND");
        if (sanpham.getHinhanh().contains("http")){
            Glide.with(context).load(sanpham.getHinhanh()).into(holder.imgtimkiem);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imgtimkiem);
        }
        holder.setItemClick((view, pos, isLongclick) -> {
            if (!isLongclick){
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("chitiet",sanpham);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanphamList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgtimkiem;
        TextView txttentimkiem,txtgiatimkiem;
        private ItemClick itemClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txttentimkiem=itemView.findViewById(R.id.timkiem_ten);
            txtgiatimkiem=itemView.findViewById(R.id.timkiemgia);
            imgtimkiem=itemView.findViewById(R.id.imageseach);
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
