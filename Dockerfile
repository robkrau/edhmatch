FROM alpine
MAINTAINER rkra

RUN apk -U upgrade
RUN apk add openjdk17
RUN echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk" >> ~/.zshrc
RUN echo "export PATH=$PATH:$JAVA_HOME/bin" >> ~/.zshrc
RUN source ~/.zshrc

COPY target/edhmatch-0.0.1-SNAPSHOT.jar edhmatch.jar
ENTRYPOINT ["java", "--add-opens java.base/sun.net.www.protocol.https=ALL-UNNAMED", "-jar", "edhmatch.jar"]


