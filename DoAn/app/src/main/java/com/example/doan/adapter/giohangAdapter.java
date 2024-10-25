package com.example.doan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.Eventbus.TinhTongEvent;
import com.example.doan.R;
import com.example.doan.Utils.Utils;
import com.example.doan.inteface.ItemImageClick;
import com.example.doan.model.giohang;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class giohangAdapter extends RecyclerView.Adapter<giohangAdapter.MyViewHolder> {
    Context context;
    List<giohang> giohangList;

    public giohangAdapter(Context context, List<giohang> giohangList) {
        this.context = context;
        this.giohangList = giohangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        giohang gioHang = giohangList.get(position);
        holder.txttensp.setText(gioHang.getTensanpham());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Utils.mangmuahang.add(gioHang);
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }else {
                    for (int i = 0; i<Utils.mangmuahang.size();i++){
                        if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                            holder.checkBox.setChecked(b);
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }
        });
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgiasp1.setText("Đơn Giá: "+decimalFormat.format(gioHang.getGiasp())+"VND");
        holder.txtsoluong.setText(gioHang.getSoluong()+ "");
        if (gioHang.getHinhanh().contains("http")){
            Glide.with(context).load(gioHang.getHinhanh()).into(holder.imagehinhanhsp);
        }else {
            String hinh = Utils.BASE_URL+"images/"+gioHang.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imagehinhanhsp);
        }
        holder.txtgiasp2.setText(decimalFormat.format(gioHang.getSoluong() * gioHang.getGiasp())+"VND");
        holder.setImageClick(new ItemImageClick() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1){
                    if (giohangList.get(pos).getSoluong()>1){
                        int soluongmoi = giohangList.get(pos).getSoluong() -1;
                        giohangList.get(pos).setSoluong(soluongmoi);
                        holder.txtsoluong.setText(giohangList.get(pos).getSoluong() + " ");
                        holder.txtgiasp2.setText(decimalFormat.format(giohangList.get(pos).getSoluong()*giohangList.get(pos).getGiasp())+"VND");
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }else if (giohangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng");
                        builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                            Utils.mangmuahang.clear();
                            Utils.manggiohang.remove(pos);
                            notifyDataSetChanged();
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        });
                        builder.setNegativeButton("Hủy bỏ", (dialogInterface, i) -> dialogInterface.dismiss());
                        builder.show();
                    }
                }else if (giatri == 2){
                    if (giohangList.get(pos).getSoluong()<giohangList.get(pos).getSoluongkho()){
                        int soluongmoi = giohangList.get(pos).getSoluong() +1;
                        giohangList.get(pos).setSoluong(soluongmoi);
                        holder.txtsoluong.setText(giohangList.get(pos).getSoluong() + "");
                        holder.txtgiasp2.setText(decimalFormat.format(giohangList.get(pos).getSoluong()*giohangList.get(pos).getGiasp())+"VND");
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return giohangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtgiasp1,txtgiasp2,txttensp,txtsoluong;
        ImageView imagetru,imagecong,imagehinhanhsp;
        CheckBox checkBox;
        private ItemImageClick imageClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgiasp1=itemView.findViewById(R.id.txt_giasanphamgiohang1);
            txtgiasp2=itemView.findViewById(R.id.txt_giasanphamgiohang2);
            txttensp=itemView.findViewById(R.id.txt_tensanphamgiohang);
            txtsoluong=itemView.findViewById(R.id.txt_soluonggiohang);
            imagehinhanhsp=itemView.findViewById(R.id.image_giohang);
            imagetru=itemView.findViewById(R.id.image_tru);
            imagecong=itemView.findViewById(R.id.image_cong);
            checkBox=itemView.findViewById(R.id.checkboxgiohang);
            imagetru.setOnClickListener(this);
            imagecong.setOnClickListener(this);
        }

        public void setImageClick(ItemImageClick imageClick) {
            this.imageClick = imageClick;
        }

        @Override
        public void onClick(View view) {
            if (view == imagetru){
                imageClick.onImageClick(view,getAdapterPosition(),1);
            }else  if (view == imagecong){
                if (giohangList.get(getAdapterPosition()).getSoluong() + 1 > giohangList.get(getAdapterPosition()).getSoluongkho()) {
                    Toast.makeText(context, "Số lượng vượt quá số lượng trong kho", Toast.LENGTH_SHORT).show();
                } else {
                    imageClick.onImageClick(view, getAdapterPosition(), 2);
                }
            }
        }
    }
}
