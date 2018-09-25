FROM jenkins/jenkins:lts-alpine

MAINTAINER Jeromy Van Dusen <jeromy.vandusen@gmail.com>

ENV JENKINS_USER administrator
ENV JENKINS_PASS administrator

ENV ROOT_URL http://jenkins:8080/
ENV GENERATE_SEED_JOB false
ENV CONFIGURE_GLOBAL_LIB false

ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

USER root
RUN echo http://dl-cdn.alpinelinux.org/alpine/edge/community >> /etc/apk/repositories && \
    apk update && \
    apk add docker && \
    apk add openrc && \
    rc-update add docker boot && \
    apk del openrc && \
    apk add py-pip && \
    pip install docker-compose && \
    rm -rf /var/cache/apk/*

COPY plugins.txt /usr/share/jenkins/ref/plugins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins/plugins.txt

COPY scripts/*.groovy /usr/share/jenkins/ref/init.groovy.d/

VOLUME /var/jenkins_home
