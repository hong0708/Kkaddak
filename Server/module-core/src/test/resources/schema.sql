CREATE SCHEMA IF NOT EXISTS `coredb`;
USE `coredb`;

CREATE TABLE IF NOT EXISTS `Music` (
    `id` int NOT NULL AUTO_INCREMENT
);

CREATE TABLE IF NOT EXISTS `Member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `member_type` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `profile_path` varchar(255) DEFAULT NULL,
  `uuid` char(36) DEFAULT NULL,
  PRIMARY KEY (id)
);
