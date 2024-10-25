<?php  

include "connect.php";

$upload = "images/";  
// name
$query = "SELECT max(id) as id from sanpham";
$data = mysqli_query($conn, $query);
$result = array();

while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);
	// code...
}
if($result[0]['id'] == null){
    $name = 1;
    
}else{
    $name = ++ $result[0]['id'];
}
$name = $name.".jpg";
$uploadname = $upload .$name;  

// Check if image file is an actual image or fake image  
if (isset($_FILES["file"]))  
   {  
   if (move_uploaded_file($_FILES["file"]["tmp_name"], $uploadname)){  
       		$arr = [
				'success' => true,
				'message'  => "thanh cong",
                "name"  => $name
			];
      }  
   else  
      {  
        		$arr = [
				'success' => false,
				'message'  => " khong thanh cong",
			];
      }  
   }  
else  
   {  
     		$arr = [
				'success' => false,
				'message'  => "loi",
			];
   }  

   echo json_encode($arr);