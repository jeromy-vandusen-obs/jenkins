pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
        timeout(time: 15, unit: 'MINUTES')
    }

    triggers {
        pollSCM('H/5 * * * 1-5')
    }

    environment {
        IMAGE_NAME = 'jvandusen/jenkins'
        JENKINS_VERSION = '2.138.1'
    }
    
    stages {
        stage('Build Image') {
            steps {
                sh "docker build -t $IMAGE_NAME:$JENKINS_VERSION -t $IMAGE_NAME:latest ."
            }
        }
        stage('Push Image') {
            steps {
                withDockerRegistry([url: '', credentialsId: 'DOCKER_HUB_CREDENTIALS']) {
                    sh "docker push $IMAGE_NAME:$JENKINS_VERSION"
                    sh "docker push $IMAGE_NAME:latest"
                }
            }
        }
    }
    post {
        success {
            slackSend ":information_source: <$env.BUILD_URL|$env.JOB_NAME #$env.BUILD_NUMBER>: Image $IMAGE_NAME:$JENKINS_VERSION has been built and pushed to Docker Hub."
        }
        failure {
            slackSend ":fire: <$env.BUILD_URL|$env.JOB_NAME #$env.BUILD_NUMBER>: Image $IMAGE_NAME:$JENKINS_VERSION failed to build and push to Docker Hub."
        }
    }
}
