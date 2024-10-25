<?php
include "connect.php";

$query = 'SELECT * FROM `sanpham` WHERE `loai`';

$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);

};

if (!empty($result)) {

	$arr = [
		'success' => true,
		'message'  => "Thành Công",
		'result'    => $result	
	];

	
}else{
	$arr = [
		'success' => false,
		'message'  => " Không Thành Công",
		'result'    => $result	
	];

}
print_r(json_encode($arr));

?>