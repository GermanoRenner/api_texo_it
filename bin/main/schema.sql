DROP TABLE IF EXISTS MOVIES;
CREATE TABLE movies (
	`id` INTEGER PRIMARY KEY AUTO_INCREMENT,
	`ano` INTEGER,
	`title` VARCHAR(255),
	`studios` VARCHAR(255),
	`producers` VARCHAR(255),
	`winner` Boolean
);
INSERT INTO movies (`ano`,`title`,`studios`,`producers`,`winner`)
select * from CSVREAD('classpath:dataset.csv', NULL, 'fieldSeparator=;');
