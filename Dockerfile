FROM openjdk:17-jdk
ADD ./build/libs/texoit-api-1.0.jar /texoit-api-1.0.jar

EXPOSE 9180
CMD ["java", "-jar", "texoit-api-1.0.jar"]