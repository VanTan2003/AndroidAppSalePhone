-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost:3306
-- Thời gian đã tạo: Th6 09, 2024 lúc 06:49 AM
-- Phiên bản máy phục vụ: 10.5.20-MariaDB
-- Phiên bản PHP: 7.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `id22182786_bandienthoai`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `iddonhang` int(11) NOT NULL,
  `idsp` int(11) NOT NULL,
  `soluong` int(11) NOT NULL,
  `giasp` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`iddonhang`, `idsp`, `soluong`, `giasp`) VALUES
(1, 27, 6, '30990000'),
(2, 27, 1, '30990000'),
(3, 26, 2, '21990000'),
(4, 25, 2, '24990000'),
(5, 27, 1, '30990000'),
(6, 1, 2, '32940000'),
(7, 26, 1, '21990000'),
(8, 27, 1, '30990000'),
(9, 30, 1, '15500000'),
(10, 30, 1, '15500000'),
(11, 30, 8, '15500000'),
(11, 30, 1, '15500000'),
(11, 30, 2, '15500000'),
(12, 27, 1, '30990000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `id` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `diachi` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `sodienthoai` varchar(250) NOT NULL,
  `soluong` int(11) NOT NULL,
  `tongtien` varchar(250) NOT NULL,
  `trangthai` int(11) NOT NULL,
  `ngaydat` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`id`, `iduser`, `diachi`, `email`, `sodienthoai`, `soluong`, `tongtien`, `trangthai`, `ngaydat`) VALUES
(1, 2, 'hung yen', 'levantan20032003@gmail.com', '0353216143', 2, '213232423', 4, '2024-05-19'),
(2, 2, 'hung yen', 'levantan20032003@gmail.com', '0353216143', 1, '30990000', 4, '2024-05-19'),
(3, 2, 'hung yen', 'levantan20032003@gmail.com', '0353216143', 2, '43980000', 4, '2024-05-31'),
(4, 2, 'Hung Yen', 'levantan20032003@gmail.com', '0353216143', 2, '49980000', 5, '2024-05-31'),
(5, 2, 'Hung Yen', 'levantan20032003@gmail.com', '0353216143', 1, '30990000', 4, '2024-05-31'),
(6, 2, 'Hung Yen', 'levantan20032003@gmail.com', '0353216143', 2, '65880000', 2, '2024-06-01'),
(7, 2, 'Hung Yen', 'levantan20032003@hotmail.com', '0353216143', 1, '21990000', 5, '2024-06-02'),
(8, 2, 'Hung Yen', 'levantan20032003@hotmail.com', '0353216143', 1, '30990000', 2, '2024-06-02'),
(9, 2, 'Hung Yen', 'levantan20032003@gmail.com', '0353216143', 1, '15500000', 2, '2024-06-05'),
(10, 2, 'Hung Yen', 'levantan20032003@gmail.com', '0353216143', 1, '15500000', 2, '2024-06-06'),
(11, 2, 'Hung Yen', 'levantan20032003@gmail.com', '0353216143', 11, '170500000', 5, '2024-06-06'),
(12, 2, 'Van Giang, Hung Yen', 'levantan20032003@gmail.com', '0353216143', 1, '30990000', 4, '2024-06-06');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `id` int(11) NOT NULL,
  `tensanpham` varchar(250) NOT NULL,
  `hinhanh` text NOT NULL,
  `mota` text NOT NULL,
  `giasp` varchar(250) NOT NULL,
  `loai` int(11) NOT NULL,
  `soluongkho` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`id`, `tensanpham`, `hinhanh`, `mota`, `giasp`, `loai`, `soluongkho`) VALUES
(1, 'Samsung Galaxy A24', 'https://cdn.tgdd.vn/Products/Images/42/305886/samsung-galaxy-a24-green-thumb-600x600.jpg', 'Màn hình: Super AMOLED6.5\"Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 50 MP & Phụ 5 MP, 2 MP\r\nCamera trước: 13 MP\r\nChip: MediaTek Helio G99\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIMHỗ trợ 4G\r\nPin, Sạc: 5000 mAh, 25 W\r\nHãng: Samsung\r\n', '5490000', 2, 8),
(2, 'Samsung Galaxy A34 5G', 'https://cdn.tgdd.vn/Products/Images/42/303583/samsung-galaxy-a34-thumb-xanh-600x600.jpg', 'Màn hình: Super AMOLED, 6.6\", Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 48 MP & Phụ 8 MP, 5 MP\r\nCamera trước: 13 MP\r\nChip: MediaTek Dimensity 1080 8 nhân\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIMHỗ trợ 5G\r\nPin, Sạc: 5000 mAh, 25 W\r\nHãng: Samsung', '6990000', 2, 10),
(3, 'Samsung Galaxy M34 5G', 'https://cdn.tgdd.vn/Products/Images/42/309834/samsung-galaxy-m34-xanh-ngoc-thumb-600x600.jpg', 'Màn hình: Super AMOLED, 6.5\", Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 50 MP & Phụ 8 MP, 2 MP\r\nCamera trước: 13 MP\r\nChip: Exynos 1280\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM (SIM 2 chung khe thẻ nhớ)Hỗ trợ 5G\r\nPin, Sạc: 6000 mAh, 25 W\r\nHãng: Samsung', '7490000', 2, 10),
(4, 'Samsung Galaxy A54 5G', 'https://cdn.tgdd.vn/Products/Images/42/303585/samsung-galaxy-a54-thumb-xanh-600x600.jpg', 'Màn hình: Super AMOLED, 6.4\", Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 50 MP & Phụ 12 MP, 5 MP\r\nCamera trước: 32 MP\r\nChip: Exynos 1380 8 nhân\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIMHỗ trợ 5G\r\nPin, Sạc: 5000 mAh,25 W\r\nHãng: Samsung', '8190000', 2, 10),
(5, 'Samsung Galaxy A55 5G', 'https://cdn.tgdd.vn/Products/Images/42/321771/samsung-galaxy-a55-5g-black-thumbnew-600x600.jpg', 'Màn hình: Super AMOLED, 6.6\", Full HD+\r\nHệ điều hành: Android 14\r\nCamera sau: Chính 50 MP & Phụ 8 MP, 5 MP\r\nCamera trước: 13 MP\r\nChip: Exynos 1380 8 nhân\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM Hỗ trợ 5G\r\nPin, Sạc: 5000 mAh, 25 W\r\nHãng: Samsung.', '9690000', 2, 10),
(6, 'Samsung Galaxy M54 5G', 'https://cdn.tgdd.vn/Products/Images/42/275953/samsung-galaxy-m54-bac-thumb-600x600.jpg', 'Màn hình: Super AMOLED Plus, 6.7\", Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 108 MP & Phụ 8 MP, 2 MP\r\nCamera trước: 32 MP\r\nChip: Exynos 1380 8 nhân\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIM (SIM 2 chung khe thẻ nhớ)Hỗ trợ 5G\r\nPin, Sạc: 6000 mAh25 W\r\nHãng: Samsung', '9990000', 2, 10),
(7, 'SamSung Galaxy A15', 'https://cdn.tgdd.vn/Products/Images/42/319584/thumb-5g-xanh-duong-600x600.jpg', 'Màn hình: Super AMOLED, 6.5\", Full HD+\r\nHệ điều hành: Android 14\r\nCamera sau: Chính 50 MP & Phụ 5 MP, 2 MP\r\nCamera trước: 13 MP\r\nChip: MediaTek Dimensity 6100+\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIM Hỗ trợ 5G\r\nPin, Sạc: 5000 mAh, 25 W\r\nHãng: Samsung', '5790000', 2, 10),
(8, 'SamSung Galaxy A14', 'https://cdn.tgdd.vn/Products/Images/42/303579/samsung-galaxy-a14-tlte-thumb-den-600x600.jpg', 'Màn hình: PLS LCD, 6.6\", Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 50 MP & Phụ 5 MP, 2 MP\r\nCamera trước: 13 MP\r\nChip: Exynos 850\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM Hỗ trợ 4G\r\nPin, Sạc: 5000 mAh, 15 W\r\nHãng: Samsung', '2990000', 2, 10),
(9, 'SamSung Galaxy A25 5G', 'https://cdn.tgdd.vn/Products/Images/42/319904/samsung-galaxy-a25-den-thumb-600x600.jpg', 'Màn hình: Super AMOLED6.5\"Full HD+\r\nHệ điều hành: Android 14\r\nCamera sau: Chính 50 MP & Phụ 8 MP, 2 MP\r\nCamera trước: 13 MP\r\nChip: Exynos 1280\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIMHỗ trợ 5G\r\nPin, Sạc: 5000 mAh, 25 W\r\nHãng: Samsung', '6490000', 2, 10),
(10, 'SamSung Galaxy S23 Fe 5G', 'https://cdn.tgdd.vn/Products/Images/42/321058/samsung-galaxy-s23-fe-5g-tim-thumb-600x600.jpeg', 'Màn hình: Dynamic AMOLED 2X, 6.4\", Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 50 MP & Phụ 12 MP, 8 MP\r\nCamera trước: 10 MP\r\nChip: Exynos 2200 8 nhân\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM hoặc 1 Nano SIM + 1 eSIMHỗ trợ 5G\r\nPin, Sạc: 4500 mAh25 W\r\nHãng: Samsung', '13990000', 2, 10),
(11, 'iPhone 11', 'https://cdn.tgdd.vn/Products/Images/42/153856/iphone-11-trang-600x600.jpg', 'Màn hình: IPS LCD, 6.1\", Liquid Retina\r\nHệ điều hành: iOS 15\r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A13 Bionic\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nSIM:1 Nano SIM & 1 eSIM Hỗ trợ 4G\r\nPin, Sạc: 3110mAh 18W\r\nHãng: iPhone (Apple)', '9990000', 1, 10),
(12, 'iPhone 12', 'https://cdn.tgdd.vn/Products/Images/42/213031/iphone-12-trang-13-600x600.jpg', 'Màn hình: OLED, 6.1\", Super Retina XDR\r\nHệ điều hành: iOS 15\r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A14 Bionic\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 2815mAh 20W\r\nHãng: iPhone (Apple)', '12290000', 1, 10),
(13, 'iPhone 13', 'https://cdn.tgdd.vn/Products/Images/42/223602/iphone-13-starlight-1-600x600.jpg', 'Màn hình: OLED, 6.1\", Super Retina XDR\r\nHệ điều hành: iOS 15\r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A15 Bionic\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 3240mAh 20W\r\nHãng: iPhone (Apple)', '15290000', 1, 10),
(14, 'iPhone 14', 'https://cdn.tgdd.vn/Products/Images/42/240259/iPhone-14-thumb-trang-600x600.jpg', 'Màn hình: OLED, 6.1\", Super Retina XDR\r\nHệ điều hành: iOS 16\r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A15 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 3279mAh 20W\r\nHãng: iPhone (Apple)', '17790000', 1, 10),
(15, 'iPhone 14 Plus', 'https://cdn.tgdd.vn/Products/Images/42/245545/iPhone-14-plus-thumb-xanh-1-600x600.jpg', 'Màn hình: OLED, 6.7\", Super Retina XDR\r\nHệ điều hành: iOS 16\r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A15 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 4325mAh 20W\r\nHãng: iPhone (Apple)', '20090000', 1, 10),
(16, 'iPhone 14 Pro', 'https://cdn.tgdd.vn/Products/Images/42/289691/iphone-14-pro-tim-thumb-600x600.jpg', 'Màn hình: OLED, 6.1\", Super Retina XDR\r\nHệ điều hành: iOS 16\r\nCamera sau: Chính 48 MP & Phụ 12 MP, 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A16 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 3200mAh 20W\r\nHãng: iPhone (Apple)', '24900000', 1, 10),
(17, 'SamSung Galaxy Z Flip5 5', 'https://cdn.tgdd.vn/Products/Images/42/299250/samsung-galaxy-z-flip5-mint-thumbnew-600x600.jpg', 'Màn hình: Chính: Dynamic AMOLED 2X, Phụ: Super AMOLED, Chính 6.7\" & Phụ 3.4\", Full HD+\r\nHệ điều hành: Android 13\r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 10 MP\r\nChip: Snapdragon 8 Gen 2 for Galaxy\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 1 Nano SIM & 1 eSIMHỗ trợ 5G\r\nPin, Sạc: 3700 mAh25 W\r\nHãng: Samsung', '21990000', 2, 10),
(18, 'iPhone 14 Pro Max', 'https://cdn.tgdd.vn/Products/Images/42/251192/iphone-14-pro-max-tim-thumb-600x600.jpg', 'Màn hình: OLED, 6.7\", Super Retina XDR\r\nHệ điều hành: iOS 16\r\nCamera sau: Chính 48 MP & Phụ 12 MP, 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A16 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 4323mAh 20W\r\nHãng: Phone (Apple)', '27390000', 1, 10),
(19, 'SamSung Galaxy Z Flod5 5G', 'https://cdn.tgdd.vn/Products/Images/42/301608/samsung-galaxy-z-fold5-kem-thumbnew-600x600.jpg', 'Màn hình: Dynamic AMOLED 2X, Chính 7.6\" & Phụ 6.2\",Quad HD+ (2K+)\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 50 MP & Phụ 12 MP, 10 MP\r\nCamera trước: 10 MP & 4 MP\r\nChip: Snapdragon 8 Gen 2 for Galaxy\r\nRAM: 12 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIM hoặc 1 Nano SIM + 1 eSIMHỗ trợ 5G\r\nPin, Sạc: 4400 mAh25 W\r\nHãng: Samsung', '40900000', 2, 10),
(20, 'iPhone 15', 'https://cdn.tgdd.vn/Products/Images/42/281570/iphone-15-xanh-la-thumb-600x600.jpg', 'Màn hình: OLED, 6.1\", Super Retina XDR\r\nHệ điều hành: iOS 17\r\nCamera sau: Chính 48 MP & Phụ 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A16 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 3349mAh 20W\r\nHãng: iPhone (Apple)', '20690000', 1, 10),
(21, 'SamSung Galaxy S23 Ultra 5G', 'https://cdn.tgdd.vn/Products/Images/42/249948/samsung-galaxy-s23-ultra-green-thumbnew-600x600.jpg', 'Màn hình: Dynamic AMOLED 2X, 6.8\", Quad HD+ (2K+)\r\nHệ điều hành: Android 13\r\nCamera sau: Chính 200 MP & Phụ 12 MP, 10 MP, 10 MP\r\nCamera trước: 12 MP\r\nChip: Snapdragon 8 Gen 2 for Galaxy\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIM hoặc 1 Nano SIM + 1 eSIMHỗ trợ 5G\r\nPin, Sạc: 5000 mAh,45 W\r\nHãng: Samsung', '24990000', 2, 10),
(22, 'iPhone 15 Plus', 'https://cdn.tgdd.vn/Products/Images/42/303891/iphone-15-plus-xanh-la-128gb-thumb-600x600.jpg', 'Màn hình: OLED, 6.7\", Super Retina XDR\r\nHệ điều hành: iOS 17\r\nCamera sau: Chính 48 MP & Phụ 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A16 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 4383mAh 20W\r\nHãng: iPhone (Apple)', '23690000', 1, 10),
(23, 'SamSung Galaxy S24 Ultra 5G', 'https://cdn.tgdd.vn/Products/Images/42/307174/samsung-galaxy-s24-ultra-grey-thumb-600x600.jpg', 'Màn hình: Dynamic AMOLED 2X, 6.8\", Quad HD+ (2K+)\r\nHệ điều hành: Android 14\r\nCamera sau: Chính 200 MP & Phụ 50 MP, 12 MP, 10 MP\r\nCamera trước: 12 MP\r\nChip: Snapdragon 8 Gen 3 for Galaxy\r\nRAM: 12 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIM hoặc 2 eSIM hoặc 1 Nano SIM + 1 eSIMHỗ trợ 5G\r\nPin, Sạc: 5000 mAh, 45 W\r\nHãng: Samsung', '31990000', 2, 10),
(24, 'iPhone 15 Pro', 'https://cdn.tgdd.vn/Products/Images/42/299033/iphone-15-pro-blue-thumbnew-600x600.jpg', 'Màn hình: OLED, 6.1\", Super Retina XDR\r\nHệ điều hành: iOS 17\r\nCamera sau: Chính 48 MP & Phụ 12 MP, 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A17 Pro 6 nhân\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 3274mAh 20W\r\nHãng: iPhone (Apple)', '27490000', 1, 10),
(25, 'SamSung Galaxy S24+ 5G', 'https://cdn.tgdd.vn/Products/Images/42/307172/samsung-galaxy-s24-plus-violet-thumb-600x600.jpg', 'Màn hình: Dynamic AMOLED 2X, 6.7\", Quad HD+ (2K+)\r\nHệ điều hành: Android 14\r\nCamera sau: Chính 50 MP & Phụ 12 MP, 10 MP\r\nCamera trước: 12 MP\r\nChip: Exynos 2400\r\nRAM: 12 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIM hoặc 2 eSIM hoặc 1 Nano SIM + 1 eSIMHỗ trợ 5G\r\nPin, Sạc: 4900 mAh,45 W\r\nHãng: Samsung', '24990000', 2, 8),
(26, 'SamSung Galaxy S24 5G', 'https://cdn.tgdd.vn/Products/Images/42/319665/samsung-galaxy-s24-black-thumb-1-600x600.jpg', 'Màn hình: Dynamic AMOLED 2X, 6.2\",Full HD+\r\nHệ điều hành: Android 14\r\nCamera sau: Chính 50 MP & Phụ 12 MP, 10 MP\r\nCamera trước: 12 MP\r\nChip: Exynos 2400\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 2 Nano SIM hoặc 2 eSIM hoặc 1 Nano SIM + 1 eSIMHỗ trợ 5G\r\nPin, Sạc: 4000 mAh25 W\r\nHãng: Samsung\r\n', '21990000', 2, 7),
(27, 'iPhone 15 Pro Max', 'https://cdn.tgdd.vn/Products/Images/42/305658/iphone-15-pro-max-blue-thumbnew-600x600.jpg', 'Màn hình: OLED, 6.7\", Super Retina XDR\r\nHệ điều hành: iOS 17\r\nCamera sau: Chính 48 MP & Phụ 12 MP, 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A17 Pro 6 nhân\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 256 GB\r\nSIM: 1 Nano SIM & 1 eSIM Hỗ trợ 5G\r\nPin, Sạc: 4422mAh 20W\r\nHãng: iPhone (Apple)', '30990000', 1, 0),
(30, 'iPhone 11 Pro Max', '28.jpg', 'Good', '15500000', 1, -3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `id` int(11) NOT NULL,
  `hoten` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `matkhau` varchar(250) NOT NULL,
  `sodienthoai` varchar(250) NOT NULL,
  `quyen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`id`, `hoten`, `email`, `matkhau`, `sodienthoai`, `quyen`) VALUES
(1, 'admin', 'admin', 'admin', 'admin', 1),
(2, 'Tan', 'levantan20032003@gmail.com', 'levantan2003', '0353216143', 2),
(3, 'Le Van Tan', 'admin2003@gmail.com', 'tanle2003', '0353216143', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
