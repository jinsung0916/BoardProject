CREATE TABLE `board` (
	`NO` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`TITLE` VARCHAR(100) NOT NULL COLLATE 'utf8_bin',
	`CONTENTS` VARCHAR(500) NOT NULL COLLATE 'utf8_bin',
	`REGDATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`NO`),
	INDEX `board_idx1` (`REGDATE`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
;

CREATE TABLE `file` (
	`uuid` VARCHAR(100) NOT NULL COLLATE 'utf8_bin',
	`fileName` VARCHAR(200) NOT NULL COLLATE 'utf8_bin',
	`filePath` VARCHAR(100) NOT NULL COLLATE 'utf8_bin',
	`boardNo` INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY (`uuid`),
	INDEX `file_fk1` (`boardNo`),
	CONSTRAINT `file_fk1` FOREIGN KEY (`boardNo`) REFERENCES `board` (`NO`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_bin'
ENGINE=InnoDB
;

CREATE TABLE `tbl_member` (
	`userid` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
	`userpw` VARCHAR(100) NOT NULL COLLATE 'utf8_bin',
	`username` VARCHAR(100) NOT NULL COLLATE 'utf8_bin',
	`regdate` DATE NULL DEFAULT NULL,
	`updatedate` DATE NULL DEFAULT NULL,
	`enabled` CHAR(50) NOT NULL DEFAULT '1' COLLATE 'utf8_bin',
	PRIMARY KEY (`userid`)
)
COLLATE='utf8_bin'
ENGINE=InnoDB
;

CREATE TABLE `tbl_member_auth` (
	`userid` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
	`auth` VARCHAR(50) NOT NULL COLLATE 'utf8_bin',
	INDEX `fk_member_auth` (`userid`),
	CONSTRAINT `fk_member_auth` FOREIGN KEY (`userid`) REFERENCES `tbl_member` (`userid`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_bin'
ENGINE=InnoDB
;
