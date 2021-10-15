#!/bin/bash
runuser -l postgres -c '/usr/lib/postgresql/12/bin/postgres -D 12/main -c config_file=/etc/postgresql/12/main/postgresql.conf' &

until pg_isready -h localhost -p 5432 -U postgres; do
  sleep 0.1
done

java -jar /usr/src/mura-web/mura-web-*.jar