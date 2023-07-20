CREATE TABLE `tb_actor_followers` (
  `actor_id` bigint NOT NULL,
  `follower_id` bigint NOT NULL,
  KEY `FKiy47gscs0tvpasa278cevgmn9` (`follower_id`),
  KEY `FKsxqp720utdhvd2wk57mpse1d2` (`actor_id`),
  CONSTRAINT `FKiy47gscs0tvpasa278cevgmn9` FOREIGN KEY (`follower_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKsxqp720utdhvd2wk57mpse1d2` FOREIGN KEY (`actor_id`) REFERENCES `tb_actor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;