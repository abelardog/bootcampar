-- MySQL Script generated by MySQL Workbench
-- vie 29 sep 2023 23:09:06
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bootcampar
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `bootcampar` ;

-- -----------------------------------------------------
-- Schema bootcampar
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bootcampar` DEFAULT CHARACTER SET utf8 ;
USE `bootcampar` ;

-- -----------------------------------------------------
-- Table `bootcampar`.`Usuarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Usuarios` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Usuarios` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Apellido` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(90) NOT NULL,
  `Clave` VARCHAR(45) NOT NULL,
  `Rol` INT NULL,
  `Telefono` VARCHAR(30) NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `ID_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bootcampar`.`Cursos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Cursos` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Cursos` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Titulo` VARCHAR(45) NOT NULL,
  `Descripcion` VARCHAR(450) NULL,
  `Nivel` INT NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `ID_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bootcampar`.`Inscripciones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Inscripciones` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Inscripciones` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `UsuarioId` INT NOT NULL,
  `CursoId` INT NOT NULL,
  `Puntuacion` INT ZEROFILL NULL,
  `Favorito` TINYINT ZEROFILL NULL,
  `UltimaLeccion` INT ZEROFILL NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `idInscripciones_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `fk_Inscripciones_1_idx` (`UsuarioId` ASC) VISIBLE,
  INDEX `fk_Inscripciones_2_idx` (`CursoId` ASC) VISIBLE,
  CONSTRAINT `fk_Inscripciones_1`
    FOREIGN KEY (`UsuarioId`)
    REFERENCES `bootcampar`.`Usuarios` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inscripciones_2`
    FOREIGN KEY (`CursoId`)
    REFERENCES `bootcampar`.`Cursos` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bootcampar`.`Categorias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Categorias` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Categorias` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Descripcion` VARCHAR(45) NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `ID_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bootcampar`.`Categorizaciones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Categorizaciones` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Categorizaciones` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `CursoId` INT NOT NULL,
  `CategoriaId` INT NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `ID_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `fk_Categorizaciones_1_idx` (`CategoriaId` ASC) VISIBLE,
  INDEX `fk_Categorizaciones_2_idx` (`CursoId` ASC) VISIBLE,
  CONSTRAINT `fk_Categorizaciones_1`
    FOREIGN KEY (`CategoriaId`)
    REFERENCES `bootcampar`.`Categorias` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Categorizaciones_2`
    FOREIGN KEY (`CursoId`)
    REFERENCES `bootcampar`.`Cursos` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bootcampar`.`Lecciones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Lecciones` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Lecciones` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Titulo` VARCHAR(45) NOT NULL,
  `Contenido` VARCHAR(450) NULL,
  `Duracion` INT ZEROFILL NOT NULL,
  `Orden` INT ZEROFILL NOT NULL,
  `IDCurso` INT NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `ID_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `fk_Lecciones_1_idx` (`IDCurso` ASC) VISIBLE,
  CONSTRAINT `fk_Lecciones_1`
    FOREIGN KEY (`IDCurso`)
    REFERENCES `bootcampar`.`Cursos` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bootcampar`.`Grupos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Grupos` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Grupos` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Invitacion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `ID_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bootcampar`.`Curriculas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Curriculas` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Curriculas` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `CursoId` INT NOT NULL,
  `GrupoId` INT NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `ID_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `fk_Curriculas_1_idx` (`CursoId` ASC) VISIBLE,
  INDEX `fk_Curriculas_2_idx` (`GrupoId` ASC) VISIBLE,
  CONSTRAINT `fk_Curriculas_1`
    FOREIGN KEY (`CursoId`)
    REFERENCES `bootcampar`.`Cursos` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Curriculas_2`
    FOREIGN KEY (`GrupoId`)
    REFERENCES `bootcampar`.`Grupos` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bootcampar`.`Divisiones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bootcampar`.`Divisiones` ;

CREATE TABLE IF NOT EXISTS `bootcampar`.`Divisiones` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `GrupoId` INT NOT NULL,
  `UsuarioId` INT NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `ID_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `fk_Divisiones_2_idx` (`GrupoId` ASC) VISIBLE,
  INDEX `fk_Divisiones_1_idx` (`UsuarioId` ASC) VISIBLE,
  CONSTRAINT `fk_Divisiones_1`
    FOREIGN KEY (`UsuarioId`)
    REFERENCES `bootcampar`.`Usuarios` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Divisiones_2`
    FOREIGN KEY (`GrupoId`)
    REFERENCES `bootcampar`.`Grupos` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
