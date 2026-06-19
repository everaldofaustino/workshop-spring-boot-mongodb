
# 📚 Workshop Spring Boot com MongoDB

[![Java](https://img.shields.io/badge/Java-25%2B-red.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-4.4%2B-green.svg)](https://www.mongodb.com/)

## 📖 Sobre o Projeto

Este projeto é um **sistema de gerenciamento de posts e usuários** desenvolvido durante o curso de **Programação Orientada a Objetos com Java** do Prof. Dr. Nelio Alves (Educandoweb). O objetivo é demonstrar a implementação de um **CRUD completo** utilizando **Spring Boot** com **MongoDB**, explorando as diferenças entre bancos de dados relacionais e orientados a documentos.

### 🎯 Objetivos do Projeto

- Compreender as principais diferenças entre o paradigma orientado a documentos e o relacional.
- Implementar operações de CRUD (Create, Read, Update, Delete).
- Refletir sobre decisões de design para um banco de dados orientado a documentos.
- Implementar associações entre objetos:
    - **Objetos aninhados** (embutidos)
    - **Referências** (`@DBRef`)
- Realizar consultas com **Spring Data** e **MongoRepository**.

---

## 🛠️ Tecnologias Utilizadas

- **Java 25** (ou superior)
- **Spring Boot 4.1.0**
- **Spring Data MongoDB**
- **MongoDB 4.4+**
- **Maven**
- **Postman** (para testes de API)
- **MongoDB Compass** (para gerenciamento visual do banco)

---

## 📂 Estrutura do Projeto

```
workshop-spring-boot-mongodb/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/everaldo/workshopmongo/
│       │       ├── config/
│       │       │   └── instantiation.java          # Carga inicial do banco
│       │       ├── domain/                         # Entidades
│       │       │   ├── User.java
│       │       │   ├── Post.java
│       │       │   └── Comment.java                # (ou CommentDTO)
│       │       ├── dto/                            # Data Transfer Objects
│       │       │   ├── UserDTO.java
│       │       │   ├── AuthorDTO.java
│       │       │   └── CommentDTO.java
│       │       ├── repository/                     # Repositórios
│       │       │   ├── UserRepository.java
│       │       │   └── PostRepository.java
│       │       ├── resources/                      # Controllers (REST)
│       │       │   ├── UserResource.java
│       │       │   └── PostResource.java
│       │       ├── services/                       # Camada de Serviço
│       │       │   ├── UserService.java
│       │       │   └── PostService.java
│       │       ├── resources/util/                 # Utilitários
│       │       │   └── URL.java                    # Decodificação de parâmetros
│       │       └── services/exception/             # Exceções customizadas
│       │           └── ObjectNotFoundException.java
│       └── resources/
│           └── application.properties              # Configurações do Spring
└── pom.xml                                         # Dependências Maven
```

---

## 🗄️ Modelagem do Banco de Dados

### 📋 Entidades e Relacionamentos

| Entidade | Descrição | Relacionamento |
|----------|-----------|----------------|
| **User** | Usuário do sistema (id, name, email) | → Lista de `posts` via `@DBRef` |
| **Post** | Postagem (id, date, title, body) | → `author` (User aninhado via `AuthorDTO`) <br> → Lista de `comments` (embutidos) |
| **Comment** | Comentário (text, date, author) | Embutido dentro de `Post` |

### 🔗 Relações no Diagrama

```
User (1) ──── posts ────→ (N) Post
Post (N) ──── author ──→ (1) User (via AuthorDTO)
Post (1) ──── comments ─→ (N) Comment (embutido)
```

### 📄 Exemplo de Documento no MongoDB

**Coleção `user`:**
```json
{
  "_id": { "$oid": "6a3326a84f4b9af27aed327a" },
  "name": "Maria Brown",
  "email": "maria@gmail.com",
  "posts": [
    { "$ref": "post", "$id": { "$oid": "..." } }
  ]
}
```

**Coleção `post`:**
```json
{
  "_id": { "$oid": "6a354b46f51f722c3f0954e4" },
  "date": { "$date": "2018-03-21T00:00:00Z" },
  "title": "Partiu viagem",
  "body": "Vou viajar para São Paulo. Abraços!",
  "author": {
    "id": "6a3326a84f4b9af27aed327a",
    "name": "Maria Brown"
  },
  "comments": [
    {
      "id": "8013",
      "text": "Aproveite!",
      "date": { "$date": "2018-03-22T00:00:00Z" }
    }
  ]
}
```

---

## 🔧 Configuração e Instalação

### ✅ Pré-requisitos

- **JDK 25** ou superior instalado.
- **MongoDB** instalado e em execução.
- **Maven** instalado.
- **MongoDB Compass** (opcional, mas recomendado).
- **Postman** (para testar a API).

### 🚀 Passo a passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/everaldo/workshop-spring-boot-mongodb.git
   cd workshop-spring-boot-mongodb
   ```

2. **Instale o MongoDB:**
    - **Windows:**
        - Baixe o [MongoDB Community Server](https://www.mongodb.com/try/download/community)
        - Instale com a opção "Complete"
        - Crie a pasta `C:\data\db`
        - Adicione `C:\Program Files\MongoDB\Server\X.X\bin` ao `PATH`
        - Execute `mongod` no terminal para iniciar
    - **Mac (via Homebrew):**
      ```bash
      brew install mongodb
      sudo mkdir -p /data/db
      sudo chown -Rv $USER /data/db
      mongod
      ```

3. **Configure o `application.properties`:**
   ```properties
   spring.application.name=workshopmongo
   spring.data.mongodb.uri=mongodb://localhost:27017/workshop_mongo
   ```

4. **Execute a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

5. **Acesse:**
    - API: `http://localhost:8080`
    - MongoDB Compass: `mongodb://localhost:27017`

---

## 📡 Endpoints da API

### 👤 Usuários (`/users`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/users` | Retorna todos os usuários |
| `GET` | `/users/{id}` | Retorna um usuário por ID |
| `GET` | `/users/{id}/posts` | Retorna os posts de um usuário |
| `POST` | `/users` | Cria um novo usuário |
| `PUT` | `/users/{id}` | Atualiza um usuário |
| `DELETE` | `/users/{id}` | Deleta um usuário |

### 📝 Posts (`/posts`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/posts/{id}` | Retorna um post por ID |
| `GET` | `/posts/titlesearch?text={text}` | Busca posts por título |
| `GET` | `/posts/fullsearch?text={text}&minDate={date}&maxDate={date}` | Busca posts por título, corpo ou comentários em um intervalo de datas |

---

## 🧪 Exemplos de Requisições

### 🔹 Criar um Usuário

**Requisição:**
```http
POST /users
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com"
}
```

**Resposta:**
```json
{
  "id": "6a344f5e39ef97ba1f4b57",
  "name": "João Silva",
  "email": "joao@email.com"
}
```

### 🔹 Buscar Posts por Título

**Requisição:**
```http
GET /posts/titlesearch?text=viagem
```

**Resposta:**
```json
[
  {
    "id": "6a354b46f51f722c3f0954e4",
    "title": "Partiu viagem",
    "body": "Vou viajar para São Paulo. Abraços!",
    "author": {
      "id": "6a3326a84f4b9af27aed327a",
      "name": "Maria Brown"
    },
    "comments": [...]
  }
]
```

### 🔹 Busca Completa (Full Search)

**Requisição:**
```http
GET /posts/fullsearch?text=viagem&minDate=20/03/2018&maxDate=25/03/2018
```

---

## 🧠 Decisões de Design

### Objetos Aninhados vs. Referências

| Cenário | Decisão | Motivo |
|---------|---------|--------|
| **Autor do post** | Aninhado (`AuthorDTO`) | Os dados do autor são lidos frequentemente junto com o post e raramente mudam. |
| **Comentários** | Aninhado | Comentários pertencem exclusivamente a um post e são acessados com ele. |
| **Posts do usuário** | Referência (`@DBRef`) | Um usuário pode ter muitos posts, e não queremos carregar todos eles toda vez que acessarmos o usuário. |

### 🔍 Consultas com Spring Data

- **Query Methods:** `findByTitleContainingIgnoreCase()`
- **@Query:** `@Query("{ 'title': { $regex: ?0, $options: 'i' } }")`
- **Consulta com vários critérios:** combinação de `$regex` e intervalo de datas com `$gte` e `$lte`.

---

## 📚 Referências

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data MongoDB](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [MongoDB Manual](https://docs.mongodb.com/manual/)
- [Curso: Programação Orientada a Objetos com Java](http://educandoweb.com.br) - Prof. Dr. Nelio Alves



---


