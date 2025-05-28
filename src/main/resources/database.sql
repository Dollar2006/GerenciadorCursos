CREATE DATABASE IF NOT EXISTS sistema_cursos;
USE sistema_cursos;

CREATE TABLE IF NOT EXISTS cursos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL,
    limite_alunos INT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT nome_min_length CHECK (LENGTH(nome) >= 3),
    CONSTRAINT carga_horaria_min CHECK (carga_horaria >= 20),
    CONSTRAINT limite_alunos_min CHECK (limite_alunos >= 1)
);

CREATE TABLE IF NOT EXISTS alunos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    id_curso INT,
    CONSTRAINT nome_min_length CHECK (LENGTH(nome) >= 3),
    CONSTRAINT cpf_length CHECK (LENGTH(cpf) = 11),
    CONSTRAINT email_format CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'),
    CONSTRAINT idade_minima CHECK (TIMESTAMPDIFF(YEAR, data_nascimento, CURDATE()) >= 16),
    CONSTRAINT fk_curso FOREIGN KEY (id_curso) REFERENCES cursos(id) ON DELETE CASCADE
); 