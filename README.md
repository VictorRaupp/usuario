# 👤 Serviço de Usuários

Microsserviço responsável pelo gerenciamento de usuários da aplicação de agendamento de tarefas.

O serviço foi desenvolvido utilizando **Java e Spring Boot**, disponibilizando uma API REST para cadastro, autenticação e gerenciamento dos dados dos usuários.

---

## 🎯 Objetivo

Este microsserviço faz parte de uma arquitetura baseada em **microsserviços** e tem como responsabilidade centralizar as operações relacionadas aos usuários da aplicação.

Entre suas principais responsabilidades estão:

- Cadastro de usuários
- Autenticação
- Geração de tokens JWT
- Consulta de usuários
- Atualização de dados
- Exclusão de usuários
- Cadastro e gerenciamento de endereços
- Cadastro e gerenciamento de telefones

---

## 🚀 Funcionalidades

### 👤 Usuários

- Cadastro de novos usuários
- Consulta de usuário por e-mail
- Atualização de dados do usuário
- Exclusão de usuário

### 🔐 Autenticação

O serviço utiliza **Spring Security** para controle de acesso e **JWT (JSON Web Token)** para autenticação.

O fluxo de autenticação funciona da seguinte forma:

1. O usuário envia e-mail e senha para o endpoint de login.
2. O sistema busca o usuário no banco de dados.
3. A senha é validada utilizando `BCryptPasswordEncoder`.
4. Após a autenticação, um token JWT é gerado.
5. O token deve ser utilizado para acessar os endpoints protegidos.

### 📍 Endereços

O usuário pode cadastrar, atualizar e consultar seus endereços associados à sua conta.

### ☎️ Telefones

O usuário também pode cadastrar e atualizar telefones vinculados à sua conta.

---

## 🏗️ Arquitetura

O serviço faz parte do projeto **Agendador de Tarefas - Microsserviços**.

A arquitetura geral da aplicação é:

```text
                    ┌───────────────┐
                    │    Cliente    │
                    └───────┬───────┘
                            │
                            ▼
                    ┌───────────────┐
                    │      BFF      │
                    │    :8084      │
                    └───────┬───────┘
                            │
                            ▼
                    ┌───────────────┐
                    │    Usuário    │
                    │    :8080      │
                    └───────┬───────┘
                            │
                            ▼
                    ┌───────────────┐
                    │  PostgreSQL   │
                    └───────────────┘
```

O microsserviço de usuários é responsável pela comunicação com seu próprio banco de dados PostgreSQL.

---

## 🛠️ Tecnologias utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Security**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **JWT**
- **BCrypt**
- **Lombok**
- **MapStruct**
- **Gradle**
- **Docker**
- **Docker Compose**
- **Swagger / OpenAPI**

---

## 🔒 Segurança

As senhas dos usuários não são armazenadas diretamente no banco de dados.

O sistema utiliza:

```text
BCryptPasswordEncoder
```

para realizar o hash das senhas antes do armazenamento.

A autenticação utiliza tokens **JWT**, permitindo que a API funcione de maneira stateless.

Os endpoints protegidos exigem um token válido no header:

```http
Authorization: Bearer <token>
```

As credenciais e configurações sensíveis são obtidas através de **variáveis de ambiente**, evitando que informações confidenciais sejam armazenadas diretamente no código-fonte.

---

## 🗄️ Banco de dados

O projeto utiliza **PostgreSQL** para persistência dos dados.

As principais informações armazenadas estão relacionadas a:

- Usuários
- Endereços
- Telefones

A aplicação utiliza **Spring Data JPA** e **Hibernate** para realizar o mapeamento entre as entidades Java e o banco de dados.

---

## 📡 Endpoints

### Cadastro

```http
POST /usuario
```

Cadastra um novo usuário.

Exemplo:

```json
{
  "nome": "Victor",
  "email": "victor@email.com",
  "senha": "1234"
}
```

---

### Login

```http
POST /usuario/login
```

Realiza a autenticação do usuário e retorna um token JWT.

Exemplo:

```json
{
  "email": "victor@email.com",
  "senha": "1234"
}
```

---

### Buscar usuário

```http
GET /usuario?email={email}
```

Retorna os dados do usuário informado.

🔐 Requer autenticação JWT.

---

### Excluir usuário

```http
DELETE /usuario/{email}
```

Exclui um usuário pelo e-mail.

🔐 Requer autenticação JWT.

---

### Atualizar usuário

```http
PUT /usuario
```

Atualiza os dados do usuário autenticado.

🔐 Requer autenticação JWT.

---

### Cadastrar endereço

```http
POST /usuario/endereco
```

Adiciona um endereço ao usuário autenticado.

🔐 Requer autenticação JWT.

---

### Cadastrar telefone

```http
POST /usuario/telefone
```

Adiciona um telefone ao usuário autenticado.

🔐 Requer autenticação JWT.

---

### Atualizar endereço

```http
PUT /usuario/endereco?id={id}
```

Atualiza um endereço existente.

🔐 Requer autenticação JWT.

---

### Atualizar telefone

```http
PUT /usuario/telefone?id={id}
```

Atualiza um telefone existente.

🔐 Requer autenticação JWT.

---

## 📚 Documentação da API

A API possui documentação através do **Swagger / OpenAPI**.

Após iniciar a aplicação, a interface do Swagger pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

A documentação da API permite visualizar e testar os endpoints disponíveis.

---

## ⚙️ Configuração

As informações sensíveis utilizadas pela aplicação devem ser configuradas através de variáveis de ambiente.

Exemplo:

```env
POSTGRES_DB=db_usuario
POSTGRES_USER=postgres
POSTGRES_PASSWORD=sua_senha
JWT_SECRET=sua_chave_jwt
```

O projeto possui um arquivo:

```text
.env.example
```

como referência para configuração do ambiente.

> O arquivo `.env` não deve ser versionado no Git.

---

## 🐳 Executando com Docker

Com o Docker instalado e em execução, utilize:

```bash
docker compose up --build
```

A aplicação será disponibilizada na porta:

```text
8080
```

E o PostgreSQL na porta:

```text
5432
```

Para parar os containers:

```bash
docker compose down
```

---

## 💻 Executando localmente

Também é possível executar a aplicação diretamente pela IDE ou utilizando o Gradle.

Para gerar o projeto:

```bash
./gradlew build
```

No Windows:

```powershell
.\gradlew build
```

Depois, a aplicação pode ser executada pela classe principal:

```text
UsuarioApplication
```

É necessário possuir um banco PostgreSQL configurado e definir as variáveis de ambiente necessárias.

---

## 📂 Estrutura do projeto

A aplicação segue uma organização baseada na separação de responsabilidades:

```text
src/
└── main/
    └── java/
        └── com.victor.usuario/
            ├── business/
            │   ├── dto/
            │   └── service/
            │
            ├── infrastructure/
            │   ├── entity/
            │   ├── repository/
            │   └── security/
            │
            └── controller/
```

A separação facilita a manutenção e evolução do microsserviço.

---

## 🔗 Projeto completo

Este microsserviço faz parte do projeto:

**Agendador de Tarefas - Arquitetura de Microsserviços**

O sistema é composto por diferentes serviços responsáveis por funcionalidades específicas da aplicação.

---

## 📌 Próximos passos

Algumas melhorias que podem ser implementadas futuramente:

- [ ] Melhorar cobertura de testes automatizados
- [ ] Adicionar testes de integração
- [ ] Evoluir documentação da API
- [ ] Melhorar tratamento global de exceções
- [ ] Implementar observabilidade e logs estruturados
- [ ] Evoluir pipeline de CI/CD
- [ ] Melhorar configuração para ambientes de desenvolvimento e produção

---

## 👨‍💻 Autor

**Victor Raupp**

Estudante de Engenharia de Software e desenvolvedor em formação com foco em **Java, Spring Boot, APIs REST, bancos de dados e arquitetura de microsserviços**.