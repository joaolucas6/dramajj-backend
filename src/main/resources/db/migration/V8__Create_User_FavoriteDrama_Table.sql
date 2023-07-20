CREATE TABLE `tb_user_favorite_dramas` (
  `user_id` bigint NOT NULL,
  `favorite_dramas_id` bigint NOT NULL,
  UNIQUE KEY `UK_hkscx99502m9g9sxeov0dlwv0` (`favorite_dramas_id`),
  KEY `FKo99ykcfdgha8ghj3ml40wbslj` (`user_id`),
  CONSTRAINT `FK8k1q67n5baw0mrfmk36x1gt0h` FOREIGN KEY (`favorite_dramas_id`) REFERENCES `tb_drama` (`id`),
  CONSTRAINT `FKo99ykcfdgha8ghj3ml40wbslj` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;