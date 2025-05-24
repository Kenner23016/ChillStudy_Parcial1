FROM openjdk:17

# Crear carpeta de trabajo
WORKDIR /app

# Copiar todos los archivos del proyecto al contenedor
COPY . .

# Dar permisos al wrapper
RUN chmod +x mvnw

# Compilar el proyecto usando el Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Ejecutar la app Spring Boot
CMD ["java", "-jar", "target/crud-0.0.1-SNAPSHOT.jar"]