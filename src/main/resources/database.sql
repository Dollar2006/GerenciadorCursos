CREATE DATABASE IF NOT EXISTS sistema_cursos;
USE sistema_cursos;

CREATE TABLE IF NOT EXISTS cursos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL,
    vagas INT NOT NULL,
    vagas_ocupadas INT NOT NULL DEFAULT 0,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT nome_min_length CHECK (LENGTH(nome) >= 3),
    CONSTRAINT carga_horaria_min CHECK (carga_horaria >= 20),
    CONSTRAINT vagas_min CHECK (vagas >= 1),
    CONSTRAINT vagas_ocupadas_check CHECK (vagas_ocupadas >= 0 AND vagas_ocupadas <= vagas)
);

CREATE TABLE IF NOT EXISTS alunos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    id_curso INT,
    CONSTRAINT nome_aluno_min_length CHECK (LENGTH(nome) >= 3),
    CONSTRAINT email_format CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'),
    CONSTRAINT cpf_format CHECK (cpf REGEXP '^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$'),
    CONSTRAINT fk_curso FOREIGN KEY (id_curso) REFERENCES cursos(id) ON DELETE CASCADE
); 