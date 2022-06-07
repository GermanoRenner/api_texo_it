DROP TABLE IF EXISTS MOVIES;
CREATE TABLE movies (
	`ano` INTEGER,
	`title` VARCHAR(255),
	`studios` VARCHAR(255),
	`producers` VARCHAR(255),
	`winner` Boolean
) as select * from CSVREAD('classpath:dataset.csv', NULL, 'fieldSeparator=;');
