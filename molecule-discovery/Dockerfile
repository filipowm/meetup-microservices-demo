# Set the base image to java8
FROM openjdk:8-alpine

# File Author / Maintainer
MAINTAINER Mateusz Filipowicz

# Define default environment variables
ENV APPLICATION_HOME=/opt/application
ENV APPLICATION_BINARIES=/opt/application/bin

# Create directory
RUN mkdir -p $APPLICATION_BINARIES

# Set default directory
WORKDIR $APPLICATION_HOME

# Copy application jar file
COPY target/*.jar $APPLICATION_HOME/application.jar

# Add initialization script
ADD entrypoint.sh $APPLICATION_BINARIES/entrypoint.sh

# Give permissions
RUN chmod 755 $APPLICATION_BINARIES/entrypoint.sh

# Expose default servlet container port
EXPOSE 8080

# Main command
ENTRYPOINT ["/bin/sh", "/opt/application/bin/entrypoint.sh"]