# Usar imagen base con Java 17
FROM openjdk:17-alpine

# Instalar PostgreSQL client y bash
RUN apk update && apk add --no-cache postgresql-client bash


# Establecer directorio de trabajo
WORKDIR /app

# Copiar todos los archivos del proyecto al contenedor
COPY . .

# Copiar y dar permisos al script de espera
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh

# Dar permisos al wrapper de Maven
RUN chmod +x mvnw

# Compilar el proyecto sin ejecutar pruebas
RUN ./mvnw clean package -DskipTests

# Comando de arranque: espera a la base de datos y luego ejecuta el JAR
CMD ["/wait-for.sh", "ep-wispy-morning-a8gc1j4j-pooler.eastus2.azure.neon.tech", "java", "-jar", "target/crud-0.0.1-SNAPSHOT.jar"]