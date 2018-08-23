-- chainId column is added to post table in this script

alter table `post` add column `chain_id` VARCHAR(255) default "";
update `post` set `chain_id` = cast(`id` as char) where `chain_id` = "";