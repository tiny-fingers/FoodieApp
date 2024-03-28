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
            }
        }
    }
}