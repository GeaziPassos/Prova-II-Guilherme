-- Schema do banco de dados para o Cenário 3 - Escola de Cursos Livres
-- Execute este script no PostgreSQL (psql, DBeaver, pgAdmin etc.) antes de rodar a aplicação.
-- Crie o banco antes, se ainda não existir:
-- CREATE DATABASE escola_cursos;

CREATE TABLE IF NOT EXISTS aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS curso (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(255),
    carga_horaria INTEGER NOT NULL,
    vagas_totais INTEGER NOT NULL,
    vagas_disponiveis INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS matricula (
    id SERIAL PRIMARY KEY,
    id_aluno INTEGER NOT NULL REFERENCES aluno(id),
    id_curso INTEGER NOT NULL REFERENCES curso(id),
    data_matricula DATE NOT NULL,
    valor_pago NUMERIC(10,2) NOT NULL CHECK (valor_pago >= 0),
    UNIQUE (id_aluno, id_curso)
);
