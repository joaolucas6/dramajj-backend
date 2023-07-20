CREATE TABLE `tb_drama` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `episode_number` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `poster_img_url` varchar(255) DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `synopsis` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
