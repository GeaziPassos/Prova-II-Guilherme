-- Schema do banco de dados para o Cenário 1 - Clínica Veterinária
-- Execute este script no PostgreSQL (psql, DBeaver, pgAdmin etc.) antes de rodar a aplicação.
-- Crie o banco antes, se ainda não existir:
-- CREATE DATABASE clinica_veterinaria;

CREATE TABLE IF NOT EXISTS tutor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS animal (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raca VARCHAR(50) NOT NULL,
    id_tutor INTEGER NOT NULL REFERENCES tutor(id)
);

CREATE TABLE IF NOT EXISTS consulta (
    id SERIAL PRIMARY KEY,
    id_animal INTEGER NOT NULL REFERENCES animal(id),
    data_consulta DATE NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    valor NUMERIC(10,2) NOT NULL CHECK (valor >= 0)
);
