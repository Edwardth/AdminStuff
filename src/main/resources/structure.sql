CREATE DATABASE IF NOT EXISTS `adminstuff` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
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
  `probeEndDate` DATETIME NULL ,
  `gluelocation` INT NULL ,
  PRIMARY KEY (`idplayer`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `adminstuff`.`gluelocation`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `adminstuff`.`gluelocation` (
  `idglue` INT NOT NULL AUTO_INCREMENT ,
  `world` TEXT NULL ,
  `x` DOUBLE NULL ,
  `y` DOUBLE NULL ,
  `z` DOUBLE NULL ,
  `pitch` FLOAT NULL ,
  `yaw` FLOAT NULL ,
  PRIMARY KEY (`idglue`) )
ENGINE = InnoDB;


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
