<?php
include "connect.php";
$query = "SELECT idsp,sanpham.tensanpham,sanpham.hinhanh,sanpham.giasp,sanpham.soluongkho,sanpham.mota ,COUNT(`soluong`) AS tong FROM `chitietdonhang` INNER JOIN sanpham ON sanpham.id=chitietdonhang.idsp GROUP BY `idsp` HAVING tong >=1;";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);

};

if (!empty($result)) {
	$arr = [
		'success' => true,
		'message'  => "thanh Cong",
		'result'   => $result	
	];
}else{
	$arr = [
		'success' => false,
		'message'  => " Khong Thanh Cong",
		'result'    => $result	
	];
}

print_r(json_encode($arr))
?>