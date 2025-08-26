# Java image
FROM openjdk:21-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos pom.xml y el wrapper de Maven
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copiamos el código fuente
COPY src ./src

# Damos permisos al wrapper
RUN chmod +x mvnw

# Construimos el proyecto (sin tests)
RUN ./mvnw clean package -DskipTests

# Exponemos el puerto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/contact-manager-0.0.1-SNAPSHOT.jar"]

