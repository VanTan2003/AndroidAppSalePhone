<?php
include "connect.php";
$id = $_POST['id'];
$query = 'DELETE FROM `taikhoan` WHERE `id` ='.$id;

	$data = mysqli_query($conn, $query);

		if ($data == true) {
			$arr = [
				'success' => true,
				'message'  => " xoa tai khoan thanh cong",
			];
		}else{

			$arr = [
				'success' => false,
				'message'  => " xoa san pham khong thanh cong",
			];

		}
	
print_r(json_encode($arr));

?>