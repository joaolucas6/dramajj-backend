CREATE TABLE `drama_rates` (
  `drama_id` bigint NOT NULL,
  `rates` double DEFAULT NULL,
  KEY `FKdx01miqxwp7duod9tdy28bdoe` (`drama_id`),
  CONSTRAINT `FKdx01miqxwp7duod9tdy28bdoe` FOREIGN KEY (`drama_id`) REFERENCES `tb_drama` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;