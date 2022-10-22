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

cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd
cd ../
echo ">>> root dir: ${PWD}"

if [[ $rebuild == 1 ]]; then
  echo ">>> Building key-generator"
  cd key-generator || exit
  ./gradlew jibDockerBuild
  cd ../

#  echo ">>> Building tiny-url-api"
#  cd tiny-url-api || exit
#  ./gradlew jibDockerBuild
#  cd ../
fi

case $docker_command in
  up)
    echo ">>> Starting docker containers"
    docker-compose up -d
    ;;
  restart)
    echo ">>> Starting docker containers"
    docker-compose restart
    ;;
  down)
    echo ">>> Shutting down docker containers"
    docker-compose down
    ;;
  *)
    echo ">>> Unknown command !!"
    ;;
esac
