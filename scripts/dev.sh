#!/usr/bin/env bash

docker_command=$1
if [[ -z "$1" ]]; then
  echo "Usages: dev [up|down|restart] [rebuild]"
  echo "up - boot up docker container"
  echo "     --rebuild - rebuilds images, can only be used with up"
  echo "down - shut down docker container"
  exit 1
fi
rebuild=0
if [[ $2 == '--rebuild' ]]; then
  rebuild=1
fi

base_dir=${PWD}
echo ">>> base dir: ${base_dir}"

function to_dir() {
    cd "$1" || exit
    echo ">>> change dir: ${PWD}"
}

if [[ $rebuild == 1 ]]; then
  echo ">>> Building key-generator"
  to_dir key-generator
  ./gradlew jibDockerBuild
  to_dir ../
fi

echo ">>> Firing docker"
docker-compose "$docker_command" -d
