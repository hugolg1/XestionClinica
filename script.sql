drop database  if exists `clinica`;

CREATE SCHEMA `clinica`;

CREATE TABLE `clinica`.`RAZAS` (
  `codRaza` INT NOT NULL AUTO_INCREMENT ,
  `descripcion` VARCHAR(40) NOT NULL ,
  PRIMARY KEY (`codRaza`));

CREATE TABLE `clinica`.`PROPIETARIOS` (
  `dni` VARCHAR(15) NOT NULL,
  `nome` VARCHAR(50) NOT NULL ,
  `ap1` VARCHAR(50) NOT NULL ,  
  `ap2` VARCHAR(50) NOT NULL ,
    `tlf` VARCHAR(50) NOT NULL ,
  `eMail` VARCHAR(50) NOT NULL ,    
  PRIMARY KEY (`dni`));
  
CREATE TABLE `clinica`.`VACINAS` (
  `codVacina` INT NOT NULL AUTO_INCREMENT ,
  `nomeVacina` VARCHAR(50) NOT NULL ,
  `numTotalDoses` INT NOT NULL ,  
  PRIMARY KEY (`codVacina`));  


CREATE  TABLE `clinica`.`CANS` (
  `chip` VARCHAR(20) NOT NULL,
  `nome` VARCHAR(50) NOT NULL ,
  `codRaza` INT NOT NULL ,
  `dniPropietario` VARCHAR(15) NOT NULL ,

  PRIMARY KEY (`chip`),
  CONSTRAINT `BB` FOREIGN KEY (`codRaza`) REFERENCES `clinica`.`RAZAS` (`codRaza` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `CC` FOREIGN KEY (`dniPropietario`) REFERENCES `clinica`.`PROPIETARIOS` (`dni`) ON DELETE RESTRICT ON UPDATE RESTRICT);    

CREATE  TABLE `clinica`.`VACINACIONS` (
  `codVacinacion` INT NOT NULL AUTO_INCREMENT ,  
  `chip` VARCHAR(20) NOT NULL,
  `codVacina` INT NOT NULL ,
  `data` DATE NOT NULL ,
  `observacions` VARCHAR(100) NOT NULL ,  
  `dose` INT NOT NULL ,

  PRIMARY KEY (`codVacinacion`),
  CONSTRAINT `AA` FOREIGN KEY (`codVacina` ) REFERENCES `clinica`.`VACINAS` (`codVacina` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `DD` FOREIGN KEY (`chip` ) REFERENCES `clinica`.`CANS` (`chip` ) ON DELETE RESTRICT ON UPDATE RESTRICT);