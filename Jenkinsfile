pipeline {
    agent none
    tools {
        maven 'maven'
    }
    stages {
        stage('mvn package') {
            agent any
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('build docker') {
            agent {
                dockerfile {
                    label 'foodie-app'
                }
            }
            stage('Test') {
                steps {
                    sh 'java --version'
                }
            }
        }
    }
}