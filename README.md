## Visão Geral 
Desafio Backend Java para a empresa DBC Company.

## Objetivo
Cada associado possui um voto e as decisões são tomadas em assembleias, por votação.
A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação.

● Cadastrar uma nova pauta;

● Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo
determinado na chamada de abertura ou 1 minuto por default);

● Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é
identificado por um id único e pode votar apenas uma vez por pauta);

● Contabilizar os votos e dar o resultado da votação na pauta.


## Requisitos para rodar:

● Docker instalado

● Java 8+ e Maven


## Execução:

Clonar o Repositório

```bash
git clone git@github.com:michelbernardods/pauta-dbc.git
cd pauta-dbc
```

Build da Aplicação
```bash
mvn clean compile
mvn install
```

execute o docker e depois rode a aplicação na sua IDE
```bash
docker-compose up 
run aplicação
```

### Endpoints Disponíveis:
Cadastrar Nova Pauta

Endpoint: POST /api/v1/cadastrar

Descrição: Cria uma nova pauta para votação.

Exemplo de Uso:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"nome": "Minha Pauta"}' http://localhost:8080/api/v1/cadastrar
```

Abrir Pauta para Votação

Endpoint: POST /api/v1/abrir

Descrição: Abre uma pauta para votação com a possibilidade de especificar a duração em minutos (opcional).

OBS: Caso não for definido os minutos o default é 1 minuto

Exemplo de Uso:
```bash
curl -X POST "http://localhost:8080/api/v1/abrir?id=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx&minutos=2"

curl -X POST "http://localhost:8080/api/v1/abrir?id={id_da_pauta}"
```

Votar em uma Pauta

Endpoint: POST /api/v1/votar

Descrição: Registra um voto em uma determinada pauta.

Exemplo de Uso:
```bash
curl -X POST "http://localhost:8080/api/v1/votar?idPauta=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx&idAssociado=1&voto=sim&cpf=12345678900"
```

Obter Resultado da Votação

Endpoint: GET /api/v1/resultado/{id}

Descrição: Obtém o resultado da votação de uma pauta específica.

Exemplo de Uso:
```bash
curl -X GET "http://localhost:8080/api/v1/resultado/xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
```