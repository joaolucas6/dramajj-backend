CREATE TABLE `tb_genre_dramas` (
  `genre_id` bigint NOT NULL,
  `drama_id` bigint NOT NULL,
  KEY `FKtdrf64evwfw3crlnqwq2vnkaj` (`drama_id`),
  KEY `FKeucgt6gwaqoj2g48ogayhrxef` (`genre_id`),
  CONSTRAINT `FKeucgt6gwaqoj2g48ogayhrxef` FOREIGN KEY (`genre_id`) REFERENCES `tb_genre` (`id`),
  CONSTRAINT `FKtdrf64evwfw3crlnqwq2vnkaj` FOREIGN KEY (`drama_id`) REFERENCES `tb_drama` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
