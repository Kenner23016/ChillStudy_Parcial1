FROM openjdk:17-alpine

# Instalar cliente de PostgreSQL en Alpine
RUN apk update && apk add postgresql-client bash

# Crear carpeta de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Copiar script de espera
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh

# Dar permisos al wrapper
RUN chmod +x mvnw

# Compilar el proyecto
RUN ./mvnw clean package -DskipTests

# Ejecutar la app usando el script
CMD ["/wait-for.sh"]
