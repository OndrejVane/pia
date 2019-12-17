#!/bin/bash
# build and run spring application in docker

echo "START - BUILDING WEB APPLICATION";
mvn clean install
echo "FINISHED - BUILDING WEB APPLICATION";

echo "START - CREATE DOCKER IMAGE WITH APPLICATION";
docker build -t piaondrejvane .
echo "FINISHED - CREATE DOCKER IMAGE WITH APPLICATION";

echo "START - RUN CREATED IMAGE IN DOCKER ON PORT 8080";
docker run -p8080:8080 piaondrejvane
echo "APPLICATION FINISHED";
