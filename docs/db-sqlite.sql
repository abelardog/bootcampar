-- SQLite Script
-- Generated from MySQL Script

-- PRAGMA statements to set foreign keys and enforce integrity
PRAGMA foreign_keys=ON;
PRAGMA foreign_key_check;

-- Create the database if it doesn't exist (SQLite does not have CREATE SCHEMA)
ATTACH DATABASE 'bootcampar.db' AS bootcampar;

-- Table `Usuarios`
CREATE TABLE IF NOT EXISTS bootcampar.Usuarios (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  Nombre TEXT NOT NULL,
  Apellido TEXT NOT NULL,
  Email TEXT NOT NULL,
  Clave TEXT NOT NULL,
  Rol INTEGER,
  Telefono TEXT
);

-- Table `Cursos`
CREATE TABLE IF NOT EXISTS bootcampar.Cursos (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  Titulo TEXT NOT NULL,
  Descripcion TEXT,
  Nivel INTEGER NOT NULL
);

-- Table `Inscripciones`
CREATE TABLE IF NOT EXISTS bootcampar.Inscripciones (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  UsuarioId INTEGER NOT NULL,
  CursoId INTEGER NOT NULL,
  Puntuacion INTEGER,
  Favorito INTEGER,
  FOREIGN KEY (UsuarioId) REFERENCES Usuarios (Id) ON DELETE CASCADE ON UPDATE NO ACTION,
  FOREIGN KEY (CursoId) REFERENCES Cursos (Id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Table `Categorias`
CREATE TABLE IF NOT EXISTS bootcampar.Categorias (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  Nombre TEXT NOT NULL,
  Descripcion TEXT
);

-- Table `Categorizaciones`
CREATE TABLE IF NOT EXISTS bootcampar.Categorizaciones (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  CursoId INTEGER NOT NULL,
  CategoriaId INTEGER NOT NULL,
  FOREIGN KEY (CursoId) REFERENCES Cursos (Id) ON DELETE CASCADE ON UPDATE NO ACTION,
  FOREIGN KEY (CategoriaId) REFERENCES Categorias (Id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Table `Lecciones`
CREATE TABLE IF NOT EXISTS bootcampar.Lecciones (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  Titulo TEXT NOT NULL,
  Contenido TEXT,
  Duracion INTEGER NOT NULL,
  Orden INTEGER NOT NULL,
  CursoId INTEGER NOT NULL,
  FOREIGN KEY (CursoId) REFERENCES Cursos (Id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Table `Grupos`
CREATE TABLE IF NOT EXISTS bootcampar.Grupos (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  Nombre TEXT NOT NULL,
  Invitacion TEXT NOT NULL
);

-- Table `Curriculas`
CREATE TABLE IF NOT EXISTS bootcampar.Curriculas (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  CursoId INTEGER NOT NULL,
  GrupoId INTEGER NOT NULL,
  FOREIGN KEY (GrupoId) REFERENCES Grupos (Id) ON DELETE CASCADE ON UPDATE NO ACTION,
  FOREIGN KEY (CursoId) REFERENCES Cursos (Id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- Table `Divisiones`
CREATE TABLE IF NOT EXISTS bootcampar.Divisiones (
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  UsuarioId INTEGER NOT NULL,
  GrupoId INTEGER NOT NULL,
  FOREIGN KEY (GrupoId) REFERENCES Grupos (Id) ON DELETE CASCADE ON UPDATE NO ACTION,
  FOREIGN KEY (UsuarioId) REFERENCES Usuarios (Id) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- PRAGMA statements to set foreign keys and enforce integrity
PRAGMA foreign_keys=ON;
PRAGMA foreign_key_check;
