# 🎯 Logic Tournament

Aplicação full stack em Kotlin com frontend Android (Jetpack Compose) e backend Kotlin para torneios de jogos de lógica em tempo real.

## 🚀 Objetivo

Construir uma aplicação completa que envolva:
- Lógica de jogo (ex: 4 em linha, Tic-Tac-Toe, Nim)
- Torneios e ranking (ex: ELO)
- Comunicação em tempo real via WebSockets
- Backend concorrente e escalável
- Interface Android moderna com Jetpack Compose
- Boas práticas de clean code, testes, arquitetura e CI/CD



- Criar uma app Android onde os utilizadores podem:

- Iniciar sessão

- Participar em torneios de lógica (jogos simples como 4 em linha, Sudoku, Nim, etc.)

- Jogar contra o CPU (AI) ou outros jogadores via socket

- Ver estatísticas e rankings

---

## 📦 Tecnologias

| Camada      | Tecnologias                                                        |
|-------------|---------------------------------------------------------------------|
| Frontend    | Android, Kotlin, Jetpack Compose, Retrofit, OkHttp (WebSockets)    |
| Backend     | Kotlin, HTTP4K, Coroutines,JDBC, PostgreSQL     |
| Testes      | JUnit5, Kotest, Espresso, Testcontainers                            |
| CI/CD       | GitHub Actions                                                      |
| Documentação| Markdown, possivelmente OpenAPI para o contrato                    |

---
## Architetura

- **Frontend**: Aplicação Android com Jetpack Compose, utilizando Retrofit para chamadas REST e OkHttp para WebSockets.
- MVVM com ViewModel, StateFlow, Repository
- Retrofit para REST e OkHttp WebSocket para comunicação em tempo real
- UI com Jetpack Compose
- Persistência local opcional (Room ou DataStore)

- **Backend**: Servidor em Kotlin com HTTP4K, utilizando Coroutines para concorrência e Exposed ou JDBC para acesso a dados.
- REST para registo/login, torneios, histórico
- WebSocket para jogos ao vivo
- Concorrência com coroutines
- Base de dados PostgreSQL
- Autenticação com JWT
- Algoritmos de jogo (Minimax para AI, verificação de vitórias, etc.)
- Clean architecture: domain, application, infrastructure, web


-- pontos
- utilizar flow com websocket, estrutura de dados (hashmap) para 
- jogos a decorrer e um objeto de partilha de dados (gameRoom) 
- para partilhar o estado do jogo entre os jogadores
- separar os services dos websockets dos rest