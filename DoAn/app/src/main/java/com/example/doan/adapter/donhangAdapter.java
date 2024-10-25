package com.example.doan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.Retrofit.RetrofitClient;
import com.example.doan.Utils.Utils;
import com.example.doan.activity.ManHinhChoActivity;
import com.example.doan.inteface.ApiDienThoai;
import com.example.doan.model.donhang;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class donhangAdapter extends RecyclerView.Adapter<donhangAdapter.MyViewHolder> {
    private ApiDienThoai apiDienThoai;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private Context context;
    private List<donhang> listdonhang;

    public donhangAdapter(Context context, List<donhang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        donhang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Mã Đơn: " + donHang.getId());
        holder.txttinhtrang.setText(trangthaiDon(donHang.getTrangthai()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerview_chitiet.getContext(),
                LinearLayoutManager.VERTICAL, false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());

        chitietdonhangAdapter chitietAdapter = new chitietdonhangAdapter(context, donHang.getItem());
        holder.recyclerview_chitiet.setLayoutManager(layoutManager);
        holder.recyclerview_chitiet.setAdapter(chitietAdapter);
        holder.recyclerview_chitiet.setRecycledViewPool(viewPool);

        if (donHang.getTrangthai() == 2 || donHang.getTrangthai() == 4 || donHang.getTrangthai() == 5) {
            holder.huydonhang.setVisibility(View.GONE);
            holder.nhanhang.setVisibility(View.GONE);
        } else if (donHang.getTrangthai() == 3) {
            holder.huydonhang.setVisibility(View.GONE);
            holder.nhanhang.setVisibility(View.VISIBLE);
        } else {
            holder.huydonhang.setVisibility(View.VISIBLE);
            holder.nhanhang.setVisibility(View.GONE);
        }
    }

    private String trangthaiDon(int status) {
        switch (status) {
            case 0:
                return "Đơn hàng đang được xử lí";
            case 1:
                return "Đơn hàng đã chấp nhận";
            case 2:
                return "Đơn hàng đã giao đến đơn vị vận chuyển";
            case 3:
                return "Đang trên đường giao đến bạn";
            case 4:
                return "Nhận hàng thành công";
            case 5:
                return "Đơn hàng đã hủy";
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtdonhang, txttinhtrang;
        RecyclerView recyclerview_chitiet;
        AppCompatButton huydonhang, nhanhang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            txttinhtrang = itemView.findViewById(R.id.txttinhtrang);
            recyclerview_chitiet = itemView.findViewById(R.id.recyclerview_chitiet);
            huydonhang = itemView.findViewById(R.id.huydonhang);
            nhanhang = itemView.findViewById(R.id.nhanhang);
            huydonhang.setOnClickListener(this);
            nhanhang.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                donhang donHang = listdonhang.get(position);
                if (view.getId() == R.id.huydonhang) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn chắc chắn muốn hủy đơn hàng");
                    builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                        donHang.setTrangthai(5);
                        capNhatDonHang(donHang.getId(), 5);
                    });
                    builder.setNegativeButton("Hủy bỏ", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.show();
                } else if (view.getId() == R.id.nhanhang) {
                    donHang.setTrangthai(4);
                    capNhatDonHang(donHang.getId(), 4);
                }
            }
        }

        private void capNhatDonHang(int idDonHang, int trangThai) {
            apiDienThoai = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiDienThoai.class);
            compositeDisposable.add(apiDienThoai.updateTrangThai(idDonHang, trangThai)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            donhangModel -> {
                                if (donhangModel.isSuccess()) {
                                    Toast.makeText(context, donhangModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            },
                            throwable -> {
                                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
        }
    }
}
