#!/bin/bash

# Diretório onde o docker-compose está localizado
cd docker-compose

# Nome do serviço definido no docker-compose.yml
SERVICE_NAME="wiremock"

# Verificar se o contêiner está em execução
if [ "$(docker-compose ps -q $SERVICE_NAME)" ]; then
    echo "Parando e removendo contêineres do Docker..."
    docker-compose down --rmi all
else
    echo "Iniciando o Docker Compose..."
    docker-compose up -d
fi
