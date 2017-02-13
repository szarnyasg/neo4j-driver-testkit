#!/bin/bash

set -e # exit with nonzero exit code if anything fails

cd "$( cd "$( dirname "$0" )" && pwd )/../.."

git clone https://github.com/szarnyasg/neo4j-java-driver
cd neo4j-java-driver
mvn clean install -DskipTests
