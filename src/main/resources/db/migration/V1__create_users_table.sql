DROP TABLE IF EXISTS users;
CREATE TABLE `users` (
  `id`         BIGINT(20)  NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`       VARCHAR(20) NOT NULL,
  `sex`        VARCHAR(8),
  `age`        INTEGER NOT NULL,
  `email`      VARCHAR(250)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;