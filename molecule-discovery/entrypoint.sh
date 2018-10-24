#!/bin/bash
set -e

SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-dev}

# Start application with specific JVM_ARGS and SPRING_PROFILE
java ${JVM_ARGS} -Djava.security.egd=file:/dev/./urandom -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} ${APPLICATION_HOME}/application.jar