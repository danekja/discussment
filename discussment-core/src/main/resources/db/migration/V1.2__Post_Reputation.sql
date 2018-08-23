SET FOREIGN_KEY_CHECKS=0;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

alter table `post` add column `likes` bigint(20) DEFAULT NULL;
alter table `post` add column `dislikes` bigint(20) DEFAULT NULL;

CREATE TABLE IF NOT EXISTS `user_post_reputation` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
    `liked` tinyint(1),
    `post_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY(`id`),
    KEY `FKrgfybuccjvwomik64wxebctiu` (`post_id`),
    CONSTRAINT `FKrgfybuccjvwomik64wxebctiu` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

SET FOREIGN_KEY_CHECKS=1;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;