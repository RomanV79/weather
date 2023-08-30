FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package
EXPOSE 8080

#ENV WEATHER_DB_HOST=jdbc:postgresql://postgres:5432/weather
#ENV WEATHER_DB_HOST=jdbc:postgresql://host.docker.internal:5432/weather
#
#ENV WEATHER_DB_USER=user2
#ENV WEATHER_DB_PASSWORD=isYfi53hm8XMqdTY

FROM tomcat
COPY --from=build /app/target/*.war $CATALINA_HOME/webapps/weather.war
CMD ["catalina.sh", "run"]


















#FROM tomcat:10.1-jdk17
#COPY /target/*.war /usr/local/tomcat/webapps/weather.war


#ENV APP_ROOT /weather
#RUN apt-get update && apt-get install -y default-jdk
#
#COPY weather.war $CATALINA_BASE/webapps/weather.war
#WORKDIR $APP_ROOT

#FROM maven:3-amazoncorretto-8 AS build
#WORKDIR /app
#COPY pom.xml ./pom.xml
#RUN mvn dependency:go-offline
#
#COPY src ./src
#RUN mvn package
#
#FROM tomcat:9-jre8
#COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
