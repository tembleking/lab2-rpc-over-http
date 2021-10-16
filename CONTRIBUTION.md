## Compilation instructions

In order to compile and execute this RPC example, you need first to create an account at [DeepL](https://www.deepl.com/pro#developer).

This is required, because the [server makes use of the DeepL API](./server/src/main/kotlin/translator/Server.kt) to execute translations.

### Building the server

To do that, you can execute the following steps:

```shell
$ gradle :server:bootJar -x test --no-daemon
```

This will compile the server into `./server/build/libs/server.jar`.

### Building the client

To build the client, a connection to the server is established to retrieve the wsdl required to autogenerate the RPC code.
That's why the server must be started, for the client to be successfully compiled.
Since the server requires an DeepL API token to work correctly, it must be provided as an environment variable.

```shell
$ DEEPL_AUTH_KEY="<YOUR DEEPL API KEY>" java -jar ./server/build/libs/server.jar
```

Once the server is up and ready to accept requests, the client can be built correctly:

```shell
$ gradle :client:bootJar --no-daemon
```

This will compile the client into `./client/build/libs/client.jar`.

### Executing both server and client

The server must be provided a DeepL API token to work correctly as an environment variable, because this will be
the service used to translate the requests from the clients.

```shell
$ DEEPL_AUTH_KEY="<YOUR DEEPL API KEY>" java -jar ./server/build/libs/server.jar
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.5.5)
...
...
2021-10-16 19:22:27.634  INFO 21384 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-10-16 19:22:27.645  INFO 21384 --- [           main] translator.ServerKt                      : Started ServerKt in 1.933 seconds (JVM running for 2.386)
```

After the server is running, you can execute the client:

```shell
$ java -jar ./client/build/libs/client.jar
Result of translating [Translate me!] is [¡Tradúceme!]
```

You can also execute the client with an input from STDIN to translate:

```shell
$ echo Unizar is the university of Zaragoza | java -jar ./client/build/libs/client.jar
Result of translating [Unizar is the university of Zaragoza] is [Unizar es la universidad de Zaragoza]

$ echo Web Engineering is fun | java -jar ./client/build/libs/client.jar
Result of translating [Web Engineering is fun] is [La ingeniería web es divertida]
```
