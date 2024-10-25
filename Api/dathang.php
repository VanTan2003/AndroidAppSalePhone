<?php
include "connect.php";

$sodienthoai = $_POST['sodienthoai'];
$email = $_POST['email'];
$tongtien = $_POST['tongtien'];
$iduser = $_POST['iduser'];
$diachi = $_POST['diachi'];
$soluong = $_POST['soluong'];
$trangthai = $_POST['trangthai'];
$chitiet = $_POST['chitiet'];

$query = 'INSERT INTO `donhang`(`iduser`, `diachi`, `email`, `sodienthoai`, `soluong`, `tongtien`,`trangthai`) 
VALUES (' . $iduser . ',"' . $diachi . '","' . $email . '","' . $sodienthoai . '",' . $soluong . ',"' . $tongtien . '",' . $trangthai . ')';
$data = mysqli_query($conn, $query);

if ($data) {
    $query = 'SELECT id AS iddonhang FROM `donhang` WHERE `iduser` = ' . $iduser . ' ORDER BY id DESC LIMIT 1';
    $data = mysqli_query($conn, $query);
    $row = mysqli_fetch_assoc($data);
    $iddonhang = $row['iddonhang'];

    if ($iddonhang) {
        $chitiet = json_decode($chitiet, true);
        foreach ($chitiet as $value) {
            $truyvan = 'INSERT INTO `chitietdonhang`(`iddonhang`, `idsp`, `soluong`, `giasp`) 
            VALUES (' . $iddonhang . ',' . $value["idsp"] . ',' . $value["soluong"] . ',"' . $value["giasp"] . '")';
            $data = mysqli_query($conn, $truyvan);
            
            $truyvankho = 'SELECT `soluongkho` FROM `sanpham` WHERE `id` = '.$value["idsp"];
            $datakho = mysqli_query($conn, $truyvankho);
            $slnhap = mysqli_fetch_assoc($datakho);

            $truyvankho1 = 'UPDATE `sanpham` SET `soluongkho`='.$slnhap["soluongkho"]- $value["soluong"].' WHERE `id`='. $value["idsp"];
            $datakho1 = mysqli_query($conn, $truyvankho1);
        }

        $arr = [
            'success' => true,
            'message' => "Thành công",
            'iddonhang' => $iddonhang
        ];
        printf(json_encode($arr));
    } else {
        $arr = [
            'success' => false,
            'message' => "Không thành công"
        ];
        printf(json_encode($arr));
    }
} else {
    $arr = [
        'success' => false,
        'message' => "Không thành công"
    ];
    printf(json_encode($arr));
}
?>
