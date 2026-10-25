FROM eclipse-temurin:25-jre

ENV LANGUAGE='pt_BR: pt'

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080

# MaxRAMPercentage evita que a JVM estoure a memória do plano gratuito da Render
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
