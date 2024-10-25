package com.example.doan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.R;
import com.example.doan.Utils.Utils;
import com.example.doan.activity.ChiTietSanPhamActivity;
import com.example.doan.inteface.ItemClick;
import com.example.doan.model.sanpham;

import java.text.DecimalFormat;
import java.util.List;

public class sanphamlienquanAdapter extends RecyclerView.Adapter<sanphamlienquanAdapter.MyViewHolder> {
    Context context;
    List<sanpham> array;

    public sanphamlienquanAdapter(Context context, List<sanpham> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphamlienquan,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        sanpham sanpham=array.get(position);
        holder.txttensanpham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (sanpham.getSoluongkho() == 0) {
            holder.imagehethang.setVisibility(View.VISIBLE);

        } else {
            holder.imagehethang.setVisibility(View.GONE);
        }
        holder.txtgiasanpham.setText("" +decimalFormat.format(Double.parseDouble(sanpham.getGiasp()))+"VND");
        if (sanpham.getHinhanh().contains("http")){
            Glide.with(context).load(sanpham.getHinhanh()).into(holder.imagesanpham);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imagesanpham);
        }
        holder.setItemClickListener((view, pos, isLongclick) -> {
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
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txttensanpham,txtgiasanpham;
        ImageView imagesanpham,imagehethang;
        private ItemClick itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txttensanpham=itemView.findViewById(R.id.txt_tensanpham);
            txtgiasanpham=itemView.findViewById(R.id.txt_giasanpham);
            imagesanpham=itemView.findViewById(R.id.image_sanpham);
            imagehethang=itemView.findViewById(R.id.txthethangroi);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                if (array.get(getAdapterPosition()).getSoluongkho() > 0) {
                    itemClickListener.onClick(view, getAdapterPosition(), false);
                } else {
                    Toast.makeText(view.getContext(), "Sản Phẩm Tạm Hết Hàng", Toast.LENGTH_SHORT).show();
                }
            }
        }

        public void setItemClickListener(ItemClick itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
