package com.example.doan.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.Eventbus.SuaXoaSanPhamEvent;
import com.example.doan.Eventbus.SuaXoaTaiKhoanEvent;
import com.example.doan.R;
import com.example.doan.Utils.Utils;
import com.example.doan.inteface.ItemClick;
import com.example.doan.model.sanpham;
import com.example.doan.model.taikhoan;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class taikhoanAdapter extends RecyclerView.Adapter<taikhoanAdapter.MyViewHolder> {
    Context context;
    List<taikhoan> taikhoanList;

    public taikhoanAdapter(Context context, List<taikhoan> taikhoanList) {
        this.context = context;
        this.taikhoanList = taikhoanList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        taikhoan taikhoan=taikhoanList.get(position);
        holder.txtiduser.setText("ID tài khoản : "+Integer.toString(taikhoan.getId()));
        holder.txthoten.setText("Họ tên : "+taikhoan.getHoten());
        holder.txtemail.setText("Email : "+taikhoan.getEmail());
        holder.txtsodienthoai.setText("Số điện thoại : "+taikhoan.getSodienthoai());
        holder.setItemClick(new ItemClick() {
            @Override
            public void onClick(View view, int pos, boolean isLongclick) {
                if (!isLongclick){

                }
                else {
                    EventBus.getDefault().postSticky(new SuaXoaTaiKhoanEvent(taikhoan));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taikhoanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener, View.OnCreateContextMenuListener {
        TextView txtiduser,txthoten,txtemail,txtsodienthoai;
        private ItemClick itemClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtiduser=itemView.findViewById(R.id.txt_iduserad);
            txthoten=itemView.findViewById(R.id.txt_hotenad);
            txtemail=itemView.findViewById(R.id.txt_emailad);
            txtsodienthoai=itemView.findViewById(R.id.txt_sodienthoaiad);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void setItemClick(ItemClick itemClick) {
            this.itemClick = itemClick;
        }

        @Override
        public boolean onLongClick(View view) {
            itemClick.onClick(view,getAdapterPosition(),true);
            return false;
        }

        @Override
        public void onClick(View view) {
            itemClick.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, 0, getAdapterPosition(), "Sửa tài khoản");
            contextMenu.add(0, 1, getAdapterPosition(), "Xóa tài khoản");
        }
    }
}
