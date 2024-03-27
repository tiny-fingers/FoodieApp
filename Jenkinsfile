pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Build Docker image') {
            steps {
                sh 'docker build -t foodie-app:latest .'
            }
            post {
                success {
                    echo 'Docker image built'
                }
            }
        }
    }
}