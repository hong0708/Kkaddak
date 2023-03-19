CREATE SCHEMA IF NOT EXISTS `coredb`;
USE `coredb`;

CREATE TABLE IF NOT EXISTS `member` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `email` varchar(255) DEFAULT NULL,
    `member_type` varchar(255) DEFAULT NULL,
    `nickname` varchar(255) DEFAULT NULL,
    `profile_path` varchar(255) DEFAULT NULL,
    `uuid` char(36) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `song` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `cover_path` varchar(255) DEFAULT NULL,
    `genre` varchar(255) DEFAULT NULL,
    `mood` varchar(255) DEFAULT NULL,
    `song_path` varchar(255) DEFAULT NULL,
    `song_uuid` char(36) DEFAULT NULL,
    `title` varchar(255) DEFAULT NULL,
    `upload_date` bigint(20) DEFAULT NULL,
    `member_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKln77pl0kiwceje9wi2qn9jj4c` (`member_id`),
    CONSTRAINT `FKln77pl0kiwceje9wi2qn9jj4c` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);

CREATE TABLE IF NOT EXISTS `member_like_list` (
    `member_id` int(11) NOT NULL,
    `like_list_id` int(11) NOT NULL,
    KEY `FKp08dlcg1rifv6fkqucp2ivucn` (`like_list_id`),
    KEY `FKntqrxkeapskgf73c6jxvjrdv3` (`member_id`),
    CONSTRAINT `FKntqrxkeapskgf73c6jxvjrdv3` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FKp08dlcg1rifv6fkqucp2ivucn` FOREIGN KEY (`like_list_id`) REFERENCES `song` (`id`)
);

CREATE TABLE IF NOT EXISTS `play_list` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `added_date` bigint(20) DEFAULT NULL,
    `member_id` int(11) DEFAULT NULL,
    `song_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK2gi6hat3h1e23eg6oa5pd6kts` (`member_id`),
    KEY `FK3g07bajw78i2boa7r5chrp3fm` (`song_id`),
    CONSTRAINT `FK2gi6hat3h1e23eg6oa5pd6kts` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FK3g07bajw78i2boa7r5chrp3fm` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`)
);

CREATE TABLE IF NOT EXISTS `auction` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `bid_start_price` DOUBLE,
    `creator_name` VARCHAR(255),
    `expired_date` DATE,
    `highest_bid_price` DOUBLE,
    `is_close` BOOLEAN,
    `nft_id` VARCHAR(255),
    `nft_image_path` VARCHAR(255),
    `song_title` VARCHAR(255),
    `seller_id` INT,
    CONSTRAINT FKasojcnrr08a6k9aobcfhofh2y
     FOREIGN KEY (`seller_id`) REFERENCES member (`id`)
);