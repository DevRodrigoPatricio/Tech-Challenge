FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080
# porta de debug
EXPOSE 5005  

# Ativa o modo debug (com suspend=n para não pausar a execução)
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD ["sh", "-c", "java $JAVA_OPTS -jar target/*.jar"]