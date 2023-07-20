CREATE TABLE `tb_user_followers` (
  `followed_id` bigint NOT NULL,
  `follower_id` bigint NOT NULL,
  KEY `FKk0ecwwup49qkk8iw5bwmon0dd` (`follower_id`),
  KEY `FK10bhjpu6cgbrkeeq57dt9gfbx` (`followed_id`),
  CONSTRAINT `FK10bhjpu6cgbrkeeq57dt9gfbx` FOREIGN KEY (`followed_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKk0ecwwup49qkk8iw5bwmon0dd` FOREIGN KEY (`follower_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;