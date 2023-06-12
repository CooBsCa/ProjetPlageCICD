# ProjetPlageCICD


## Reservation de parasol sur la plage

L'application vous donnera accès à une api pour reserver des parasols sur une plage privée.

## Build Docker Image

Run : `docker image build --tag=docker-cicd:latest ./` 

## Running Application

Run : `docker container run -p 8080:8080 --name docker-cicd docker-cicd:latest mvn spring-boot:run` pour démarrer l'application sur le port 8080 et ajoutez `/swagger-ui/index.html#` à la fin de l'url générée pour accéder au swagger de l'API.

## Stop Application

Dans le même terminal que la commande de run `controle + c`  
<b>OU</b>  
Dans un autre terminal `docker container stop docker-cicd`