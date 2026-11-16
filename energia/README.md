# Energia API

API RESTful em Spring Boot para gestão de consumo de energia elétrica: cadastro de **unidades consumidoras** e registro de **leituras de consumo (kWh)**. Projeto da Global Solution 2026.2 (FIAP, Domain Driven Design - Java).

## Tecnologias
Java 21 · Spring Boot 3.3.5 · Spring Web · Spring Data JPA (Hibernate) · Bean Validation · Oracle (ojdbc11) · Docker · Render

## Arquitetura (camadas)
```
br.com.fiap.energia
├── model        Entidades JPA mapeadas para o Oracle (UnidadeConsumidora, LeituraConsumo, TipoUnidade)
├── dao          Acesso a dados (Spring Data JPA)
├── bo           Regras de negócio e transações
├── controller   Endpoints REST (GET, POST, PUT, DELETE)
├── dto          Objetos de entrada/saída (records) com validações
└── exception    Exceções de domínio e GlobalExceptionHandler
```

## Modelo de dados
- **TB_UNIDADE_CONSUMIDORA**: ID_UNIDADE, NM_UNIDADE, DS_ENDERECO, NM_CIDADE, TP_UNIDADE, DT_CADASTRO, ST_ATIVA
- **TB_LEITURA_CONSUMO**: ID_LEITURA, ID_UNIDADE (FK), DT_LEITURA, NR_CONSUMO_KWH, VL_TARIFA_KWH

Uma unidade consumidora possui várias leituras (1:N). As tabelas são criadas automaticamente (`ddl-auto=update`).

## Endpoints
| Método | Rota | Descrição |
|---|---|---|
| GET | `/` | Status da API |
| GET | `/unidades?cidade=` | Lista unidades (filtro opcional) |
| GET | `/unidades/{id}` | Busca unidade |
| POST | `/unidades` | Cria unidade (201 + Location) |
| PUT | `/unidades/{id}` | Atualiza unidade |
| DELETE | `/unidades/{id}` | Exclui unidade (204) |
| GET | `/leituras?unidadeId=` | Lista leituras (filtro opcional) |
| GET | `/leituras/{id}` | Busca leitura |
| POST | `/leituras` | Registra leitura (201 + Location) |
| PUT | `/leituras/{id}` | Atualiza leitura |
| DELETE | `/leituras/{id}` | Exclui leitura (204) |

Exemplos prontos de requisições em `requests.http`.

## Regras de negócio
- Leitura só pode ser registrada em unidade **ativa**.
- Data da leitura não pode ser futura; consumo e tarifa devem ser maiores que zero.
- Unidade com leituras associadas **não pode ser excluída**.
- `valorTotal` da leitura = consumo × tarifa (2 casas, arredondamento HALF_UP).

## Tratamento de exceções
| Situação | HTTP |
|---|---|
| Recurso não encontrado | 404 |
| Dados inválidos / JSON malformado | 400 (com lista de campos) |
| Regra de negócio violada | 422 |
| Violação de integridade no banco | 409 |
| Erro inesperado | 500 |

Todas as respostas de erro seguem o mesmo formato JSON (`timestamp`, `status`, `erro`, `mensagem`, `caminho`, `detalhes`).

## Variáveis de ambiente
Nenhuma credencial fica no código. Defina antes de executar:

| Variável | Exemplo |
|---|---|
| `DB_URL` | `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL` |
| `DB_USER` | seu RM / usuário |
| `DB_PASSWORD` | sua senha |

## Executar localmente
Windows (Prompt de Comando):
```
set DB_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
set DB_USER=SEU_USUARIO
set DB_PASSWORD=SUA_SENHA
mvn clean -DskipTests package
java -jar target/energia-0.0.1-SNAPSHOT.jar
```
Linux/macOS: use `export` no lugar de `set`.

## Deploy (Render)
1. Suba o projeto para o GitHub.
2. No Render: **New > Web Service**, escolha o repositório, runtime **Docker**, plano **Free**.
3. Cadastre `DB_URL`, `DB_USER` e `DB_PASSWORD` em Environment Variables.
4. **Deploy Web Service**. A URL pública aparece no topo da página do serviço.

> O plano gratuito hiberna por inatividade: a primeira requisição pode levar 50 s ou mais.
