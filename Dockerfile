FROM openjdk:11
COPY build/classes/java/main/com/example/demo /tmp
WORKDIR /tmp
CMD java com/example/demo/TradeHelperApplication
# last error:
# Error: Could not find or load main class com.example.demo.TradeHelperApplication
# 2021-11-28T14:24:05.128115614Z Caused by: java.lang.ClassNotFoundException: com.example.demo.TradeHelperApplication