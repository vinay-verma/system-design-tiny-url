#!/usr/bin/env bash
echo ">>> Building images and push to local registry"
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


echo ">>>docker tag tiny-url/key-generator localhost:5000/key-generator"
docker tag tiny-url/key-generator localhost:5000/key-generator
echo ">>> docker push localhost:5000/key-generator"
docker push localhost:5000/key-generator

echo ">>>docker tag tiny-url/tiny-url-api localhost:5000/tiny-url-api"
docker tag tiny-url/tiny-url-api localhost:5000/tiny-url-api
echo ">>>docker push localhost:5000/tiny-url-api"
docker push localhost:5000/tiny-url-api
