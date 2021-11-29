# Trading Demo

This is a small demo application representing a minimal crypto-trading system and exchange based on Spring Boot.

## Description

The application consists of two parts: a frontend and backend.
The frontend acts as the interface to the users.
They can use the HTTP endpoints provided to request (quote) crypto coins and also buy them.
The backend represents a kind of exchange and delivers the current price or executes a buy order.

Since the application contains both the backend and the frontend logic, this demo can be started with a single Java instance.
However, two different Java processes can also be used, which can be used to demonstrate distributed tracing, for example.
See the following section.

### Running the Demo

The demo can be started directly inside your IDE or via the following gradle task:

```
./gradlew bootRun
```

Using the following task, a JAR archive can be created which can be executed:

```
./gradlew bootJar
```

#### Multi-Instance Setup

If the demo should be started with multiple instances, a URL can be specified for the frontend instance, under which it can reach the backend instance.
By default, this is set to `http://localhost:8080`.

The URL can be specified using the system property `backend_url`. See the following example:

```
java -Dbackend_url=http://target:8081 -Dserver.port=8080 -jar ...
```

Please note that you have to specify the port used by the applications in case they are running on the same machine.
This can be achieved using the property `server.port`. See the previous example.

## Using the Application

The application provides the following endpoints:

| Endpoint | Description |
|---|---|
| `/info` | Returns some information about the current instance. |
| `/quote?coin=<COINT>&amount=<AMOUNT>` | Requests a quote to buy crypto coins. The coin parameter has to be one of: `BTC`, `ETC`. The amount represents a floating point number. |
| `/status?quote=<QUOTE_ID>` | Returns information about the status of a quote. |
| `/buy?quote=<QUOTE_ID>` | Accepts a quote and executes the related buy order. |


