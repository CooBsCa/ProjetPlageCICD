FROM ubuntu:20.04
    #RUN apt-get update && \
    #    apt-get install --yes --no-install-recommends maven
    # hadolint ignore=DL3008
    RUN apt-get update && apt-get install -y --no-install-recommends wget && \
        apt-get clean && \
        rm -rf /var/lib/apt/lists/*
    #create a dir foe maven in opt
    RUN mkdir /opt/maven && \
        mkdir /usr/share/maven
    #RUN mkdir /usr/share/maven
    #dwnl maven using link in tmp
    RUN wget --progress=dot:giga --no-check-certificate https://dlcdn.apache.org/maven/maven-3/3.9.2/binaries/apache-maven-3.9.2-bin.tar.gz -O /tmp/maven.tar.gz
    #extract the file
    WORKDIR /tmp
    RUN tar xvf maven.tar.gz && \
    #copy the file into opt/maven
    cp -R /tmp/apache-maven-3.9.2/* /usr/share/maven/
    #RUN cp /opt/files/* /usr/share/maven
    COPY pom.xml /usr/local/service/pom.xml
    COPY src /usr/local/service/src
    WORKDIR /usr/local/service
    # Install OpenJDK-17
    # hadolint ignore=DL3008
    RUN apt-get update && \
        apt-get install -y --no-install-recommends openjdk-17-jdk && \
        apt-get clean && \
        rm -rf /var/lib/apt/lists/*
    # Setup JAVA_HOME -- useful for docker commandline
    ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64/
    RUN export JAVA_HOME

   

    ENV MAVEN_HOME /usr/share/maven
    ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
    RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn
    #RUN mvn -version
    RUN mvn clean install && \
        mvn package

    EXPOSE 8080
    
    CMD [ "java","-cp","target/docker-service-1.0-SNAPSHOT.jar", "fr.plage.reservation.ReservationApplication"]

