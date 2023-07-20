CREATE TABLE `tb_user_plan_to_watch` (
  `user_id` bigint NOT NULL,
  `plan_to_watch_id` bigint NOT NULL,
  UNIQUE KEY `UK_bcjax730bnjpenueqrhh5pqx2` (`plan_to_watch_id`),
  KEY `FK37obunx5kur6be5i18xpxf1uh` (`user_id`),
  CONSTRAINT `FK37obunx5kur6be5i18xpxf1uh` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK42kn2h7g8j98wjobwhmlguilo` FOREIGN KEY (`plan_to_watch_id`) REFERENCES `tb_drama` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;