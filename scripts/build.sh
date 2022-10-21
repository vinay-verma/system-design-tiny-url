#!/usr/bin/env bash

cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd
cd ../
echo ">>> root dir: ${PWD}"

echo ">>> Building key-generator docker image"
cd key-generator  || exit
./gradlew jibDockerBuild
cd ../

echo ">>> Building tiny-url-api docker image"
cd tiny-url-api  || exit
./gradlew jibDockerBuild
