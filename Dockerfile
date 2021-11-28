FROM openjdk:11
COPY build/classes/java/main/com/example/demo /tmp
WORKDIR /tmp
CMD java com/example/demo/TradeHelperApplication