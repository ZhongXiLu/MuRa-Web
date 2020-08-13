#!/bin/bash
runuser -l postgres -c '/usr/lib/postgresql/10/bin/postgres -D 10/main -c config_file=/etc/postgresql/10/main/postgresql.conf' & (sleep 5 && java -jar /usr/src/mura-web/mura-web.jar)