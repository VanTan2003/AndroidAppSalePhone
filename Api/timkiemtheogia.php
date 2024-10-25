<?php
include "connect.php";
$minPrice = $_POST['minPrice'];
$maxPrice = $_POST['maxPrice'];

if (empty($minPrice) || empty($maxPrice)) {
    $arr = [
        'success' => false,
        'message'  => "Không Thành Công: Không có khoảng giá được cung cấp",
    ];
} else {
    // Bắt đầu câu truy vấn
    $query = "SELECT * FROM `sanpham` WHERE `giasp` BETWEEN " . $minPrice . " AND " . $maxPrice;

    $data = mysqli_query($conn, $query);
    
    $result = array();

    while ($row = mysqli_fetch_assoc($data)) {
        $result[] = $row; 
    }

    if (!empty($result)) {
        $arr = [
            'success' => true,
            'message'  => "Thành Công",
            'result'   => $result	
        ];
    } else {
        $arr = [
            'success' => false,
            'message'  => "Không Thành Công: Không tìm thấy sản phẩm phù hợp",
            'result'   => $result	
        ];
    }
}

// Xuất kết quả dưới dạng JSON
print_r(json_encode($arr));
?>
