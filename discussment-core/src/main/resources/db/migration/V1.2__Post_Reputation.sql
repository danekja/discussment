SET FOREIGN_KEY_CHECKS=0;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE IF NOT EXISTS `post_reputation` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `likes` bigint(20) DEFAULT NULL,
    `dislikes` bigint(20) DEFAULT NULL,
    `post_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY(`id`),
    KEY `FKf9g5rstl34l5anue5wc39n6m6` (`post_id`),
    CONSTRAINT `FKf9g5rstl34l5anue5wc39n6m6` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TABLE IF NOT EXISTS `user_post_reputation` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
    `liked` tinyint(1),
    `postreputation_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY(`id`),
    KEY `FKggev1nsl9ywo11rt0o6onsubj` (`postreputation_id`),
    CONSTRAINT `FKggev1nsl9ywo11rt0o6onsubj` FOREIGN KEY (`postreputation_id`) REFERENCES `post_reputation` (`id`)
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