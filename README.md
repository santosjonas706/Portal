# PortalNoticia - Guia de Execução

Este projeto é composto por dois módulos principais:
- **Back**: Backend em Java Spring Boot
- **PortalNoticia**: Frontend em Angular

---

## Backend (Spring Boot)

### Como funciona
- API REST para cadastro, edição, listagem e remoção de notícias, categorias, autores e publicidades.
- Utiliza Spring Boot, Maven e banco de dados configurado em `application.properties`.

### Como rodar
1. **Pré-requisitos:**
   - Java 17 ou superior
   - Maven 3.8+

2. **Configuração:**
   - Edite o arquivo `Back/src/main/resources/application.properties` para ajustar as configurações do banco de dados, se necessário.

3. **Build e execução:**
   - No terminal, acesse a pasta `Back`:
     ```sh
     cd Back
     mvn clean install
     mvn spring-boot:run
     ```
   - A API estará disponível em: `http://localhost:8080`

---

## Frontend (Angular)

### Como funciona
- Interface web para interação com as APIs do backend.
- Permite login, cadastro, edição e visualização de notícias, categorias e publicidades.

### Como rodar
1. **Pré-requisitos:**
   - Node.js 18+
   - npm 9+
   - Angular CLI (instale com `npm install -g @angular/cli`)

2. **Instalação das dependências:**
   - No terminal, acesse a pasta `PortalNoticia`:
     ```sh
     cd PortalNoticia
     npm install
     ```

3. **Execução do frontend:**
   - Ainda na pasta `PortalNoticia`, execute:
     ```sh
     npm start
     ```
   - O app estará disponível em: `http://localhost:4200`

---

## Observações
- O frontend espera que o backend esteja rodando em `http://localhost:8080`.
- Para ambiente de produção, ajuste as URLs de API conforme necessário.
- Em caso de dúvidas, consulte os arquivos de configuração ou abra uma issue.

---

**Desenvolvido por:**
- Equipe PortalNoticia
