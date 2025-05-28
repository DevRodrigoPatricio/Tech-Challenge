
# Tech Challenge

Este repositório contém o projeto desenvolvido por uma equipe de colaboradores como parte de um desafio técnico. O objetivo é criar um sistema de autoatendimento para fast food com foco em boas práticas de engenharia de software, arquitetura hexagonal e integração de serviços como Mercado Pago.

## 👨‍💻 Equipe

Este projeto foi desenvolvido em equipe, com contribuições de vários desenvolvedores. Para visualizar todos os colaboradores, acesse:  
[https://github.com/DevRodrigoPatricio/Tech-Challenge/graphs/contributors](https://github.com/DevRodrigoPatricio/Tech-Challenge/graphs/contributors)

## 🚀 Objetivo do Projeto

Criar uma aplicação robusta e escalável para gerenciamento de pedidos em um sistema de autoatendimento de lanchonete, utilizando uma arquitetura moderna e tecnologias amplamente utilizadas no mercado.

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Arquitetura Hexagonal (Ports & Adapters)**
- **Docker & Docker Compose**
- **JPA / Hibernate**
- **Banco de Dados** ( MySQL)
- **Swagger para documentação de APIs**
- **Integração com Mercado Pago (QR Code )**

## 📦 Estrutura do Projeto

```
Tech-Challenge/
├── domain/               # Camada de domínio (regras de negócio)
├── application/          # Casos de uso
├── adapters/             # Entradas e saídas (controllers, gateways, clients)
├── config/               # Configurações de aplicação
├── infrastructure/       # Integrações com frameworks, banco e serviços externos
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

## 🧪 Como Executar

### Pré-requisitos

- Java 17
- Docker e Docker Compose
- Maven

### Passos

```bash
# Clone o repositório
git clone https://github.com/DevRodrigoPatricio/Tech-Challenge.git
cd Tech-Challenge

# Suba os containers
docker-compose up --build
```

A aplicação estará disponível em `http://localhost:8080` (ou outra porta definida).

## 🔍 Documentação da API

Se estiver habilitado, acesse o Swagger UI:  
`http://localhost:8080/swagger-ui.html`

## ✅ Funcionalidades

- Cadastro e gerenciamento de clientes
- Menu de produtos
- Criação e acompanhamento de pedidos
- Integração com Mercado Pago para pagamentos via QRCode
- Consulta de status de pagamento ( polling)
- Sistema modular com arquitetura hexagonal

