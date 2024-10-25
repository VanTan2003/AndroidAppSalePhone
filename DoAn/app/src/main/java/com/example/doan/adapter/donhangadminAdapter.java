package com.example.doan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.Eventbus.DonHangEventBus;
import com.example.doan.R;
import com.example.doan.inteface.ItemClick;
import com.example.doan.model.donhang;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class donhangadminAdapter extends RecyclerView.Adapter<donhangadminAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<donhang> listdonhang;

    public donhangadminAdapter(Context context, List<donhang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhangadmin,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        donhang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Mã Đơn: "+donHang.getId());
        holder.txttinhtrang.setText(""+trangthaiDon(donHang.getTrangthai()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerview_chitiet.getContext(),
                LinearLayoutManager.VERTICAL,false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        // adapter chi tiét
        chitietdonhangAdapter chitietAdapter = new chitietdonhangAdapter(context,donHang.getItem());
        holder.recyclerview_chitiet.setLayoutManager(layoutManager);
        holder.recyclerview_chitiet.setAdapter(chitietAdapter);
        holder.recyclerview_chitiet.setRecycledViewPool(viewPool);
        // băt sự kiện click
        holder.setItemClick(new ItemClick() {
            @Override
            public void onClick(View view, int pos, boolean isLongclick) {
                EventBus.getDefault().postSticky(new DonHangEventBus(donHang));
            }
        });
    }
    private String trangthaiDon(int status){
        String result= "";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao đến đơn vị vấn chuyển";
                break;
            case 3:
                result = "Đang trên đường giao đến bạn";
                break;
            case 4:
                result = "Giao hàng thành công";
                break;
            case 5:
                result = "Đơn hàng đã hủy";
                break;
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtdonhang,txttinhtrang;
        RecyclerView recyclerview_chitiet;
        private ItemClick itemClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang=itemView.findViewById(R.id.iddonhang);
            txttinhtrang=itemView.findViewById(R.id.txttinhtrang);
            recyclerview_chitiet=itemView.findViewById(R.id.recyclerview_chitiet);
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
