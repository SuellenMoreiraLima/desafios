# 🏦 Desafio Santander - API Consulta CEP

Sistema completo de consulta de CEP com mock de API externa, banco de dados e arquitetura Docker.

## 🚀 **Execução Rápida (Docker)**

### **Pré-requisitos:**
- **Docker Desktop** instalado ([Download aqui](https://www.docker.com/products/docker-desktop/))
- **Git** para clonar o repositório

### **Passos para executar:**

```bash
# 1. Clonar o repositório
git clone https://github.com/SuellenMoreiraLima/desafios.git
cd desafio-nava

# 2. Iniciar ambiente completo
docker compose up --build -d

# 3. Aguardar inicialização (30-60 segundos)

# 4. Testar aplicação
curl http://localhost:8080/api/cep/01310100
```

### **🔗 Acesso aos serviços:**
- **📱 API Principal:** http://localhost:8080/api/cep/{cep}
- **🎭 WireMock (Mock):** http://localhost:8089/ws/{cep}/json/
- **🗄️ MySQL (Adminer):** http://localhost:8082
- **📊 Health Check:** http://localhost:8080/actuator/health

**Credenciais MySQL (Adminer):**
- Servidor: `mysql` | Usuário: `cepuser` | Senha: `ceppass123` | Base: `desafio_cep`

---

## 📋 Descrição do Projeto

Sistema de consulta de CEP desenvolvido como desafio técnico para o **Banco Santander**. A aplicação permite consultar informações de endereço através de CEP, utilizando uma API externa mocada, e registra logs de todas as consultas realizadas em banco de dados.

## 🎯 Objetivos Atendidos

✅ **API REST** para consulta de CEP  
✅ **Integração com API externa** (ViaCEP mocada com Wiremock)  
✅ **Log das consultas** em banco de dados com timestamp  
✅ **Aplicação dos princípios SOLID**  
✅ **Docker** para banco de dados e mocks  
✅ **Banco de dados H2** (em memória) para desenvolvimento  
✅ **Documentação Swagger/OpenAPI**  
✅ **Testes unitários** com Wiremock  

## 🏗️ Arquitetura da Solução

```
┌─────────────────────────────────────────────────────────────┐
│                    DESENHO DA SOLUÇÃO                       │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  [Cliente] ──HTTP──> [CepController]                        │
│                           │                                 │
│                           ▼                                 │
│                    [CepService]                             │
│                           │                                 │
│                    ┌──────┴──────┐                          │
│                    ▼             ▼                          │
│            [CepApiService]  [CepLogService]                 │
│                    │             │                          │
│                    ▼             ▼                          │
│              [Wiremock API]  [H2 Database]                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 🔧 Tecnologias Utilizadas

- **Java 17** ☕
- **Spring Boot 3.5.6** 🍃
- **Spring Data JPA** 📊
- **H2 Database** (desenvolvimento) / **PostgreSQL** (produção) 🗄️
- **Wiremock** (mock API externa) 🎭
- **Docker & Docker Compose** 🐳
- **Swagger/OpenAPI** 📚
- **Maven** 📦

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- Docker & Docker Compose (opcional)

### 1️⃣ Executar Localmente (Desenvolvimento)

```bash
# 1. Compilar o projeto
mvn clean compile

# 2. Executar testes
mvn test

# 3. Iniciar a aplicação
mvn spring-boot:run
```

### 2️⃣ Executar com Docker (Recomendado)

```bash
# 1. Compilar o projeto
mvn clean package -DskipTests

# 2. Subir todos os serviços
docker-compose up -d

# 3. Verificar se está rodando
docker-compose ps
```

## 📖 Endpoints da API

### Base URL: `http://localhost:8080/api/cep`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/cep/{cep}` | Consultar CEP |
| `GET` | `/api/cep/logs` | Listar todos os logs |
| `GET` | `/api/cep/logs/{cep}` | Listar logs por CEP |

### 📝 Exemplos de Uso

#### Consultar CEP
```bash
# CEP válido (Avenida Paulista)
curl -X GET "http://localhost:8080/api/cep/01310100"

# CEP válido (Centro RJ)
curl -X GET "http://localhost:8080/api/cep/20040020"

# CEP inválido
curl -X GET "http://localhost:8080/api/cep/00000000"
```

#### Consultar Logs
```bash
# Todos os logs
curl -X GET "http://localhost:8080/api/cep/logs"

# Logs de um CEP específico
curl -X GET "http://localhost:8080/api/cep/logs/01310100"
```

## 🔍 Monitoramento e Debug

### Swagger UI
- **URL**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

### H2 Console (Desenvolvimento)
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

### Wiremock Admin
- **URL**: http://localhost:8089/__admin
- **Mappings**: http://localhost:8089/__admin/mappings

## 🏛️ Princípios SOLID Aplicados

### 1. **Single Responsibility Principle (SRP)**
- `CepController`: Responsável apenas pela camada de apresentação
- `CepService`: Coordena a lógica de negócio
- `CepApiService`: Consulta API externa
- `CepLogService`: Gerencia logs

### 2. **Open/Closed Principle (OCP)**
- Interfaces permitem extensão sem modificação
- Novos provedores de CEP podem ser adicionados facilmente

### 3. **Liskov Substitution Principle (LSP)**
- Implementações podem ser substituídas pelas interfaces

### 4. **Interface Segregation Principle (ISP)**
- Interfaces específicas para cada responsabilidade

### 5. **Dependency Inversion Principle (DIP)**
- Dependência de abstrações, não de implementações concretas
- Injeção de dependência via Spring

## 🧪 Testes

### Executar Testes
```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=ViaCepApiServiceTest
```

### Cobertura
- Testes unitários com Wiremock
- Testes de integração com Spring Boot Test
- Mock de APIs externas

## 🚢 Deploy AWS (Preparado)

### Estrutura para AWS
```
AWS ECS + RDS PostgreSQL + Application Load Balancer
```

### Variáveis de Ambiente (Produção)
```bash
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://rds-endpoint:5432/desafio_cep
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=senha_secreta
APP_VIACEP_URL=https://viacep.com.br
```

## 📊 Estrutura do Banco de Dados

### Tabela: `cep_logs`
```sql
CREATE TABLE cep_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(10) NOT NULL,
    consulta_timestamp TIMESTAMP NOT NULL,
    logradouro VARCHAR(255),
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    localidade VARCHAR(255),
    uf VARCHAR(2),
    ibge VARCHAR(10),
    gia VARCHAR(10),
    ddd VARCHAR(3),
    siafi VARCHAR(10),
    sucesso BOOLEAN,
    erro_mensagem TEXT
);
```

## � **Comandos Docker Úteis**

### **Gerenciamento básico:**
```bash
# Ver status dos containers
docker compose ps

# Ver logs de todos os serviços
docker compose logs -f

# Ver logs de um serviço específico
docker compose logs -f app
docker compose logs -f wiremock
docker compose logs -f mysql

# Parar todos os serviços
docker compose down

# Parar e remover volumes (ATENÇÃO: apaga dados do MySQL)
docker compose down -v
```

### **Rebuild e troubleshooting:**
```bash
# Rebuild apenas a aplicação
docker compose up --build app -d

# Rebuild tudo do zero
docker compose down
docker compose up --build --force-recreate -d

# Entrar no container da aplicação
docker exec -it desafio-app sh

# Entrar no MySQL
docker exec -it desafio-mysql mysql -u cepuser -p desafio_cep

# Limpar cache Docker (se houver problemas)
docker system prune -a
```

### **🔧 Troubleshooting comum:**

| Problema | Solução |
|----------|---------|
| **Porta já em uso** | Parar serviços locais nas portas 8080, 8089, 3306, 8082 |
| **MySQL não conecta** | Aguardar 30-60s para inicialização completa |
| **App não inicia** | Verificar logs: `docker compose logs app` |
| **WireMock não responde** | Testar diretamente: `curl http://localhost:8089/ws/01310100/json/` |

## �📈 Próximos Passos (Melhorias)

- [ ] Cache Redis para consultas frequentes
- [ ] Rate limiting por IP
- [ ] Métricas com Micrometer + Prometheus
- [ ] Circuit Breaker com Resilience4j
- [ ] Autenticação JWT
- [ ] Logs estruturados (JSON)
- [ ] Pipeline CI/CD

## 👨‍💻 Desenvolvedor

**Suellen Moreira Lima**  
📧 Email: developer.suellen.lima@gmail.com  
🐙 GitHub:SuellenMoreiraLima

---

## 🎯 Apresentação do Desafio

### ⏱️ Cronograma da Apresentação (15 min)

1. **Explicação da Solução** (5 min)
   - Arquitetura e desenho
   - Tecnologias escolhidas
   - Princípios SOLID aplicados

2. **Demonstração do Código** (5 min)
   - Estrutura do projeto
   - Principais classes e responsabilidades
   - Testes implementados

3. **Execução da Aplicação** (5 min)
   - Inicialização via Docker
   - Demonstração das operações via Swagger
   - Consulta aos logs no banco H2

### 🎪 Scripts de Demonstração

```bash
# 1. Subir a aplicação
docker-compose up -d

# 2. Consultar CEPs de exemplo
curl -X GET "http://localhost:8080/api/cep/01310100"
curl -X GET "http://localhost:8080/api/cep/20040020"
curl -X GET "http://localhost:8080/api/cep/00000000"

# 3. Verificar logs
curl -X GET "http://localhost:8080/api/cep/logs"

# 4. Acessar Swagger UI
# http://localhost:8080/swagger-ui.html

# 5. Verificar H2 Console
# http://localhost:8080/h2-console
```

---

**🎉 Desafio desenvolvido com dedicação e seguindo as melhores práticas de desenvolvimento!**
