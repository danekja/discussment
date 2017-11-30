-- a script to create database schema for discussion app

-- create database
CREATE DATABASE IF NOT EXISTS `discussment`;
use discussment;

-- create userId
CREATE TABLE IF NOT EXISTS `userId` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `username` varchar(255),
    `name` varchar(255),
    `lastname` varchar(255),
    `permissions` bigint(20),
    PRIMARY KEY(`id`)
);

-- create permission
CREATE TABLE IF NOT EXISTS `userId` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `createCategory` tinyint(1),
    `removeCategory` tinyint(1),
    `createTopic` tinyint(1),
    `removeTopic` tinyint(1),
    `createDiscussion` tinyint(1),
    `removeDiscussion` tinyint(1),
    `createPost` tinyint(1),
    `removePost` tinyint(1),
    `disablePost` tinyint(1),
    `readPrivateDiscussion` tinyint(1),
    PRIMARY KEY(`id`)
);

-- create

