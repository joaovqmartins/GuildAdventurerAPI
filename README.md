# 🏹 Registro da Guilda de Aventureiros

Este projeto é uma **API REST monolítica desenvolvida com Spring Boot**, com foco em boas práticas de arquitetura, validação de regras de negócio e consistência de dados.

A aplicação simula um sistema real de gerenciamento de domínio, aplicando conceitos importantes como:

* Arquitetura em camadas (Controller, Service, Repository)
* Validação robusta de dados
* Paginação e filtros
* Tratamento padronizado de erros
* Composição entre entidades
* Uso de boas práticas REST
* Documentação interativa com Swagger
* Redução de boilerplate com Lombok

A persistência é **simulada em memória**, permitindo foco total na modelagem e regras de negócio sem dependência de banco de dados.

---

# 📜 Contexto do domínio

Durante séculos, a Guilda de Aventureiros manteve seus registros de forma desorganizada — pergaminhos espalhados, nomes incompletos e vínculos esquecidos.

Com o aumento das expedições e novas ameaças surgindo, tornou-se necessário um sistema confiável.

Este projeto implementa o **Registro Oficial da Guilda**, garantindo consistência, rastreabilidade e regras bem definidas para o gerenciamento de aventureiros.

---

# 📌 Tecnologias utilizadas

* Java 21+
* Spring Boot
* Lombok
* Swagger (OpenAPI)
* Maven

---

# 🚀 Como executar o projeto

## Pré-requisitos

* Java 17+
* Maven instalado

## Passos

```bash
git clone https://github.com/joaovqmartins/GuildAdventurerAPI.git
cd aplicacaoMonoliticaSpring
mvn spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

# 📖 Documentação da API (Swagger)

Após iniciar o projeto, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

Você poderá:

* Visualizar todos os endpoints
* Testar requisições diretamente
* Ver exemplos de request/response

---

# 🧠 Regras de negócio

## Aventureiro

Um aventureiro possui:

* `id` (gerado automaticamente)
* `nome` (obrigatório)
* `classe` (obrigatória)
* `nivel` (>= 1)
* `ativo` (true por padrão)
* `companheiro` (opcional)

## Classes permitidas

* GUERREIRO
* MAGO
* ARQUEIRO
* CLERIGO
* LADINO

---

## 🐺 Companheiro (composição)

O companheiro:

* Existe apenas dentro de um aventureiro
* Não pode existir isoladamente
* Não pode ser compartilhado

### Campos

* `nome` (obrigatório)
* `especie` (obrigatória)
* `lealdade` (entre 0 e 100)

## Espécies permitidas

* LOBO
* CORUJA
* GOLEM
* DRAGAO_MINIATURA

---

# 📡 Endpoints

## ➕ Registrar aventureiro

**POST** `/aventureiros`

### Request

```json
{
  "nome": "Arthas",
  "classe": "GUERREIRO",
  "nivel": 10
}
```

### Regras

* ID gerado automaticamente
* Começa como ativo
* Não permite companheiro na criação

### Response

* `201 Created`

---

## 📄 Listar aventureiros

**GET** `/aventureiros`

### Filtros

* `classe`
* `ativo`
* `nivelMin`

### Paginação

* `page` (default: 0)
* `size` (default: 10)

### Restrições

* page >= 0
* size entre 1 e 50

### Headers retornados

```
X-Total-Count
X-Page
X-Size
X-Total-Pages
```

---

## 🔍 Buscar por ID

**GET** `/aventureiros/{id}`

Retorna todas as informações, incluindo o companheiro.

### Exemplo

```json
{
  "id": 1,
  "nome": "Arthas",
  "classe": "GUERREIRO",
  "nivel": 10,
  "ativo": true,
  "companheiro": {
    "nome": "Fenrir",
    "especie": "LOBO",
    "lealdade": 95
  }
}
```

---

## ✏️ Atualizar aventureiro

**PUT** `/aventureiros/{id}`

Permite alterar:

* nome
* classe
* nível

Não permite:

* id
* ativo
* companheiro

---

## ❌ Encerrar vínculo

**PATCH** `/aventureiros/{id}/inativar`

Define:

```
ativo = false
```

---

## 🔄 Recrutar novamente

**PATCH** `/aventureiros/{id}/reativar`

Define:

```
ativo = true
```

---

## 🐺 Companheiro

### Definir ou substituir

**PUT** `/aventureiros/{id}/companheiro`

```json
{
  "nome": "Luna",
  "especie": "CORUJA",
  "lealdade": 88
}
```

---

### Remover companheiro

**DELETE** `/aventureiros/{id}/companheiro`

---

# ⚠️ Padrão de erro

```json
{
  "mensagem": "Solicitação inválida",
  "detalhes": [
    "classe inválida",
    "nivel deve ser maior ou igual a 1"
  ]
}
```

## Tipos

* `400 Bad Request`
* `404 Not Found`

---

# 💾 Persistência

* Simulada com `ArrayList`
* Sem banco de dados
* Inicializa com pelo menos **100 registros**

---

# 🧰 Lombok

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
```

---

# 📊 Ordenação e Paginação

* Ordenação por ID crescente
* Paginação aplicada após filtros
* Página inexistente retorna lista vazia

---

# 🧭 Considerações finais

* API REST consistente e previsível
* Regras de negócio bem definidas
* Foco em arquitetura e domínio
* Fácil de testar via Swagger

---

Se quiser dar um toque final **nível recrutador (top GitHub)**, posso adicionar:

* badges (build, Java, Spring)
* gifs ou prints do Swagger
* descrição estilo LinkedIn/portfólio

Só falar 👍
