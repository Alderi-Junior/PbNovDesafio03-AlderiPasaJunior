# Utilizar a imagem do JDK 17 para construção do projeto
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Utilizar a imagem do JRE 17 para execução do projeto
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
