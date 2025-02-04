# **Sistema de Agendamento de Transferências Financeiras**

## **Visão Geral**
Este projeto é um sistema de **agendamento de transferências financeiras**, onde os usuários podem cadastrar transferências e visualizar o extrato de todas as transferências agendadas. O backend foi desenvolvido em **Spring Boot** e o frontend em **Vue.js**, seguindo uma arquitetura baseada em API REST.

---

## **Arquitetura do Projeto**
O projeto segue uma arquitetura **MVC (Model-View-Controller)**, dividindo as responsabilidades da seguinte forma:

- **Backend (Spring Boot)**
  - Fornece uma API REST para manipular os agendamentos.
  - Implementa a lógica de negócio e cálculo das taxas conforme a tabela especificada.
  - Utiliza um banco de dados **H2** (em memória) para armazenar os dados.
  
- **Frontend (Vue.js)**
  - Interface para interação com o sistema.
  - Consome a API REST para cadastrar e listar transferências.
  - Exibe os dados em uma tabela de extrato.


---

## **Tecnologias Utilizadas**
### **Backend:**
- **Linguagem:** Java 17
- **Framework:** Spring Boot 3.1.2
- **Banco de Dados:** H2 Database (em memória)
- **Dependências:**
  - `spring-boot-starter-web` (API REST)
  - `spring-boot-starter-data-jpa` (Persistência com Hibernate)
  - `spring-boot-starter-validation` (Validação de dados)
  - `lombok` (Redução de boilerplate)
  
### **Frontend:**
- **Linguagem:** JavaScript (Vue.js 3)
- **Gerenciador de Pacotes:** npm
- **Bibliotecas:**
  - `axios` (Consumo de API REST)
  - `Vue CLI` para desenvolvimento

---

## **Instruções para Rodar o Projeto**

### **1️⃣ Clonar o Repositório**
```sh
git clone https://github.com/seu-usuario/transferencias-financeiras.git
cd transferencias-financeiras
```

### **2️⃣ Subindo o Backend (Spring Boot)**
1. Acesse a pasta do backend:
   ```sh
   cd demo
   ```
2. Compile e rode a aplicação:
   ```sh
   mvn clean package
   mvn spring-boot:run
   ```
3. O backend estará rodando em: `http://localhost:8080`

4. Acesse o **H2 Console** (opcional, para visualizar o banco de dados):
   - URL: `http://localhost:8080/h2-console`
   - **JDBC URL:** `jdbc:h2:mem:transferencias`
   - **Usuário:** `sa`
   - **Senha:** (deixe em branco)

### **3️⃣ Subindo o Frontend (Vue.js)**
1. Acesse a pasta do frontend:
   ```sh
   cd frontend
   ```
2. Instale as dependências:
   ```sh
   npm install
   ```
3. Rode o servidor de desenvolvimento:
   ```sh
   npm run serve
   ```
4. O frontend estará acessível em: `http://localhost:8081`

---

## **Uso da API**

### **1️⃣ Criar uma Transferência (POST)**
```sh
curl -X POST http://localhost:8080/api/transferencias \
-H "Content-Type: application/json" \
-d '{
  "contaOrigem": "1234567890",
  "contaDestino": "0987654321",
  "valor": 1000,
  "dataTransferencia": "2025-02-05",
  "dataAgendamento": "2025-02-04"
}'
```

### **2️⃣ Listar Transferências (GET)**
```sh
curl -X GET http://localhost:8080/api/transferencias
```

Caso haja erros (ex: data de transferência maior que 50 dias), a API retorna uma mensagem de erro adequado.

---

## **Decisões Arquiteturais**
1. **Separar Backend e Frontend**: Mantemos os serviços desacoplados para facilitar manutenção e escalabilidade.
2. **Banco H2 em Memória**: Utilizado para facilitar o desenvolvimento e os testes, sem necessidade de configurar um banco externo.
3. **CORS Configurado**: O Spring Boot permite chamadas do Vue.js (`http://localhost:8081`) através da classe `CorsConfig.java`.
4. **API RESTful**: O backend segue os padrões REST, tornando a API simples de consumir e escalável.

---

## **Conclusão**
O projeto atende aos requisitos propostos, permitindo o agendamento de transferências, cálculo automático de taxas conforme a data escolhida, e exibição do extrato de transferências.

🚀 **Agora é só rodar e testar!**

