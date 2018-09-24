# Jenkins Image

This is a custom Docker image for Jenkins, based on the latest version of the official Jenkins lts-alpine image, which can be found
[here](https://hub.docker.com/r/jenkins/jenkins/). This image is extended to add the following features:

* Docker CLI and Docker Compose are installed, so that Jenkins pipelines can be used to build and maintain Docker images.
* pre-initialized with a predetermined set of useful plugins.
* administrator user is created, with the option to customize this user through environment variables (see below).
* seed job created that uses the Job DSL plugin, which is installed, to automatically create jobs for your project.

## Environment Variables

* ENV ROOT_URL - The root URL for this Jenkins instance (default: http://jenkins:8080/).
* ENV JENKINS_USER - The name of the administrator user to be created (default: administrator).
* ENV JENKINS_PASS - The password for the administrator user to be created (default: administrator).
* ENV SEED_JOB_NAME The name of the seed job to be created (default: seed).
* ENV SEED_JOB_GIT_URL The URL of the Git repository containing the seed job script (default: https://github.com/example/seed).

## Seed Job

The seed job must have a script in its root directory called "seedJobs.groovy" (this is not customizable), which uses Job DSL to
create all the jobs and views that you need for your project.

## Usage - CLI

```bash
$ docker run -d \
             -p 8080:8080 \
             -e ROOT_URL="http://jenkins.example.com" \
             -e JENKINS_USER="admin" \
             -e JENKINS_PASS="secret" \
             -e SEED_JOB_NAME="project-seed" \
             -e SED_JOB_GIT_URL="https://github.com/example/project-seed-job.git" \
             --name jenkins \
             jvandusen/jenkins:latest
```

## Usage - Compose File

```yaml
version: "3"

services:
  jenkins:
    image: jvandusen/jenkins:latest
    container_name: jenkins
    environment:
      ROOT_URL: http://jenkins.example.com
      JENKINS_USER: admin
      JENKINS_PASS: secret
      SEED_JOB_NAME: project-seed
      SEED_JOB_GIT_URL: https://github.com/example/project-seed-job.git
    ports:
    - "8080:8080"
    volumes:
    - "/var/run/docker.sock:/var/run/docker.sock"
    - "./data:/var/jenkins_home"
    restart: unless-stopped
```
