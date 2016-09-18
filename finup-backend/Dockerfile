FROM jetty:alpine

MAINTAINER Marcio Rodrigues <marciorodrigues87@gmail.com>

ADD api/target/*.war /var/lib/jetty/webapps/ROOT.war

RUN java -jar "$JETTY_HOME/start.jar" --add-to-startd=jmx,stats
