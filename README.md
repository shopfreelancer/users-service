# Spring Boot User Service

## Local Dev environment
Set active profile in your IDE `-Dspring.profiles.active=local`

Start Postgres database
```
docker-compose up -d postgres
```

Start Spring boot from your IDE or use `mvn spring-boot:run -Dspring.profiles.active=local`

Test run the app before deploying with the docker file
```
docker-compose up --build
```