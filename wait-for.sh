#!/bin/sh

# Host y puerto desde variables de entorno, con valores por defecto
DB_HOST="${DB_HOST:-ep-wispy-morning-a8gc1j4j-pooler.eastus2.azure.neon.tech}"
DB_PORT="${DB_PORT:-5432}"
DB_USER="${DB_USER:-ChillStudy_db_owner}"

echo "⌛ Esperando a que la base de datos en $DB_HOST:$DB_PORT esté disponible..."

until pg_isready -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER"; do
  echo "❗ Aún no responde la base de datos..."
  sleep 2
done

echo "✅ Base de datos disponible. Iniciando la app..."
exec java -jar target/crud-0.0.1-SNAPSHOT.jar
✅ Qué más debes hacer:
