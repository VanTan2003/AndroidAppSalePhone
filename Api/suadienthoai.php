<?php
include "connect.php";
$tensanpham = $_POST['tensanpham'];
$giasp   = $_POST['giasp'];
$hinhanh = $_POST['hinhanh'];
$mota = $_POST['mota'];
$loai  = $_POST['loai'];
$soluongkho  = $_POST['soluongkho'];
$id   = $_POST['id'];

// // check data
$query = 'UPDATE `sanpham` SET `tensanpham`="'.$tensanpham.'",`giasp`="'.$giasp.'",`hinhanh`="'.$hinhanh.'",`mota`="'.$mota.'",`loai`='.$loai.',`soluongkho`='.$soluongkho.' WHERE id='.$id;

	$data = mysqli_query($conn, $query);

		if ($data == true) {
			$arr = [
				'success' => true,
				'message'  => "thanh cong",
			];

			
		}else{

			$arr = [
				'success' => false,
				'message'  => " khong thanh cong",
			];

		}

	
print_r(json_encode($arr));

?>