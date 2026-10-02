# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Phase 2: Server Design

[View the server sequence diagram (presentation mode)](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vjYDE0DsIyMQinAEbSHACgwAAMhAWSFFhzBOtQ-rNG0XS9AY6j5GgBGKp8kIgr8-yArR6HwpUQH+uB5aqTB6z6H8OxfNCjzARJVBIjACDCeKGJCSJBJEmApLboYu40vuDJMlOKlcjefl3kuwowGKEpujKcplu8Sqhby4W2fZsXaDA0AwD2famCqwYahl8gwNkOW9ggMAtGkMAaTsRgQGoOUQMwxxgCA8TeSB1T+sy0yXtASAAF4oBwUYxnGhSgZhyCpjA6YAIwETmqh5vM0FFiW9Q+P1eqDSNux0U2XW+Sl-L1IecgoM+8Tnpe17Dg6EX1I+Ab3VuJ0ftZ-queKGSqAB05GSgawUVR6Ckvp8K2QZhHA18sGXuDyGQlc01JpUYk4XhylEetJljGDiH1qjjYMZ43h+P4XgoOgMRxIkNN065vhYGJgqgfUDTSBGAkRu0EbdD08mqIpwzGkjxNTT1ukPLC-pE0hmBQ5Sb5dg5wktKed3wVLpJqxUj30jAjJgDdOuUXryULgKFTLjAABm4pPu9JXxYr6DW-uPpqy9rvAFlsAAHIVZgBXqm9L6ZWVuX9mrPsy66u3xPto3jSgsaKTpPpY-NTgLTAy38mtBZjJt0DbcnqeHWTJ1Ure51GCg3DHpeFvI3OYVPZU9scM3TKGDdWglTqeqR5bSvGO2Rujk3LeKTAIAQDM2At2IM8J529TSAUYjxxjUP1Czp4wAD+4O72yvfdDvqJzAIxozLOezdhMC4fhaD30d5NMVTKLrv4bA4oNQCTRDAAA4kqDQ7MYalgaOA-mQt7BKmGB7aWkldKHwlrrJWKsKi+wcmiSBOZ25W33uUGeAUzZtzQZ3M6i5yj2ydhKG6cV5Q3WRvlVUGpWHRwFLHT65ROaSirlAYaadowZ0mtnDGudFqF1GGMFaJcNrFgrgqUR4ia70TrpQwhORiFqAxHQm295IrRXXCaSqhiNBTwxjIBuxtkE5lATkL2qUhHOnVs4tQrisD7wqFgvxNjoomgxD41QfjIbX03jUF4ESXguCSRcR+GDn4lDAPUdm78FH3wSffJJLgUm1wpsxfwHAADsbgnAoCcDECMwQ4DcQAGzwAnIYQxMAigv3ErfDBXNWgdCQSg5OyMsw+KDkqVJiZyhYLQWsEYEyplX3lrpAhl10SGNIUhNYSy5iknDhqJADscoViOLGSw3kMbCL0BwdyagwDp0zvGUCsielpjwrk7Mxd8yqK2rcmAKJCQPIbDo8hejTbm1oe47uQp6jMJdlHN27DJaTx0NPRxs9eElWygIje9iCHYoDri0OYduHjxlDHUOATPH9JERRauTzpHowqHI-OCjvm5l+YWNRpYdoMrEQdUFx1yEOK7sbDZKAtl7JBjlFAmx4DxH5Fs4kJjvbPXIJAFEEClQylHvqHxABJaQcqFVwCVeoQxoMJSx1qggYAlgMJvEmTmTQ6L7F6LIMAGgDF8VpRdCbJUxrTA0rll+LJ7TDH-UBka6Q0TVndTpYsoN0hpkYXsbnd+4yU3f1MP4GAAApCA4oIEKAALKGD1GVVQ8RhqlQFKoCAMB4h4HXJAQFwYOAAEIe29p7ZgfNBb+jMBbjAOYRgoDAAVVWgUjxSrMG2L2dcOhDCNrHT4Dqjt9DoigH2vde7GKUwCI6vsEAFWxCQAkMAJ7HIKqLSWzp-hkigDVN0jJHM77NGZLJHoPjUEDUFXkAo9Q0FZlXg6ygcAICOSgLsnNMi9LXxA6i9ACzwOOqgFBmDcG5jGqsgm-16sABWxa0BbPmXaiDmHoPQBwygY1XlRUQqZFClDaA1XhR7uY52FLMruzY26jF4qsX+0DuVPKfq+l2QDUSsTAiyWFV4yVKleVQ3CL6gKrRTKs4ssxu8vOS1FHKO5WXXlldNNCtzXXMV9DZ6m2lSmjjsL7YWJ1XMNhgbcPSEEx6zF9RDEyhJRJzFibpPqwC5lIL-YFMRwi8p-hFUqo1XQ5BmjsAY4tRgAAdRYIagWYmABCAkFBwAANKCI7HEmABWQz3JyNpl5T8M36fTB-Tlq0TPl1LACoFHlhXk0OTAOr6J1xLxg-WysZoazhnQWF0L-pAxTbDEhBrs303JhaxmIuXL1o8q2pNzt03qJWaY3540a4mj2ow+ElNawUvUew9gq82gXDBScwwuFVRmB6lXf3cAo1KMYYSkaB9uqtx2NOjbV02Bh6jRhYuQj29d4hq8e2LB97P5nxHBffseDYEvDTTfeA+ms2jCs7-AIXgHX00vYzSd8pEDBlgMAbAq9CBAc-m+8wH66Xc15vzQWvRjAIawdIfu6IhsoGBW4vBtKwsXW4HgYxuizsgEV+6bQyv8VcYV0zyslVh4BxLT9ow2g9AGDdRjPRHBkeSYITbtAe9UcHyQ4vdX5AN3Y8vnjuXBlCfpLmjkh+tcgA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

