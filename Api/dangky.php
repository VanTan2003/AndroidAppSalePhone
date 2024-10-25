<?php
include "connect.php";
$hoten = $_POST['hoten'];
$email    = $_POST['email'];
$matkhau = $_POST['matkhau'];
$sodienthoai   = $_POST['sodienthoai'];
$quyen=$_POST["quyen"];
$query = 'SELECT * FROM `taikhoan` WHERE `email`= "' . $email . '"';
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);

if ($numrow > 0) {
	$arr = [
		'success' => false,
		'message'  => " Email đã tồn tại",
	];
} else {
	$query = 'INSERT INTO `taikhoan`(`hoten`, `email`, `matkhau`, `sodienthoai`,`quyen`) 
	VALUES ("' . $hoten . '","' . $email . '","' . $matkhau . '","' . $sodienthoai . '","' . $quyen . '")';
	$data = mysqli_query($conn, $query);

	if ($data == true) {
		$arr = [
			'success' => true,
			'message'  => "Thành Công",
		];
	} else {

		$arr = [
			'success' => false,
			'message'  => " Không Thành Công",
		];
	}
}


print_r(json_encode($arr));