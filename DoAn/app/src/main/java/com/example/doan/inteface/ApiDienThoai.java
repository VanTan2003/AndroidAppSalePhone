package com.example.doan.inteface;

import com.example.doan.model.donhangModel;
import com.example.doan.model.loaisanphamModel;
import com.example.doan.model.sanphamModel;
import com.example.doan.model.slideModel;
import com.example.doan.model.taikhoanModel;
import com.example.doan.model.thongkeModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiDienThoai {
    @GET("getsanphammoi.php")
    Observable<sanphamModel> getSanPhamMoi();
    @GET("thongkesanphambanchay.php")
    Observable<thongkeModel> getSanPhamBanChay();
    @GET("thongkedoanhthuthang.php")
    Observable<thongkeModel> getDoanhThuThang();
    @GET("gettaikhoan.php")
    Observable<taikhoanModel> getTaiKhoan();
    @GET("getbannerquangcao.php")
    Observable<slideModel> getBanner();
    @GET("getsanphambanchay.php")
    Observable<sanphamModel> getSanPhamBanChay1();
    @GET("getloaisanpham.php")
    Observable<loaisanphamModel> getLoaiSanPham();
    @GET("xemdonhangadmin.php")
    Observable<donhangModel> getDonHang();
    @POST("getsanpham.php")
    @FormUrlEncoded
    Observable<sanphamModel> getSanPham(
            @Field("loai") int loai
    );
    @POST("xoadienthoai.php")
    @FormUrlEncoded
    Observable<sanphamModel> xoaSanPham(
            @Field("id") int id
    );
    @POST("xoataikhoan.php")
    @FormUrlEncoded
    Observable<taikhoanModel> xoaTaiKhoan(
            @Field("id") int id
    );
    @POST("updatetinhtrang.php")
    @FormUrlEncoded
    Observable<donhangModel> updateTrangThai(
            @Field("id") int id,
            @Field("trangthai") int trangthai);
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<donhangModel> xemDonHang(
            @Field("iduser") int id
    );
    @POST("dangky.php")
    @FormUrlEncoded
    Observable<taikhoanModel> dangKy(
            @Field("hoten") String hoten,
            @Field("email") String email,
            @Field("matkhau") String matkhau,
            @Field("sodienthoai") String sodienthoai,
            @Field("quyen") int quyen);
    @POST("dathang.php")
    @FormUrlEncoded
    Observable<donhangModel> datHang(
            @Field("diachi") String diachi,
            @Field("email") String email,
            @Field("sodienthoai") String sodienthoai,
            @Field("iduser") int id,
            @Field("soluong") int soluong,
            @Field("tongtien") String tongtien,
            @Field("trangthai") int trangthai,
            @Field("chitiet") String chitiet);
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<taikhoanModel> dangNhap(
            @Field("email") String email,
            @Field("matkhau") String matkhau);
    @POST("themdienthoai.php")
    @FormUrlEncoded
    Observable<sanphamModel> themDienThoai(
            @Field("tensanpham") String tensanpham,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai,
            @Field("soluongkho") int soluongkho,
            @Field("giasp") String giasp);
    @POST("suadienthoai.php")
    @FormUrlEncoded
    Observable<sanphamModel> suaDienThoai(
            @Field("tensanpham") String tensanpham,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai,
            @Field("soluongkho") int soluongkho,
            @Field("giasp") String giasp,
            @Field("id") int id);
    @POST("suataikhoan.php")
    @FormUrlEncoded
    Observable<taikhoanModel> suaTaiKhoan(
            @Field("hoten") String hoten,
            @Field("email") String email,
            @Field("matkhau") String matkhau,
            @Field("sodienthoai") String sodienthoai,
            @Field("quyen") int quyen,
            @Field("id") int id
            );
    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<sanphamModel> timKiem(
            @Field("search") String search);
    @POST("timkiemtheogia.php")
    @FormUrlEncoded
    Observable<sanphamModel> timKiemTheoGia(
            @Field("minPrice") String minPrice,
            @Field("maxPrice") String maxPrice);
    @Multipart
    @POST("upload.php")
    Call<sanphamModel> uploadFile(@Part MultipartBody.Part file);
}
