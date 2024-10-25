<?php
include "connect.php";

$tensanpham = $_POST['tensanpham'];
$hinhanh    = $_POST['hinhanh'];
$mota = $_POST['mota'];
$giasp   = $_POST['giasp'];
$loai   = $_POST['loai'];
$soluongkho = $_POST['soluongkho'];

// Construct SQL query with proper formatting
$query = "INSERT INTO `sanpham` (`tensanpham`, `hinhanh`, `mota`, `giasp`, `loai`, `soluongkho`) VALUES ('$tensanpham', '$hinhanh', '$mota', '$giasp', '$loai', '$soluongkho')";

// Execute query
$data = mysqli_query($conn, $query);

// Check if the query was successful
if ($data) {
    $arr = [
        'success' => true,
        'message'  => "Thành công",
    ];
} else {
    $arr = [
        'success' => false,
        'message'  => "Không thành công",
    ];
}

// Output JSON response
print_r(json_encode($arr));
?>
