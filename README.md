# 🛒 API de E-commerce com Spring Boot

Este projeto é o backend de uma API RESTful completa para um sistema de e-commerce, construída com Spring Boot. A API foi desenvolvida passo a passo, cobrindo conceitos essenciais do desenvolvimento backend moderno, desde a modelagem de dados e segurança até otimizações de performance e containerização.

---

## ✨ Features

* **Autenticação e Autorização:** Sistema de segurança robusto com Spring Security e JWT (JSON Web Tokens).
* **Controle de Acesso por Papel (Roles):** Distinção entre usuários `ADMIN` e `CUSTOMER`, com permissões específicas para cada um.
* **CRUD Completo:** Operações de Criar, Ler, Atualizar e Deletar para as entidades principais (Produtos, Clientes).
* **Sistema de Pedidos:** Lógica de negócio para criação de pedidos, validação de estoque e cálculo automático do valor total.
* **Busca Avançada:** Endpoint de busca de produtos com suporte a paginação e ordenação.
* **Soft Delete:** Implementação de exclusão suave para produtos, garantindo a integridade dos dados históricos de pedidos.
* **Otimização de Performance:** Uso de cache in-memory com Caffeine para acelerar consultas frequentes.
* **Tratamento de Erros Global:** Respostas de erro padronizadas e amigáveis para uma melhor integração com o front-end.
* **Migrations de Banco de Dados:** Gerenciamento do schema do banco de dados de forma versionada e segura com Flyway.
* **Operações Assíncronas:** Execução de tarefas demoradas (como envio de notificações) em segundo plano para não impactar a experiência do usuário.
* **Documentação de API:** Documentação interativa e automática com Springdoc OpenAPI (Swagger UI).
* **Containerização:** Aplicação e banco de dados totalmente containerizados com Docker e Docker Compose para facilitar o setup e o deploy.

---

## 🛠️ Stack Tecnológica

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Segurança:** Spring Security 6
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL
* **Migrations:** Flyway
* **Mapeamento de Objetos:** MapStruct
* **Cache:** Spring Cache com Caffeine
* **Build Tool:** Maven
* **Testes:** JUnit 5 & Mockito
* **Documentação:** Springdoc OpenAPI 3 (Swagger UI)
* **Containerização:** Docker & Docker Compose

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
* [Java (JDK) 21](https://www.oracle.com/java/technologies/downloads/#java21) ou superior
* [Maven 3.8+](https://maven.apache.org/download.cgi)
* [Docker](https://www.docker.com/products/docker-desktop/) e [Docker Compose](https://docs.docker.com/compose/install/)

### 1. Clone o Repositório
```bash
git clone https://github.com/andrezktt/spring_ecommerce_api
cd spring_ecommerce_api
```

### 2. Configure o Ambiente

A forma mais fácil e recomendada de rodar o projeto é via **Docker**, que cuida de tudo para você. Apenas se certifique de que o **Docker Desktop** esteja em execução.

Se você preferir rodar localmente sem Docker, precisará:

1. Ter uma instância do **PostgreSQL** rodando.

2. Criar um banco de dados chamado `ecommerce_db`.

3. Configurar as credenciais no arquivo `application.properties`. 

```
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres
```

### 3. Execute a Aplicação
**Opção A:** Com Docker (Recomendado)

Na raiz do projeto, execute o comando:

```bash
docker-compose up --build
```
**A aplicação estará disponível em `http://localhost:8080`.*

**Opção B:** Localmente via Maven

Na raiz do projeto, execute o comando:

```bash
mvn spring-boot:run
```

---

## 📖 Uso da API
Após iniciar a aplicação, a documentação completa e interativa da API estará disponível no Swagger UI:

http://localhost:8080/swagger-ui.html

### Fluxo de Autenticação

1. **Cadastre um novo usuário:** Envie uma requisição `POST` para `/api/customers` com seus dados. Este usuário terá o papel `CUSTOMER`.
2. **Faça o login:** Envie uma `POST` para `/api/auth/login` com o email e a senha do usuário cadastrado para obter um token JWT.
3. **Autorize suas requisições:** Para acessar endpoints protegidos, inclua o token no cabeçalho (header) `Authorization` com o prefixo `Bearer `. 
   - **Exemplo:** `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...`

### Usuário Administrador Padrão

Quando a aplicação é iniciada com um banco de dados vazio, um usuário **ADMIN** padrão é criado para facilitar os testes:
- **Email:** ```admin@example.com```
- **Senha:** ```admin123```

### Principais Endpoints

| Método   | Endpoint                  | Proteção    | Descrição                                            |
|----------|---------------------------|-------------|------------------------------------------------------|
| `POST`   | `/api/auth/login`         | Pública     | Autentica um usuário e retorna um token JWT.         |
| `POST`   | `/api/customers`          | Pública     | Cadastra um novo cliente (com papel CUSTOMER).       |
| `GET`    | `/api/customers`          | ADMIN       | Lista todos os clientes.                             |
| `GET`    | `/api/products`           | Autenticado | Lista todos os produtos (com paginação e ordenação). |
| `GET`    | `/api/products/{id}`      | Autenticado | Busca um produto por ID.                             |
| `POST`   | `/api/products`           | ADMIN       | Cria um novo produto.                                |
| `PUT`    | `/api/products/{id}`      | ADMIN       | Atualiza um produto existente.                       |
| `DELETE` | `/api/products/{id}`      | ADMIN       | Desativa um produto (Soft Delete).                   |
| `GET`    | `/api/products/search`    | Autenticado | Busca produtos por nome.                             |
| `POST`   | `/api/orders`             | CUSTOMER    | Cria um novo pedido.                                 |
| `PATCH`  | `/api/orders/{id}/status` | ADMIN       | Atualiza o status de um pedido.                      |

---

## 📝 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

Feito por [André Zicatti](https://github.com/andrezktt).