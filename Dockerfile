FROM maven:3.6.3-jdk-11
WORKDIR /sources
COPY pom.xml /sources/
RUN mvn -B -q dependency:go-offline
COPY ./ /sources/
RUN mvn -B package

