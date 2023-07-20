CREATE TABLE `tb_review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `instant` datetime(6) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `drama_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKvrwlhc4c4tm1hy5nfq5947vq` (`author_id`),
  KEY `FK9oka2vhyp9wgfu0goy8c2xot9` (`drama_id`),
  CONSTRAINT `FK9oka2vhyp9wgfu0goy8c2xot9` FOREIGN KEY (`drama_id`) REFERENCES `tb_drama` (`id`),
  CONSTRAINT `FKvrwlhc4c4tm1hy5nfq5947vq` FOREIGN KEY (`author_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;