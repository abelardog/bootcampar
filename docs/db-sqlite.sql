-- SQLite Script
-- Generated from MySQL Script

-- PRAGMA statements to set foreign keys and enforce integrity
PRAGMA foreign_keys=ON;
PRAGMA foreign_key_check;

-- Create the database if it doesn't exist (SQLite does not have CREATE SCHEMA)
ATTACH DATABASE 'bootcampar.db' AS bootcampar;

-- Table `Usuarios`
CREATE TABLE IF NOT EXISTS bootcampar.Usuarios (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Nombre TEXT NOT NULL,
  Apellido TEXT NOT NULL,
  Email TEXT NOT NULL,
  Clave TEXT NOT NULL,
  Rol INTEGER,
  Telefono TEXT
);

-- Table `Cursos`
CREATE TABLE IF NOT EXISTS bootcampar.Cursos (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Titulo TEXT NOT NULL,
  Descripcion TEXT,
  Nivel INTEGER NOT NULL
);

-- Table `Inscripciones`
CREATE TABLE IF NOT EXISTS bootcampar.Inscripciones (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  IDUsuario INTEGER NOT NULL,
  IDCurso INTEGER NOT NULL,
  Puntuacion INTEGER,
  Favorito INTEGER,
  FOREIGN KEY (IDUsuario) REFERENCES Usuarios (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (IDCurso) REFERENCES Cursos (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Table `Categorias`
CREATE TABLE IF NOT EXISTS bootcampar.Categorias (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Nombre TEXT NOT NULL,
  Descripcion TEXT
);

-- Table `Categorizaciones`
CREATE TABLE IF NOT EXISTS bootcampar.Categorizaciones (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  IDCurso INTEGER NOT NULL,
  IDCategoria INTEGER NOT NULL,
  FOREIGN KEY (IDCurso) REFERENCES Cursos (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (IDCategoria) REFERENCES Categorias (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Table `Lecciones`
CREATE TABLE IF NOT EXISTS bootcampar.Lecciones (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  Titulo TEXT NOT NULL,
  Contenido TEXT,
  Duracion INTEGER NOT NULL,
  Orden INTEGER NOT NULL,
  IDCurso INTEGER NOT NULL,
  FOREIGN KEY (IDCurso) REFERENCES Cursos (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- PRAGMA statements to set foreign keys and enforce integrity
PRAGMA foreign_keys=ON;
PRAGMA foreign_key_check;
