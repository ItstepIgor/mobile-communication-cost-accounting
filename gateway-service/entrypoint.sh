#!/bin/bash

set -e

host="config-service"
port="8888"
cmd="$@"

>&2 echo "!!!!!!!! Check config-service for available !!!!!!!!"

until curl http://"$host":"$port"; do
  >&2 echo "config-service is unavailable - sleeping"
  sleep 10
done

>&2 echo "config-service is up - executing command"

exec java -jar $cmd