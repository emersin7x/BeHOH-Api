Opa, tudo bem? Bem vindos ao projeto beHOH API.

Infelizmente ele não ficou como eu queria devido ao pouco tempo que tive, fiz uma viagem para acompanhar a cirúrgia de um parente meu do Ceará e acabei voltando apenas sábado pela manhã.

Bom, o projeto é criado utilizando o maven, então vocês podem rodar utilizando o Maven, ou rodar pelo IntelliJ. Fica a critério de vocês! Eu rodei e fiz utilizando o IntelliJ.

Inicialmente para rodarmos esta aplicação teremos que ter o postgreSQL, eu utilizei a versão atual para criar o banco de dados.

Favor, utilizar este comando no postgreSQL para criar o banco de dados:

``` CREATE DATABASE eventos_db; ```

Em seguida, vamos ao projeto e abrimos a pasta main > resources > application.properties
Ao abrir este arquivo, colocaremos lá o nome da aplicação, o nome do banco de dados e as informações do postgres. Deixei uma informação aleatória para facilitar o entendimento.

O projeto é composto por Models, onde fica a representação das nossas tabelas do banco de dados.
Contém nossos controllers, que são os responsáveis por receber os dados da tela e fazer o envio desses dados para o nosso service.
Temos o nosso service que é o responsável por toda a validação da nossa regra de negócio, no caso ele seria o intermédio entre os dados do usuário e o banco de dados.
Temos o nosso repository que é responsável pelas ações do banco de dados, seja ela um CRUD ou execução de PROC.
E por fim, temos o config. Que foi colocado para que nossa api respeite o CORS e assim, possa ser usado no front-end a api, sem que aja erro de CORS.


eventoController:

Rota criação evento - nesta rota você precisa enviar um JSON com as informações do evento que são: 

Nome: Aqui você coloca o nome do evento.
DataHoraInicio: Aqui você coloca no formato (YYYY-mm-dd Hh:mm:ss) a data e hora do início do evento.
DataHoraFim: O mesmo do dataHoraInicio.
Vagas: aqui você coloca a quantidade de vagas do evento.

```
url: http://localhost:8080/api/eventos
Método: POST.
{
    "nome": "Teste de evento",
    "dataHoraInicio": "2024-08-12T04:35:52",
    "dataHoraFim": "2024-08-13T04:35:52",
    "vagas": 3
}
```
Rota pegar evento pelo Id - Nesta rota você precisa colocar como parâmetro na URL o ID do evento, para que assim ele mostre as informações daquele evento em específico.

```
url: http://localhost:8080/api/eventos/{idEvento}
Método: GET.
```

Rota pegar todos os eventos - Nesta rota você irá pegar todos os eventos já cadastrados.

```
url: http://localhost:8080/api/eventos
Método: GET.
```


eventoUsuarioController:

De primeira temos a rota chamada CriaEventoUsuario. Mas o quê significa? Bom, quando colocamos um usuário para participar/se inscrever em um evento, estamos automaticamente criando uma tabela intermediária. Ela será responsável por salvar informações relacionadas ao usuário em determinado evento, como a entrada dele no evento.
De forma resumida, essa é a rota da Inscrição em um evento.
# Ah, algo importante. O retorno desta api é importante, pois ela retorna o número do seu bilhete. Ele é necessário para fazer a entrada no evento.

```
url: http://localhost:8080/api/evento-usuario
Método: POST.

{
    "usuario": {
        "nome": "Emerson"
    },
    "evento": {
        "id": 2,
        "nome": "Teste de evento",
        "dataHoraInicio": "2024-08-12T04:35:52",
        "dataHoraFim": "2024-08-13T04:35:52",
        "vagas": 3
    },
    "entrada": false
}
```

Em seguida, temos a rota realizar Entrada - Ela é responsável por realizar a entrada do usuário em determinado evento.

# Caso não lembre o idBilhete que foi fornecido ao fazer a inscrição (Ele retorna um número), infelizmente você terá que cancelar a inscrição e fazê-la novamente. Devido a minha viagem, não tive tempo suficiente de fazer tudo certinho como eu queria :/

```
url: http://localhost:8080/api/evento-usuario/{idEvento}/{idBilhete}
Método: PATCH.
```

A próxima rota é a getUsuariosByEvento - Ela é responsável por pegar os usuários que foram confirmados em determinado evento.
```
url: http://localhost:8080/api/evento-usuario/usuarios/{idEvento}
Método: GET.
```
A próxima rota é a getEventosByUsuario - Ela é responsável por pegar os eventos que determinado usuário está inscrito.

```
url: http://localhost:8080/api/evento-usuario/eventos/{nomeUsuario}
Método: GET.
```
A próxima rota é a cancelarInscricao - Como o nome já diz, ela é responsável por cancelar a inscrição do usuário em determinado evento. Essa eu fiz com uma outra opção que foi a requestParams que foi para mostrar novas possibilidades de uso.

```
url: http://localhost:8080/api/evento-usuario/cancelarInscricao?nomeUsuario={nomeUsuario}&idEvento={idEvento}
(Ou adicione nos params as keys nomeUsuario e idEvento com seus respectivos valores).
Método: DELETE.
```

Por fim, mas não menos importante, o usuarioController:

O usuarioController tem uma única API. Se tivesse mais tempo, eu faria a AUTH ao invés de criar o usuário apenas com o nome do usuário.

Essa rota createUser ela é utilizada para criar um usuário.

```
url: http://localhost:8080/api/usuarios
Método: POST.
{
    "nome": "beHOH"
}
```



