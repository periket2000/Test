-- This script would be more complex in a real production environment because
-- we need to include test if user/db exists and so on but is not the objective
-- of the test.

-- create DB
create database wallethub
  character set utf8;

-- create USER
create user 'dedup'@'localhost' identified by 'dedup';
grant SELECT, INSERT, UPDATE, DELETE on wallethub.* to 'dedup'@'localhost';
flush privileges;

-- create TABLE
use wallethub;
CREATE TABLE banned
(
  id BIGINT  NOT NULL AUTO_INCREMENT,
  run    VARCHAR(255),
  banned_ip  VARCHAR(255),
  requests INTEGER,
  comment VARCHAR(255),
  start_date DATETIME,
  end_date DATETIME,
  PRIMARY KEY(id)
);
