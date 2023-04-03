CREATE SCHEMA IF NOT EXISTS `coredb`;
USE `coredb`;

CREATE TABLE IF NOT EXISTS `member`
(
    `id`             INT(11) NOT NULL AUTO_INCREMENT,
    `email`          VARCHAR(255) DEFAULT NULL,
    `member_type`    VARCHAR(255) DEFAULT NULL,
    `nickname`       VARCHAR(255) DEFAULT NULL,
    `profile_path`   VARCHAR(255) DEFAULT NULL,
    `uuid`           char(36)     DEFAULT NULL,
    `account`        VARCHAR(225) DEFAULT NULL,
    `nft_image_path` VARCHAR(225) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `mood`
(
    `id`    int(11) NOT NULL AUTO_INCREMENT,
    `mood1` varchar(255) DEFAULT NULL,
    `mood2` varchar(255) DEFAULT NULL,
    `mood3` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `song`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `cover_path`  varchar(255) DEFAULT NULL,
    `genre`       varchar(255) DEFAULT NULL,
    `combination` varchar(255) DEFAULT NULL,
    `song_path`   varchar(255) DEFAULT NULL,
    `song_uuid`   char(36)     DEFAULT NULL,
    `title`       varchar(255) DEFAULT NULL,
    `upload_date` bigint(20)   DEFAULT NULL,
    `views`       bigint(20)   DEFAULT NULL,
    `member_id`   int(11)      DEFAULT NULL,
    `moods_id`    int(11)      DEFAULT NULL,
    `song_status` varchar(255) DEFAULT NULL,
    `nft_image_path` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKln77pl0kiwceje9wi2qn9jj4c` (`member_id`),
    KEY `FKk07ok9wqkt23ybj2lhxoxhj5r` (`moods_id`),
    CONSTRAINT `FKk07ok9wqkt23ybj2lhxoxhj5r` FOREIGN KEY (`moods_id`) REFERENCES `mood` (`id`),
    CONSTRAINT `FKln77pl0kiwceje9wi2qn9jj4c` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);

CREATE TABLE IF NOT EXISTS `member_like_list`
(
    `member_id`    int(11) NOT NULL,
    `like_list_id` int(11) NOT NULL,
    KEY `FKp08dlcg1rifv6fkqucp2ivucn` (`like_list_id`),
    KEY `FKntqrxkeapskgf73c6jxvjrdv3` (`member_id`),
    CONSTRAINT `FKntqrxkeapskgf73c6jxvjrdv3` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FKp08dlcg1rifv6fkqucp2ivucn` FOREIGN KEY (`like_list_id`) REFERENCES `song` (`id`)
);

CREATE TABLE IF NOT EXISTS `play_list`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `added_date` bigint(20) DEFAULT NULL,
    `member_id`  int(11)    DEFAULT NULL,
    `song_id`    int(11)    DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK2gi6hat3h1e23eg6oa5pd6kts` (`member_id`),
    KEY `FK3g07bajw78i2boa7r5chrp3fm` (`song_id`),
    CONSTRAINT `FK2gi6hat3h1e23eg6oa5pd6kts` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FK3g07bajw78i2boa7r5chrp3fm` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`)
);
CREATE TABLE IF NOT EXISTS `follow`
(
    `id`           INT AUTO_INCREMENT PRIMARY KEY,
    `follower_id`  INT DEFAULT NULL,
    `following_id` INT DEFAULT NULL,
    CONSTRAINT FKkcoemc64xrm83cdmhyaphcuiu
        FOREIGN KEY (`following_id`) REFERENCES `member` (`id`),
    CONSTRAINT FKtps7gpodlrhxlji90u6r3mlng
        FOREIGN KEY (`follower_id`) REFERENCES `member` (`id`)
);

CREATE TABLE IF NOT EXISTS `market`
(
    `id`             INT AUTO_INCREMENT PRIMARY KEY,
    `price`          DOUBLE,
    `creator_name`   VARCHAR(255),
    `is_close`       BOOLEAN,
    `nft_id`         VARCHAR(255),
    `nft_image_path` VARCHAR(255),
    `song_title`     VARCHAR(255),
    `created_at`     datetime DEFAULT NULL,
    `updated_at`     datetime DEFAULT NULL,
    `seller_id`      INT,
    CONSTRAINT FKasojcnrr08a6k9aobcfhofh2y
        FOREIGN KEY (`seller_id`) REFERENCES member (`id`)
);

CREATE TABLE IF NOT EXISTS `like_market`
(
    `id`        INT AUTO_INCREMENT PRIMARY KEY,
    `market_id` INT NULL,
    `liker_id`  INT NULL,
    CONSTRAINT FK5ctkdcm3x2usk40a732iw8co4
        FOREIGN KEY (`liker_id`) REFERENCES member (`id`),
    CONSTRAINT FKtquifnctrh29uy2r495t9m3lq
        FOREIGN KEY (`market_id`) REFERENCES market (`id`)
);