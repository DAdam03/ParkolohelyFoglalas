FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY src ./src
COPY lib ./lib
COPY data ./data

RUN mkdir out && javac \
      -cp "lib/sqlite-jdbc-3.53.2.1.jar" \
      -d out \
      $(find src -name "*.java")

CMD ["java", "-cp", "out:lib/sqlite-jdbc-3.53.2.1.jar", "application.Main"]