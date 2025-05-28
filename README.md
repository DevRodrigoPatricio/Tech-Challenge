
# Tech Challenge

Este repositório contém o projeto desenvolvido por uma equipe de colaboradores como parte de um desafio técnico. O objetivo é criar um sistema de autoatendimento para fast food com foco em boas práticas de engenharia de software, arquitetura hexagonal e integração de serviços como Mercado Pago.

## 👨‍💻 Equipe


| <img src="https://avatars.githubusercontent.com/u/84483277?v=4" width=115 > | <img src="https://avatars.githubusercontent.com/u/14063148?v=4" width=115 >| <img src="https://avatars.githubusercontent.com/u/88734065?v=4" width=115 >| <img src="https://avatars.githubusercontent.com/u/105437684?v=4" width=115 > 
|---|---|---|---|
| [Rodrigo Pereira](https://github.com/DevRodrigoPatricio) | [Leyner Bueno ](https://github.com/leynerbueno) | [Luciana Soares](https://github.com/lucianaTSoares) | [Breno Dias Balassoni](https://github.com/Breno101069)

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

