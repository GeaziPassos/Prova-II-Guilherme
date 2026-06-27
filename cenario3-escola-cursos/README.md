# Cenário 3 — Sistema de Escola de Cursos Livres

Sistema simples para cadastro de alunos, cursos e matrículas, controlando o limite
de vagas de cada curso e impedindo matrícula duplicada do mesmo aluno no mesmo curso.

## Tecnologias

- Java 17
- Maven
- JDBC (SQL puro, sem ORM)
- PostgreSQL

## Tabelas identificadas

A partir do relato do cliente foram identificadas três entidades: **Aluno** (cadastro
simples, existe de forma independente), **Curso** (cadastro simples, existe de forma
independente) e **Matricula** (o "movimento" do sistema, pois depende
simultaneamente de um aluno e de um curso já cadastrados).

### Tabela `aluno`

| Campo     | Tipo          | Observação                  |
|-----------|---------------|--------------------------------|
| id        | SERIAL PK     | Gerado automaticamente         |
| nome      | VARCHAR(150)  | Obrigatório                    |
| email     | VARCHAR(150)  | Obrigatório, único              |
| telefone  | VARCHAR(20)   | Obrigatório                    |

### Tabela `curso`

| Campo              | Tipo          | Observação                                       |
|--------------------|---------------|------------------------------------------------------|
| id                 | SERIAL PK     | Gerado automaticamente                               |
| nome               | VARCHAR(150)  | Obrigatório                                          |
| descricao          | VARCHAR(255)  | Opcional                                             |
| carga_horaria      | INTEGER       | Obrigatório                                          |
| vagas_totais       | INTEGER       | Obrigatório, número máximo de vagas do curso         |
| vagas_disponiveis  | INTEGER       | Controla quantas vagas ainda restam (decrementada a cada matrícula) |

### Tabela `matricula`

| Campo            | Tipo          | Observação                                                |
|-------------------|---------------|---------------------------------------------------------------|
| id               | SERIAL PK     | Gerado automaticamente                                       |
| id_aluno          | INTEGER FK    | Referencia `aluno(id)`, obrigatório                          |
| id_curso          | INTEGER FK    | Referencia `curso(id)`, obrigatório                          |
| data_matricula    | DATE          | Obrigatório                                                  |
| valor_pago        | NUMERIC(10,2) | Obrigatório, não pode ser negativo (CHECK)                   |
| —                | UNIQUE        | `(id_aluno, id_curso)` — impede matrícula duplicada no mesmo curso |

### Comandos `CREATE TABLE`

```sql
CREATE TABLE aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL
);

CREATE TABLE curso (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(255),
    carga_horaria INTEGER NOT NULL,
    vagas_totais INTEGER NOT NULL,
    vagas_disponiveis INTEGER NOT NULL
);

CREATE TABLE matricula (
    id SERIAL PRIMARY KEY,
    id_aluno INTEGER NOT NULL REFERENCES aluno(id),
    id_curso INTEGER NOT NULL REFERENCES curso(id),
    data_matricula DATE NOT NULL,
    valor_pago NUMERIC(10,2) NOT NULL CHECK (valor_pago >= 0),
    UNIQUE (id_aluno, id_curso)
);
```

> O script completo também está disponível em [`schema.sql`](./schema.sql).

## Regras de negócio levantadas a partir do texto do cliente

1. Não é possível matricular um aluno que não esteja cadastrado.
2. Não é possível matricular um aluno em um curso que não esteja cadastrado.
3. Não é possível matricular um aluno em um curso que já atingiu o limite de vagas
   (`vagas_disponiveis` controla isso e é decrementada a cada matrícula efetivada).
4. O mesmo aluno não pode ser matriculado duas vezes no mesmo curso (regra reforçada
   tanto no `service` quanto pela constraint `UNIQUE (id_aluno, id_curso)` no banco).
5. O valor pago na matrícula não pode ser negativo.
6. Deve ser possível consultar todos os alunos matriculados em um curso.
7. Deve ser possível consultar todos os cursos em que um aluno está matriculado.

> Todas essas validações de matrícula (vagas e duplicidade) ficam concentradas na
> camada `service`, conforme indicado na dica do exercício.

## Estrutura do projeto (padrão MVC)

```
src/main/java/edu/umfg/escola/
├── model/        → Aluno, Curso, Matricula
├── repository/    → AlunoRepository, CursoRepository, MatriculaRepository (CRUD via JDBC)
├── service/       → AlunoService, CursoService, MatriculaService (regras de negócio)
├── controller/     → AlunoController, CursoController, MatriculaController
├── util/          → Conexao.java (configuração da conexão JDBC)
└── Main.java      → simula o fluxo: aluno → curso → matrícula (+ tentativas inválidas)
```

## Como executar

1. Crie o banco de dados no PostgreSQL:
   ```sql
   CREATE DATABASE escola_cursos;
   ```
2. Execute o script [`schema.sql`](./schema.sql) nesse banco.
3. Ajuste usuário/senha/URL em `src/main/java/edu/umfg/escola/util/Conexao.java`
   caso sejam diferentes de `postgres` / `postgres` / `localhost:5432`.
4. Importe o projeto no IntelliJ como projeto Maven (ele vai baixar o driver do
   PostgreSQL automaticamente a partir do `pom.xml`).
5. Execute a classe `Main.java`.

A `Main` cadastra um aluno, cadastra um curso com 1 vaga disponível, matricula o
aluno no curso e, em seguida, demonstra as duas regras de negócio pedidas no
exercício: tenta matricular o mesmo aluno novamente no mesmo curso (matrícula
duplicada) e tenta matricular um segundo aluno no mesmo curso já sem vagas
disponíveis — ambas as tentativas devem ser rejeitadas pelo `service`.
