# Jenkins Image

This is a custom Docker image for Jenkins, based on the latest version of the official Jenkins lts-alpine image, which can be found
[here](https://hub.docker.com/r/jenkins/jenkins/). This image is extended to add the following features:

* Docker CLI and Docker Compose are installed, so that Jenkins pipelines can be used to build and maintain Docker images.
* pre-initialized with a predetermined set of useful plugins.
* administrator user is created, with the option to customize this user through environment variables (see below).
* seed job created that uses the Job DSL plugin, which is installed, to automatically create jobs for your project.

## Environment Variables

* ROOT_URL - The root URL for this Jenkins instance (default: http://jenkins:8080/).
* JENKINS_USER - The name of the administrator user to be created (default: administrator).
* JENKINS_PASS - The password for the administrator user to be created (default: administrator).
* GENERATE_SEED_JOB - Whether or not a seed job should be generated (default: false).
* SEED_JOB_NAME - The name of the seed job to be created.
* SEED_JOB_GIT_URL - The URL of the Git repository containing the seed job script.
* CONFIGURE_GLOBAL_LIB - Whether or not to configure global shared libraries (default: false)
* GLOBAL_LIB_NAME - The name of the global shared library to configure. For multiple libraries, separate by commas.
* GLOBAL_LIB_GIT_URL - The URL of the Git repository containing the global shared library. For multiple libraries, separate by commas.

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
             -e GENERATE_SEED_JOB="true" \
             -e SEED_JOB_NAME="project-seed" \
             -e SEED_JOB_GIT_URL="https://github.com/example/project-seed-job.git" \
             -e CONFIGURE_GLOBAL_LIB="true" \
             -e GLOBAL_LIB_NAME="project-lib" \
             -e GLOBAL_LIB_GIT_URL="https://github.com/example/project-lib.git" \
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
      GENERATE_SEED_JOB: "true"
      SEED_JOB_NAME: project-seed
      SEED_JOB_GIT_URL: https://github.com/example/project-seed-job.git
      CONFIGURE_GLOBAL_LIB: "true"
      GLOBAL_LIB_NAME: project-lib
      GLOBAL_LIB_GIT_URL: https://github.com/example/project-lib.git
    ports:
    - "8080:8080"
    volumes:
    - "/var/run/docker.sock:/var/run/docker.sock"
    - "./data:/var/jenkins_home"
    restart: unless-stopped
```

## Improvements

* Automatically approved seed job script.
* Automatically configure Slack notifier settings with token secret text.
* Automatically configure Docker Hub credentials.
