FROM jenkins/jenkins:lts-alpine

MAINTAINER Jeromy Van Dusen <jeromy.vandusen@gmail.com>

ENV JENKINS_USER administrator
ENV JENKINS_PASS administrator

ENV ROOT_URL http://jenkins:8080/

ENV SEED_JOB_NAME seed
ENV SEED_JOB_GIT_URL https://github.com/example/seed

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

RUN /usr/local/bin/install-plugins.sh ace-editor ant antisamy-markup-formatter apache-httpcomponents-client-4-api authentication-tokens bouncycastle-api branch-api build-timeout cloudbees-folder command-launcher credentials credentials-binding display-url-api docker-commons docker-workflow durable-task email-ext git git-client github github-api github-branch-source git-server gradle handlebars jackson2-api jdk-tool job-dsl jquery-detached jsch junit ldap mailer mapdb-api matrix-auth matrix-project momentjs pam-auth pipeline-build-step pipeline-github-lib pipeline-graph-analysis pipeline-input-step pipeline-milestone-step pipeline-model-api pipeline-model-declarative-agent pipeline-model-definition pipeline-model-extensions pipeline-rest-api pipeline-stage-step pipeline-stage-tags-metadata pipeline-stage-view plain-credentials resource-disposer scm-api script-security slack ssh-credentials ssh-slaves structs subversion timestamper token-macro workflow-aggregator workflow-api workflow-basic-steps workflow-cps workflow-cps-global-lib workflow-durable-task-step workflow-job workflow-multibranch workflow-scm-step workflow-step-api workflow-support ws-cleanup

COPY scripts/*.groovy /usr/share/jenkins/ref/init.groovy.d/

VOLUME /var/jenkins_home
