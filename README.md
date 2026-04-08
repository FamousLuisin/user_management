# 📊 Sistema de Gerenciamento de Clientes

Aplicação fullstack para gerenciamento de clientes, composta por:

- **Frontend:** React (Vite + TypeScript)
- **Backend:** Java 8 + Spring Boot
- **Banco de Dados:** Docker Compose


## 🚀 Funcionalidades

- Autenticação com JWT
- Controle de acesso por perfil:
  - **ADMIN** → acesso total
  - **COMMON** → apenas visualização
- Cadastro, listagem e gerenciamento de clientes
- Scroll infinito na listagem
- Integração parcial entre frontend e API


## 🔐 Usuários de Teste

| Tipo           | Username | Senha      | Permissão                      |
|----------------|----------|------------|--------------------------------|
| Admin          | admin    | admin      | Acesso total                   |
| Comum          | common   | common     | Apenas visualização dos dados  |

---

## 🧱 Arquitetura

O sistema segue uma arquitetura **cliente-servidor desacoplada**, dividida em três camadas principais:

- **Frontend (client):** Interface do usuário construída em React
- **Backend (server):** API REST responsável pela lógica de negócio
- **Banco de Dados (docker):** Persistência gerenciada via Docker Compose


## ⚙️ Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- Node.js (>= 18)
- Java 8
- Maven
- Docker + Docker Compose


## 🐳 Subindo o Banco de Dados

Va até a pasta api onde esta o `compose.yml`:

```bash
docker-compose up -d
```

Depois verifique se ele esta rodando:

```bash
docker ps
```

## ☕ Rodando a api
Na mesma pasta api do docker execute o comando crie o .env seguindo o .env.example e depois rode:

```bash
chmod +x run.sh
./run.sh
```

Se tudo der certo a api vai esta disponivel em http://localhost:8080 e o swagger em http://localhost:8080/swagger-ui/index.html

E caso deseje pode fazer os teste da aplicação usando o seguinte comando

```bash
./mvnw test
```

## ⚛️ Rodando o client
Agora navegue até a pasta do client e execute os comandos, e certifique de criar o .env seguindo o .env.example

```bash
npm install
npm run dev
```
Se tudo der certo a api vai esta disponivel em http://localhost:5173


## 📌 Regras do Sistema

Um cliente deve conter:

- Nome
- CPF
- Tipo (Celular ou Residencial)
- Outros campos definidos na API

### Validações importantes:

- CPF válido (com ou sem máscara)
- Campos obrigatórios preenchidos
- Controle de acesso por perfil


## 🧪 Testando a Aplicação

1. Suba o banco com Docker  
2. Inicie a API  
3. Inicie o frontend  
4. Acesse o sistema no navegador  
5. Faça login com um dos usuários de teste  


## 🛠️ Tecnologias Utilizadas

### Backend
- Java 8
- Spring Boot
- Spring Security
- JWT

### Frontend
- React
- TypeScript
- Vite
- TailwindCSS
- Shadcn/UI

### Infra
- Docker
- Docker Compose
