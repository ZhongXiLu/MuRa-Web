FROM adoptopenjdk/openjdk11:jdk-11.0.8_10

RUN apt update && apt install -y \
    git \
    postgresql \
    maven

USER postgres
RUN /etc/init.d/postgresql start && \
    psql --command "ALTER USER postgres WITH PASSWORD 'postgres';" && \
    createdb -O postgres mura-web
RUN echo "host all  all    0.0.0.0/0  md5" >> /etc/postgresql/10/main/pg_hba.conf
RUN echo "listen_addresses='*'" >> /etc/postgresql/10/main/postgresql.conf

USER root
WORKDIR /usr/src/mura-web
COPY build/libs/*.jar /usr/src/mura-web/mura-web.jar
COPY entrypoint.sh /usr/src/mura-web/entrypoint.sh
COPY config.xml /usr/src/mura-web/config.xml
RUN chmod +x /usr/src/mura-web/entrypoint.sh

CMD ["/usr/src/mura-web/entrypoint.sh"]
