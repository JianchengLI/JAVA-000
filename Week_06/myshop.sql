-- MySQL Script generated by MySQL Workbench
-- Mon Nov 23 21:59:31 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema myshopdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema myshopdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `myshopdb` DEFAULT CHARACTER SET utf8 ;
USE `myshopdb` ;

-- -----------------------------------------------------
-- Table `myshopdb`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myshopdb`.`order` ;

CREATE TABLE IF NOT EXISTS `myshopdb`.`order` (
  `id` BIGINT NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  `user_id` BIGINT NOT NULL,
  `order_total_price` DOUBLE NOT NULL,
  `status` TINYINT UNSIGNED NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `myshopdb`.`order` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `myshopdb`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myshopdb`.`product` ;

CREATE TABLE IF NOT EXISTS `myshopdb`.`product` (
  `id` BIGINT NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` TINYINT UNSIGNED NOT NULL,
  `price` DOUBLE UNSIGNED NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));

CREATE UNIQUE INDEX `id_UNIQUE` ON `myshopdb`.`product` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `myshopdb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myshopdb`.`user` ;

CREATE TABLE IF NOT EXISTS `myshopdb`.`user` (
  `id` BIGINT NOT NULL,
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(45) NULL,
  `telephone` VARCHAR(45) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `status` TINYINT UNSIGNED NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));

CREATE UNIQUE INDEX `id_UNIQUE` ON `myshopdb`.`user` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `myshopdb`.`shopping_cart_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myshopdb`.`shopping_cart_item` ;

CREATE TABLE IF NOT EXISTS `myshopdb`.`shopping_cart_item` (
  `id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `order_id` BIGINT NULL,
  `product_id` BIGINT NOT NULL,
  `order_product_price` DOUBLE NOT NULL,
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIME_STAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIME_STAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_shopping_cart_item_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `myshopdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shopping_cart_item_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `myshopdb`.`order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_shopping_cart_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `myshopdb`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_shopping_cart_item_user_idx` ON `myshopdb`.`shopping_cart_item` (`user_id` ASC) VISIBLE;

CREATE INDEX `fk_shopping_cart_item_order1_idx` ON `myshopdb`.`shopping_cart_item` (`order_id` ASC) VISIBLE;

CREATE INDEX `fk_shopping_cart_item_product1_idx` ON `myshopdb`.`shopping_cart_item` (`product_id` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
