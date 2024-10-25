package com.example.doan.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.bumptech.glide.Glide;
import com.example.doan.Eventbus.SuaXoaSanPhamEvent;
import com.example.doan.R;
import com.example.doan.Utils.Utils;
import com.example.doan.inteface.ItemClick;
import com.example.doan.model.sanpham;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class sanphamquanlyAdapter extends RecyclerView.Adapter<sanphamquanlyAdapter.MyViewHolder> {
    Context context;
    List<sanpham> sanphamList;

    public sanphamquanlyAdapter(Context context, List<sanpham> sanphamList) {
        this.context = context;
        this.sanphamList = sanphamList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphamquanly,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        sanpham sanpham=sanphamList.get(position);
        holder.txtidspquanly.setText("ID sản phẩm : "+Integer.toString(sanpham.getId()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiaspquanly.setText("" +decimalFormat.format(Double.parseDouble(sanpham.getGiasp()))+"VND");
        if (sanpham.getHinhanh().contains("http")){
            Glide.with(context).load(sanpham.getHinhanh()).into(holder.imgspquanly);
        }else {
            String hinh = Utils.BASE_URL+"images/"+sanpham.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imgspquanly);
        }
        holder.txttenspquanly.setText("Tên sản phẩm : "+sanpham.getTensanpham());
        holder.txtsolongspquanly.setText("Số lượng : "+Integer.toString(sanpham.getSoluongkho()));
        holder.setItemClick(new ItemClick() {
            @Override
            public void onClick(View view, int pos, boolean isLongclick) {
                if (!isLongclick){

                }
                else {
                    EventBus.getDefault().postSticky(new SuaXoaSanPhamEvent(sanpham));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sanphamList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener, View.OnLongClickListener {
        ImageView imgspquanly;
        TextView txttenspquanly,txtgiaspquanly,txtidspquanly,txtsolongspquanly;
        private ItemClick itemClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgspquanly=itemView.findViewById(R.id.image_sanpham);
            txttenspquanly=itemView.findViewById(R.id.txt_tensanphamad);
            txtgiaspquanly=itemView.findViewById(R.id.txt_giasanphamad);
            txtsolongspquanly=itemView.findViewById(R.id.txt_soluongsanphamad);
            txtidspquanly=itemView.findViewById(R.id.txt_idsanphamad);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClick(ItemClick itemClick) {
            this.itemClick = itemClick;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0, 0, getAdapterPosition(), "Sửa sản phẩm");
                contextMenu.add(0, 1, getAdapterPosition(), "Xóa sản phẩm");
        }
        @Override
        public void onClick(View view) {
            itemClick.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClick.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}
