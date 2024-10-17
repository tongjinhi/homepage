-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- 생성 시간: 24-10-16 03:01
-- 서버 버전: 10.4.32-MariaDB
-- PHP 버전: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `test`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `homepage_tb`
--

CREATE TABLE `homepage_tb` (
  `board_id` int(11) NOT NULL,
  `user_id` char(20) NOT NULL,
  `name` char(20) NOT NULL,
  `title` char(50) NOT NULL,
  `content` char(255) DEFAULT NULL,
  `w_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 테이블의 덤프 데이터 `homepage_tb`
--

INSERT INTO `homepage_tb` (`board_id`, `user_id`, `name`, `title`, `content`, `w_date`) VALUES
(1, '23tj31101', '홍길동', '김포 맛집', '김포 맛집 검색후 공유', '2023-05-22 12:30:45'),
(2, '23tj31102', '김유신', '김포 버스', '버스 정류장 검색', '2023-05-22 13:31:35'),
(3, '23tj31103', '장영실', '김포 날씨', '오늘의 날씨 뉴스', '2023-05-22 14:32:47'),
(4, '23tj31104', '이순신', '김포 관광', '김포 관광 명소', '2023-05-22 15:33:05'),
(5, '23tj31105', '한석봉', '김포 공항', '공항 가는길 정리', '2023-05-22 16:34:25'),
(6, '23tj31106', '김마송', '마송 마트', '김포 마송 마트', '2024-10-15 16:24:21');

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `homepage_tb`
--
ALTER TABLE `homepage_tb`
  ADD PRIMARY KEY (`board_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
