pipeline {
    agent any
    stages {
        stage('build docker') {
            steps {
                sh 'docker build -t foodie-app .'
                sh 'docker tag foodie-app:latest tinyfingersdocker/foodie-app:latest'
                sh 'docker push tinyfingersdocker/foodie-app:latest'
            }
        }

    }
}