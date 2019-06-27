ALTER TABLE `discussment_permission`
CHANGE `type` `level` varchar(255) COLLATE 'utf8mb4_general_ci' NOT NULL AFTER `user_id`;