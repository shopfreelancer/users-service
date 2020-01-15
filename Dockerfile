FROM tomcat:9.0.30-jdk11-openjdk

# enable manager-gui
COPY ./config/tomcat-users.xml $CATALINA_HOME/conf
COPY ./config/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

COPY ./target/user-service-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/user-service.war