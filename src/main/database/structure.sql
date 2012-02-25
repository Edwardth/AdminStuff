SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `adminstuff` DEFAULT CHARACTER SET utf8 ;
USE `adminstuff` ;

-- -----------------------------------------------------
-- Table `adminstuff`.`player`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `adminstuff`.`player` (
  `idplayer` INT NOT NULL AUTO_INCREMENT ,
  `accountName` TEXT NULL ,
  `displayName` TEXT NULL ,
  `afk` TINYINT(1) NULL ,
  `muted` TINYINT(1) NULL ,
  `banned` TINYINT(1) NULL ,
  `god` TINYINT(1) NULL ,
  `mode` TINYINT NULL ,
  `tempBann` MEDIUMTEXT NULL ,
  `lastSeen` DATETIME NULL ,
  `gluelocation` TEXT NULL ,
  PRIMARY KEY (`idplayer`) )
ENGINE = InnoDB
COMMENT = '		';


-- -----------------------------------------------------
-- Table `adminstuff`.`inventorybackup`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `adminstuff`.`inventorybackup` (
  `idinventory` INT NOT NULL AUTO_INCREMENT ,
  `itemId` SMALLINT UNSIGNED NULL ,
  `subId` SMALLINT UNSIGNED NULL ,
  `amount` TINYINT UNSIGNED NULL ,
  PRIMARY KEY (`idinventory`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adminstuff`.`player_has_inventorybackup`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `adminstuff`.`player_has_inventorybackup` (
  `player` INT NOT NULL ,
  `inventory` INT NOT NULL ,
  PRIMARY KEY (`player`, `inventory`) ,
  INDEX `fk_player_has_inventorybackup_inventorybackup1` (`inventory` ASC) ,
  INDEX `fk_player_has_inventorybackup_player1` (`player` ASC) ,
  CONSTRAINT `fk_player_has_inventorybackup_player1`
    FOREIGN KEY (`player` )
    REFERENCES `adminstuff`.`player` (`idplayer` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_player_has_inventorybackup_inventorybackup1`
    FOREIGN KEY (`inventory` )
    REFERENCES `adminstuff`.`inventorybackup` (`idinventory` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
