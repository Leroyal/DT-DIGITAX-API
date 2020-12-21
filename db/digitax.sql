-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 21, 2020 at 06:00 AM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.2.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `digitax`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `address_line1` varchar(255) DEFAULT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `country_code` bigint(20) DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `postal_code` bigint(20) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `state_code` bigint(20) DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`id`, `address_line1`, `address_line2`, `city`, `country`, `country_code`, `created_at`, `postal_code`, `state`, `state_code`, `updated_at`, `user_id`) VALUES
(7, 'kasabajayapur', 'khantapada', 'balasore', 'india', 91, 1597326665442, 788, 'Odisha', 756043, 1600885766381, 2);

-- --------------------------------------------------------

--
-- Table structure for table `device_metadata`
--

CREATE TABLE `device_metadata` (
  `id` int(11) NOT NULL,
  `client_ip_addr` varchar(255) DEFAULT NULL,
  `client_browser` varchar(255) DEFAULT NULL,
  `clientos` varchar(255) DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_logged_in` bit(1) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `unique_id` varchar(255) DEFAULT NULL,
  `unique_session_key` varchar(255) DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `device_metadata`
--

INSERT INTO `device_metadata` (`id`, `client_ip_addr`, `client_browser`, `clientos`, `created_at`, `email`, `is_logged_in`, `phone`, `unique_id`, `unique_session_key`, `updated_at`, `user_agent`, `user_id`, `user_name`) VALUES
(98, 'Chrome-86.0.4240.193', '0:0:0:0:0:0:0:1', 'Windows', 1605121756617, 'jayanta123456@yopmail.com', b'1', '801899685', 'FDERS123778IOO', 'JayantaPAFDERS123778IOOChrome-86.0.4240.193', NULL, 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36', 2, 'JayantaPA');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(100),
(100);

-- --------------------------------------------------------

--
-- Table structure for table `marketing_preference`
--

CREATE TABLE `marketing_preference` (
  `id` int(11) NOT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `is_contact_via_email_disabled` varchar(255) DEFAULT NULL,
  `is_contact_via_mail_disabled` varchar(255) DEFAULT NULL,
  `is_contact_via_phone_disabled` varchar(255) DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `question_categories`
--

CREATE TABLE `question_categories` (
  `id` int(11) NOT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `number_of_questions` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `category_label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `relations`
--

CREATE TABLE `relations` (
  `id` int(11) NOT NULL,
  `is_active` int(11) DEFAULT NULL,
  `is_deleted` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `relations`
--

INSERT INTO `relations` (`id`, `is_active`, `is_deleted`, `name`, `updated_at`, `created_at`) VALUES
(0, 1, '1', 'Sisiter', NULL, NULL),
(1, 1, '0', 'brother', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `restrict_tax`
--

CREATE TABLE `restrict_tax` (
  `id` bigint(20) NOT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `year` tinyblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `restrict_tax`
--

INSERT INTO `restrict_tax` (`id`, `created_at`, `end_date`, `start_date`, `updated_at`, `year`) VALUES
(94, 1603284635542, '2020-12-21 12:50:55', '2020-12-21 12:50:55', 1603284681393, 0x32303139);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_MODERATOR'),
(3, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `tax_tips`
--

CREATE TABLE `tax_tips` (
  `id` bigint(20) NOT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `number_of_questions` varchar(255) DEFAULT NULL,
  `tax_tip_label` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `is_visible` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `deleted_at` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_verified_email` int(11) DEFAULT NULL,
  `is_verified_phone` int(11) DEFAULT NULL,
  `is_active` int(11) DEFAULT NULL,
  `is_deleted` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `device_type` varchar(255) DEFAULT NULL,
  `reset_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created_at`, `deleted_at`, `email`, `is_verified_email`, `is_verified_phone`, `is_active`, `is_deleted`, `password`, `phone`, `updated_at`, `username`, `device_type`, `reset_token`) VALUES
(2, 1596733799716, NULL, 'jayanta123456@yopmail.com', 0, 0, 0, 0, '$2a$10$d1KmXSkhLrZyox7/6sJtfOzesQhAt1zoudA6a0rMX16mfzX7FzZ2K', '801899685', 1602235713239, 'JayantaPA', NULL, NULL),
(3, 1597075376467, NULL, 'jayanta.504455ddd@yopmail.com', 0, 0, 0, 0, '$2a$10$YQObMQ0AZVd64tp6MVui9u9amD3efQav5Yv.CCFClrjz0p4s0kygi', '', 1601412217367, 'JayantaPAm', NULL, NULL),
(4, 1597249560119, NULL, 'jayanta.504455d@gmail.com', 0, 0, 0, 0, '$2a$10$ZtLRTjLxeuNPJ/I6zssJrutz4ZIgl3sD2hVrWcAL4eYRaFeM5VSvK', '801872589', NULL, 'JayanPAm', NULL, NULL),
(5, 1598207120503, NULL, 'jayanta.5dd045dddd@gmail.com', 0, 0, 0, 0, '$2a$10$ea7qhpyn22g6aaHSoiqQp.WZKQSExltsruBws4brdEW9otS2b0fJC', '7008283503', NULL, 'JayantaPAn6', NULL, NULL),
(6, 1598207303016, NULL, 'jayanta.5045dddd@gmail.com', 0, 0, 0, 0, '$2a$10$9ZS9zU5EH3z29Gqfx5g1HOCPv4pniEubdtkcVkHmvCbhYp2zmEVd2', '7008283103', NULL, 'JayantaPn6', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_consent`
--

CREATE TABLE `user_consent` (
  `id` int(11) NOT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `date_for_spouse` datetime DEFAULT NULL,
  `date_for_user` datetime DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `spouse_first_name` varchar(255) DEFAULT NULL,
  `spouse_last_name` varchar(255) DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `consent_to_share_information` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_consent`
--

INSERT INTO `user_consent` (`id`, `created_at`, `date_for_spouse`, `date_for_user`, `first_name`, `last_name`, `spouse_first_name`, `spouse_last_name`, `updated_at`, `user_id`, `consent_to_share_information`) VALUES
(29, 1599068689935, '2021-01-01 07:00:00', '2021-01-01 07:00:00', 'Jayanta', 'Panigrahi', 'jaya', 'Panigrahi', 1599250274125, 2, 'true');

-- --------------------------------------------------------

--
-- Table structure for table `user_profile`
--

CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL,
  `consent_to_share_information` bit(1) DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `updated_at` bigint(20) DEFAULT NULL,
  `user_dateofbirth` datetime DEFAULT NULL,
  `user_first_name` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_last_name` varchar(255) DEFAULT NULL,
  `user_middle_initial` varchar(255) DEFAULT NULL,
  `user_ocupation` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_profile`
--

INSERT INTO `user_profile` (`id`, `consent_to_share_information`, `created_at`, `updated_at`, `user_dateofbirth`, `user_first_name`, `user_id`, `user_last_name`, `user_middle_initial`, `user_ocupation`) VALUES
(86, b'0', 1601965765098, 1601965765098, '2020-10-06 06:27:23', 'jayanta', 2, 'jaya', NULL, 'developer');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_tax_history`
--

CREATE TABLE `user_tax_history` (
  `id` int(11) NOT NULL,
  `consent_to_share_information` varchar(255) DEFAULT NULL,
  `created_at` bigint(20) DEFAULT NULL,
  `income` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`income`)),
  `personal_info` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`personal_info`)),
  `previous_year_summary` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`previous_year_summary`)),
  `tax_breaks` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`tax_breaks`)),
  `updated_at` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_tax_history`
--

INSERT INTO `user_tax_history` (`id`, `consent_to_share_information`, `created_at`, `income`, `personal_info`, `previous_year_summary`, `tax_breaks`, `updated_at`, `user_id`) VALUES
(40, 'true', 1599249705966, '{\"some_data\":{\"some\":\"45212\"}}', '{\"user\":{\"id\":\"1\"}}', '{\"user\":{\"id\":\"1\"}}', '{\"user\":{\"id\":\"1\"}}', 1599250108286, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK7rod8a71yep5vxasb0ms3osbg` (`user_id`);

--
-- Indexes for table `device_metadata`
--
ALTER TABLE `device_metadata`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbhomv0lpio6xmbqne0powgexm` (`unique_session_key`);

--
-- Indexes for table `marketing_preference`
--
ALTER TABLE `marketing_preference`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKpt30sv136n6dku2d6rxwqe68a` (`user_id`);

--
-- Indexes for table `question_categories`
--
ALTER TABLE `question_categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `relations`
--
ALTER TABLE `relations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restrict_tax`
--
ALTER TABLE `restrict_tax`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tax_tips`
--
ALTER TABLE `tax_tips`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKdu5v5sr43g5bfnji4vb8hg5s3` (`phone`);

--
-- Indexes for table `user_consent`
--
ALTER TABLE `user_consent`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKtmddmo1sr06harc47dgpfq3e` (`user_id`);

--
-- Indexes for table `user_profile`
--
ALTER TABLE `user_profile`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKebc21hy5j7scdvcjt0jy6xxrv` (`user_id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- Indexes for table `user_tax_history`
--
ALTER TABLE `user_tax_history`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK700txshcyls2eg0wy4h7aikr8` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_profile`
--
ALTER TABLE `user_profile`
  ADD CONSTRAINT `user_profile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
