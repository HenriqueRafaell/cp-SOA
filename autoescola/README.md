# Auto-Escola API — Checkpoint 5

API REST desenvolvida com Spring Boot para agendamento de instruções de uma auto-escola.

**Disciplina:** SOA e WebServices — FIAP  
**Professor:** Carlos Eduardo Machado de Oliveira

---

## Integrantes do Grupo

- (Adicione os nomes dos integrantes aqui)

---

## Tecnologias

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- H2 Database (em memória — desenvolvimento)
- Springdoc OpenAPI (Swagger)
- Lombok

---

## Como executar

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

---

## Documentação (Swagger)

Acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Console H2 (banco em memória)

Acesse: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- **JDBC URL:** `jdbc:h2:mem:autoescoladb`
- **User:** `sa`
- **Password:** *(vazio)*

---

## Endpoints

### Instrutores — `/instrutores`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/instrutores` | Cadastrar instrutor |
| GET | `/instrutores?pagina=0` | Listar instrutores ativos (paginado, 10/pág) |
| GET | `/instrutores/{id}` | Buscar instrutor por ID |
| PUT | `/instrutores/{id}` | Atualizar nome, telefone e/ou endereço |
| DELETE | `/instrutores/{id}` | Desativar instrutor (exclusão lógica) |

### Alunos — `/alunos`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/alunos` | Cadastrar aluno |
| GET | `/alunos?pagina=0` | Listar alunos ativos (paginado, 10/pág) |
| GET | `/alunos/{id}` | Buscar aluno por ID |
| PUT | `/alunos/{id}` | Atualizar nome, telefone e/ou endereço |
| DELETE | `/alunos/{id}` | Desativar aluno (exclusão lógica) |

### Instruções — `/instrucoes`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/instrucoes` | Agendar instrução |
| GET | `/instrucoes` | Listar todas as instruções |
| GET | `/instrucoes/{id}` | Buscar instrução por ID |
| PATCH | `/instrucoes/{id}/cancelar` | Cancelar instrução |

### CEP (API Externa) — `/cep`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/cep/{cep}` | Consultar endereço via ViaCEP |

---

## Regras de Negócio

### Instrutores e Alunos
- Exclusão lógica (campo `ativo = false`)
- E-mail, CNH (instrutor) e CPF (aluno) não podem ser alterados

### Agendamento de Instruções
- Funcionamento: segunda a sábado, 06:00 às 21:00
- Duração fixa de 1 hora
- Antecedência mínima: 30 minutos
- Máximo 2 instruções por dia para o mesmo aluno
- Sem conflito de horário para o instrutor
- Instrutor opcional — se não informado, é escolhido aleatoriamente entre os disponíveis

### Cancelamento de Instruções
- Motivo obrigatório: `ALUNO_DESISTIU`, `INSTRUTOR_CANCELOU` ou `OUTROS`
- Antecedência mínima: 24 horas
