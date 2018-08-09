ALTER TABLE `discussment_post`
  DROP FOREIGN KEY `FKp9e33g3hasd05sed8aoo6tasq`;
ALTER TABLE `discussment_post`
  MODIFY COLUMN `user_id` varchar(255) not null;
