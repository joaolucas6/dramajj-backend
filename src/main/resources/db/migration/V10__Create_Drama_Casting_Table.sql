CREATE TABLE `tb_drama_casting` (
  `drama_id` bigint NOT NULL,
  `actor_id` bigint NOT NULL,
  KEY `FK7tul5f3i0r5sc7hhh5cfjc8k0` (`actor_id`),
  KEY `FK92pjguyyyvkuuly9pss1f64fl` (`drama_id`),
  CONSTRAINT `FK7tul5f3i0r5sc7hhh5cfjc8k0` FOREIGN KEY (`actor_id`) REFERENCES `tb_actor` (`id`),
  CONSTRAINT `FK92pjguyyyvkuuly9pss1f64fl` FOREIGN KEY (`drama_id`) REFERENCES `tb_drama` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
