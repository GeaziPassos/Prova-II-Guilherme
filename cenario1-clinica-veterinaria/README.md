# Cenário 1 — Sistema de Clínica Veterinária

Sistema simples para cadastro de tutores, animais e consultas, permitindo consultar
o histórico de atendimentos de cada animal e os animais de cada tutor.

## Tecnologias

- Java 17
- Maven
- JDBC (SQL puro, sem ORM)
- PostgreSQL

## Tabelas identificadas

A partir do relato do cliente foram identificadas três entidades: **Tutor** (cadastro
simples, existe de forma independente), **Animal** (cadastro simples, vinculado a um
tutor) e **Consulta** (o "movimento" do sistema, pois depende de um animal já
cadastrado e, indiretamente, do tutor dele).

### Tabela `tutor`

| Campo     | Tipo          | Observação            |
|-----------|---------------|------------------------|
| id        | SERIAL PK     | Gerado automaticamente |
| nome      | VARCHAR(150)  | Obrigatório            |
| endereco  | VARCHAR(200)  | Obrigatório            |
| telefone  | VARCHAR(20)   | Obrigatório            |

### Tabela `animal`

| Campo     | Tipo          | Observação                          |
|-----------|---------------|---------------------------------------|
| id        | SERIAL PK     | Gerado automaticamente                |
| nome      | VARCHAR(100)  | Obrigatório                           |
| especie   | VARCHAR(50)   | Obrigatório                           |
| raca      | VARCHAR(50)   | Obrigatório                           |
| id_tutor  | INTEGER FK    | Referencia `tutor(id)`, obrigatório   |

### Tabela `consulta`

| Campo         | Tipo          | Observação                              |
|---------------|---------------|-------------------------------------------|
| id            | SERIAL PK     | Gerado automaticamente                    |
| id_animal     | INTEGER FK    | Referencia `animal(id)`, obrigatório      |
| data_consulta | DATE          | Obrigatório                               |
| motivo        | VARCHAR(200)  | Obrigatório                               |
| valor         | NUMERIC(10,2) | Obrigatório, não pode ser negativo (CHECK)|

### Comandos `CREATE TABLE`

```sql
CREATE TABLE tutor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    telefone VARCHAR(20) NOT NULL
);

CREATE TABLE animal (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raca VARCHAR(50) NOT NULL,
    id_tutor INTEGER NOT NULL REFERENCES tutor(id)
);

CREATE TABLE consulta (
    id SERIAL PRIMARY KEY,
    id_animal INTEGER NOT NULL REFERENCES animal(id),
    data_consulta DATE NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    valor NUMERIC(10,2) NOT NULL CHECK (valor >= 0)
);
```

> O script completo também está disponível em [`schema.sql`](./schema.sql).

## Regras de negócio levantadas a partir do texto do cliente

1. Um tutor pode ter mais de um animal cadastrado (relação 1:N entre `tutor` e `animal`).
2. Não é possível registrar uma consulta para um animal que não esteja cadastrado.
3. O valor cobrado na consulta não pode ser negativo.
4. Deve ser possível consultar todas as consultas (histórico) de um animal específico.
5. Deve ser possível consultar todos os animais cadastrados de um determinado tutor.
6. Cada animal pertence a exatamente um tutor, evitando o problema relatado de
   confundir animais de mesmo nome e donos diferentes (o vínculo `id_tutor` identifica
   univocamente cada animal).

## Estrutura do projeto (padrão MVC)

```
src/main/java/edu/umfg/veterinaria/
├── model/        → Tutor, Animal, Consulta
├── repository/    → TutorRepository, AnimalRepository, ConsultaRepository (CRUD via JDBC)
├── service/       → TutorService, AnimalService, ConsultaService (regras de negócio)
├── controller/     → TutorController, AnimalController, ConsultaController
├── util/          → Conexao.java (configuração da conexão JDBC)
└── Main.java      → simula o fluxo: tutor → animal → consulta
```

## Como executar

1. Crie o banco de dados no PostgreSQL:
   ```sql
   CREATE DATABASE clinica_veterinaria;
   ```
2. Execute o script [`schema.sql`](./schema.sql) nesse banco.
3. Ajuste usuário/senha/URL em `src/main/java/edu/umfg/veterinaria/util/Conexao.java`
   caso sejam diferentes de `postgres` / `postgres` / `localhost:5432`.
4. Importe o projeto no IntelliJ como projeto Maven (ele vai baixar o driver do
   PostgreSQL automaticamente a partir do `pom.xml`).
5. Execute a classe `Main.java`.

A `Main` cadastra um tutor, cadastra um animal vinculado a ele, registra uma consulta,
lista o histórico de consultas do animal, lista os animais do tutor e, por fim,
demonstra as regras de negócio tentando registrar uma consulta para um animal
inexistente e uma consulta com valor negativo (ambas devem ser rejeitadas).
