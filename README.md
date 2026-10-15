# micro-2-java · ordini-service

Microservizio **Quarkus** (Java 21) per la gestione degli ordini. Dati in memoria, nessun database.

| | |
|---|---|
| Porta | `8082` |
| Porta debug (dev mode) | `5007` |
| Health | `GET /q/health` |

## Requisiti

- JDK 21+
- Maven 3.9+ (`mvn -v`)

## Comandi

```powershell
mvn quarkus:dev        # avvio in dev mode con live reload (Dev UI: http://localhost:8082/q/dev-ui)
mvn test               # test
mvn package            # build: target\quarkus-app\quarkus-run.jar
java -jar target\quarkus-app\quarkus-run.jar
```

## API

| Metodo | Path | Descrizione |
|---|---|---|
| GET | `/ordini` | elenco |
| GET | `/ordini/{id}` | dettaglio, 404 se assente |
| POST | `/ordini` | crea: `{"prodottoId":1,"quantita":2}` |
| POST | `/ordini/{id}/annulla` | porta l'ordine in stato `ANNULLATO` |

```powershell
curl.exe http://localhost:8082/ordini
```
