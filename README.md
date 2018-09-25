# Jenkins Image

This is a custom Docker image for Jenkins, based on the latest version of the official Jenkins lts-alpine image, which can be found
[here](https://hub.docker.com/r/jenkins/jenkins/). This image is extended to add the following features:

* Docker CLI and Docker Compose are installed, so that Jenkins pipelines can be used to build and maintain Docker images.
* pre-initialized with a predetermined set of useful plugins.
* administrator user is created, with the option to customize this user through environment variables (see below).
* seed job created that uses the Job DSL plugin to automatically create jobs for your project.
* global shared libraries configured and ready for use in your pipeline jobs.

## Environment Variables

* ROOT_URL - The root URL for this Jenkins instance (default: http://jenkins:8080/).
* JENKINS_USER - The name of the administrator user to be created (default: administrator).
* JENKINS_PASS - The password for the administrator user to be created (default: administrator).
* SEED_JOBS - Comma separated list of names and URLs for any seed jobs to be created, with each entry in the format
  "name:URL" (default: ""). If blank, no seed jobs will be created.
* GLOBAL_SHARED_LIBRARIES - Comma separated list of names and URLs for any global shared libraries to be configured,
  with each entry in the format "name:URL" (default: ""). If blank, no global shared libraries will be configured.
* CREDENTIALS - Comma separated list of any credentials to be created, with each entry in the format
  "type|id|credentials|description" (default: ""). The valid types are "UsernamePassword" and "SecretText". For credentials of
  type "UsernamePassword", the "credentials" portion is in the format "username|password". For credentials of type
  "SecretText", the "credentials" portion is in the format "secret". If blank, no credentials will be created.

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
             -e SEED_JOBS="project-seed|https://github.com/example/project-seed-job.git" \
             -e GLOBAL_SHARED_LIBRARIES="pipeline-lib|https://github.com/example/pipeline-lib.git,project-lib|https://github.com/example/project-lib.git" \
             -e CREDENTIALS="UsernamePassword|GITHUB_CREDENTIALS|githubuser|P@55w0rd!|GitHub Credentials,UsernamePassword|DOCKER_HUB_CREDENTIALS|dockeruser:P@55w0rd!|Docker Hub Credentials,SecretText|SLACK_TOKEN|T0k3n|Slack Token"
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
      SEED_JOBS: "project-seed|https://github.com/example/project-seed-job.git,other-seed|https://github.com/example/other-seed-job.git"
      GLOBAL_SHARED_LIBRARIES: "pipeline-lib|https://github.com/example/pipeline-lib.git,project-lib|https://github.com/example/project-lib.git"
      CREDENTIALS: "UsernamePassword|GITHUB_CREDENTIALS|githubuser|P@55w0rd!|GitHub Credentials,UsernamePassword|DOCKER_HUB_CREDENTIALS|dockeruser:P@55w0rd!|Docker Hub Credentials,SecretText|SLACK_TOKEN|T0k3n|Slack Token"
    ports:
    - "8080:8080"
    volumes:
    - "/var/run/docker.sock:/var/run/docker.sock"
    - "./data:/var/jenkins_home"
    restart: unless-stopped
```

## Improvements

* Automatically approve seed job script.
* Automatically configure Slack notifier settings.
