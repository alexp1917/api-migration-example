# api-migration-example

demonstrate potential changes for data migration in an api

This repository shows what an api migration might look like in code for
situations where the api is abstracted away by the use of a separate
microservice that simply converts one api contract to another. Imagine
starting with something like this:

```mermaid
sequenceDiagram
    participant Application
    participant Translation Layer
    participant API Backend
    Application ->> Translation Layer: /api/apples?page=0&size=10
    Translation Layer ->> API Backend: /services/token
    API Backend ->> Translation Layer: $token (e.g. =12345)
    Translation Layer ->> API Backend: /services/orchard/apples?token=$token&count=10
    API Backend ->> Translation Layer: {"count":10,"entity":[[{"key":"name","value":"apple1"},{"key":"color","value":"red"}]]}
    Translation Layer ->> Application: {"count":10,"entity":[{"name":"apple1","color":"red"}]}
```

and ending up with something like this:

```mermaid
sequenceDiagram
    participant Application
    participant Translation Layer
    participant API Backend
    Application ->> Translation Layer: /api/apples?page=0&size=10
    Note over Application,Translation Layer: This request should still be the same
    Translation Layer ->> API Backend: /services/farm/token
    Note over API Backend,Translation Layer: this url changed, services are now grouped. (e.g. by provider, in this case 'farm')
    API Backend ->> Translation Layer: $token (e.g. =12345)
    Translation Layer ->> API Backend: /services/farm/orchard/apples?token=$token&total=10
    Note over API Backend,Translation Layer: Some parameters changed (size is now total)
    API Backend ->> Translation Layer: {"total":10,"entities":[{"label":"apple1","version":1,"properties":{"color":"red"}}]}
    Note over API Backend,Translation Layer: The properties are now nested under 'properties', except 'name' (now 'label')
    Translation Layer ->> Application: {"count":10,"entity":[{"name":"apple1","color":"red"}]}
    Note over Translation Layer,Application: This response should still be the same
```

There are several changes in here, urls, parameters, and output format, which
will be dealt with by the translation layer so the main application doesn't
have to.
