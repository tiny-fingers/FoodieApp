pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('DOCKERHUB_CREDENTIALS')
     }
    stages {
        stage('Login to dockerhub') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }
        stage('build docker') {
            steps {
                sh 'docker buildx build --platform linux/amd64,linux/arm64 -t foodie-app .'
                sh 'docker tag foodie-app:latest tinyfingersdocker/foodie-app:latest'
            }
        }
        stage('Push image to dockerhub') {
            steps {
                sh 'docker push tinyfingersdocker/foodie-app:latest'
            }
        }
    }
}