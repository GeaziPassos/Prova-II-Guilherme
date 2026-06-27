# Cenário 2 — Sistema de Oficina Mecânica

Sistema simples para cadastro de clientes, veículos e ordens de serviço, permitindo
consultar o histórico de manutenções de um determinado veículo.

## Tecnologias

- Java 17
- Maven
- JDBC (SQL puro, sem ORM)
- PostgreSQL

## Tabelas identificadas

A partir do relato do cliente foram identificadas três entidades: **Cliente**
(cadastro simples, existe de forma independente), **Veiculo** (cadastro simples,
vinculado a um cliente) e **OrdemServico** (o "movimento" do sistema, pois depende
de um veículo já cadastrado e, indiretamente, do cliente dele).

### Tabela `cliente`

| Campo     | Tipo          | Observação            |
|-----------|---------------|------------------------|
| id        | SERIAL PK     | Gerado automaticamente |
| nome      | VARCHAR(150)  | Obrigatório            |
| telefone  | VARCHAR(20)   | Obrigatório            |

### Tabela `veiculo`

| Campo       | Tipo          | Observação                          |
|-------------|---------------|---------------------------------------|
| id          | SERIAL PK     | Gerado automaticamente                |
| placa       | VARCHAR(10)   | Obrigatório                           |
| modelo      | VARCHAR(100)  | Obrigatório                           |
| ano         | INTEGER       | Obrigatório                           |
| id_cliente  | INTEGER FK    | Referencia `cliente(id)`, obrigatório |

### Tabela `ordem_servico`

| Campo               | Tipo          | Observação                                  |
|---------------------|---------------|------------------------------------------------|
| id                  | SERIAL PK     | Gerado automaticamente                        |
| id_veiculo          | INTEGER FK    | Referencia `veiculo(id)`, obrigatório         |
| descricao_problema  | VARCHAR(255)  | Obrigatório (problema relatado pelo cliente)  |
| valor_servico       | NUMERIC(10,2) | Obrigatório, não pode ser negativo (CHECK)    |
| status              | VARCHAR(20)   | `ABERTA` ou `CONCLUIDA` (CHECK), padrão `ABERTA` |

### Comandos `CREATE TABLE`

```sql
CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);

CREATE TABLE veiculo (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(10) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    ano INTEGER NOT NULL,
    id_cliente INTEGER NOT NULL REFERENCES cliente(id)
);

CREATE TABLE ordem_servico (
    id SERIAL PRIMARY KEY,
    id_veiculo INTEGER NOT NULL REFERENCES veiculo(id),
    descricao_problema VARCHAR(255) NOT NULL,
    valor_servico NUMERIC(10,2) NOT NULL CHECK (valor_servico >= 0),
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTA' CHECK (status IN ('ABERTA', 'CONCLUIDA'))
);
```

> O script completo também está disponível em [`schema.sql`](./schema.sql).

## Regras de negócio levantadas a partir do texto do cliente

1. Um cliente pode ter mais de um veículo cadastrado (relação 1:N entre `cliente`
   e `veiculo`).
2. Não é possível abrir uma ordem de serviço para um veículo que não esteja
   cadastrado.
3. O valor do serviço não pode ser negativo.
4. Toda ordem de serviço precisa indicar se está aberta ou já foi concluída.
5. Deve ser possível consultar todo o histórico de manutenções (ordens de serviço)
   de um determinado veículo.

## Estrutura do projeto (padrão MVC)

```
src/main/java/edu/umfg/oficina/
├── model/        → Cliente, Veiculo, OrdemServico, StatusOrdem
├── repository/    → ClienteRepository, VeiculoRepository, OrdemServicoRepository (CRUD via JDBC)
├── service/       → ClienteService, VeiculoService, OrdemServicoService (regras de negócio)
├── controller/     → ClienteController, VeiculoController, OrdemServicoController
├── util/          → Conexao.java (configuração da conexão JDBC)
└── Main.java      → simula o fluxo: cliente → veículo → ordem de serviço
```

## Como executar

1. Crie o banco de dados no PostgreSQL:
   ```sql
   CREATE DATABASE oficina_mecanica;
   ```
2. Execute o script [`schema.sql`](./schema.sql) nesse banco.
3. Ajuste usuário/senha/URL em `src/main/java/edu/umfg/oficina/util/Conexao.java`
   caso sejam diferentes de `postgres` / `postgres` / `localhost:5432`.
4. Importe o projeto no IntelliJ como projeto Maven (ele vai baixar o driver do
   PostgreSQL automaticamente a partir do `pom.xml`).
5. Execute a classe `Main.java`.

A `Main` cadastra um cliente, cadastra um veículo vinculado a ele, abre uma ordem de
serviço, conclui essa ordem, lista o histórico de manutenções do veículo e, por fim,
demonstra as regras de negócio tentando abrir uma ordem de serviço para um veículo
inexistente e uma ordem com valor negativo (ambas devem ser rejeitadas).
