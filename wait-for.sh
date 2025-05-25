#!/bin/sh
# Esperar a que la base de datos PostgreSQL esté disponible
until pg_isready -h chillstudy_db -p 5432 -U postgres; do
  echo "Esperando a chillstudy_db..."
  sleep 2
done

echo "Base de datos disponible. Iniciando la app..."
exec java -jar target/crud-0.0.1-SNAPSHOT.jar
