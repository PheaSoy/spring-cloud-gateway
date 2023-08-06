# spring-cloud-gateway-sample

## Filters
### Global filters

Before you can execute the command http, please make sure you install [httpie](https://httpie.io/docs/cli/installation).
```shell
http http://localhost:1234/actuator/gateway/globalfilters
```

### Factory filters
```shell
http http://localhost:1234/actuator/gateway/routefilters
```

## Routes

### All routes
```shell
http http://localhost:1234/actuator/gateway/routes
```

### Get route based on id
```shell
http http://localhost:1234/actuator/gateway/routes/backend-service
```