pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('mvn package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('build docker') {
            steps {
                sh 'docker build -t foodie-app .'
                sh 'docker tag foodie-app:latest tinyfingersdocker/foodie-app:latest'
                sh 'docker push tinyfingersdocker/foodie-app:latest'
            }
        }

    }
}