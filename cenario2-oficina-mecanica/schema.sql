-- Schema do banco de dados para o Cenário 2 - Oficina Mecânica
-- Execute este script no PostgreSQL (psql, DBeaver, pgAdmin etc.) antes de rodar a aplicação.
-- Crie o banco antes, se ainda não existir:
-- CREATE DATABASE oficina_mecanica;

CREATE TABLE IF NOT EXISTS cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS veiculo (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(10) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    ano INTEGER NOT NULL,
    id_cliente INTEGER NOT NULL REFERENCES cliente(id)
);

CREATE TABLE IF NOT EXISTS ordem_servico (
    id SERIAL PRIMARY KEY,
    id_veiculo INTEGER NOT NULL REFERENCES veiculo(id),
    descricao_problema VARCHAR(255) NOT NULL,
    valor_servico NUMERIC(10,2) NOT NULL CHECK (valor_servico >= 0),
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTA' CHECK (status IN ('ABERTA', 'CONCLUIDA'))
);
