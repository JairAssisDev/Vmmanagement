# Backend - Gestão e Monitoramento de Máquinas Virtuais

Este é o backend da aplicação para gestão e monitoramento de máquinas virtuais, desenvolvido como parte do desafio técnico para a vaga de Desenvolvedor Júnior no time Ustore/Claro.

---

## Funcionalidades

- **Autenticação e Segurança:** Implementado com Spring Security e JWT.
- **API REST:** Endpoints para:
  - Listar máquinas virtuais.
  - Criar novas VMs.
  - Iniciar, pausar, parar e excluir VMs.
- **Regras de Negócio:** Limite de 5 VMs por usuário.
- **Documentação:** API documentada com Swagger.
- **Contêinerização:** Configurado para Docker e Docker Compose.

---

## Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Framework:** Spring Boot
- **Segurança:** Spring Security e JWT
- **API:** RESTful
- **Documentação:** Swagger
- **Contêineres:** Docker e Docker Compose

---

## ▶️ Como Rodar o Projeto

### Pré-requisitos

- **Java 17** ou superior instalado.
- **Maven** (ou Gradle, conforme configuração do projeto).
- **Docker** e **Docker Compose** (opcional, para execução em contêiner).
- IDE de sua preferência (Eclipse, IntelliJ, VSCode, etc.).

### Execução Local

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/JairAssisDev/Vmmanagement.git

2. **Rode o Docker-compose**
   ```bash
   docker-compose up
3. **Abra a documentação**
   ```bash
   http://localhost:8080/swagger-ui/index.html
