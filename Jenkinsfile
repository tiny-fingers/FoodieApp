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
        stage('build docker images') {
            steps {
                sh 'docker build -t foodie-app .'
                sh 'docker tag foodie-app:latest tinyfingersdocker/foodie-app:latest'
                sh 'docker build -t foodie-ui / UI/FoodieAppUI/'
                sh 'docker tag foodie-ui:latest tinyfingersdocker/foodie-ui:latest'
            }
        }
        stage('Push images to dockerhub') {
            steps {
                sh 'docker push tinyfingersdocker/foodie-app:latest'
                sh 'docker push tinyfingersdocker/foodie-ui:latest'
            }
        }
        stage ('Login to EC2') {
            environment {
                SSH_KEY = 'AWS_CREDENTIALS'
                SSH_USER = 'ubuntu'
                EC2_HOST = '52.47.159.117'
            }
            steps {
                sshagent([SSH_KEY]) {
                    sh "ssh ${SSH_USER}@${EC2_HOST} 'echo CONNECTED SUCCESS'"
                }
            }
        }
        stage('Deploy') {
            environment {
                IMAGE_NAME='tinyfingersdocker/foodie-app:latest'
                ENV_FILE_LOCATION='.env'
                SSH_KEY = 'AWS_CREDENTIALS'
                SSH_USER = 'ubuntu'
                EC2_HOST = '52.47.159.117'
            }
            steps {
                sshagent([SSH_KEY]) {
                    sh 'ssh ${SSH_USER}@${EC2_HOST} docker pull ${IMAGE_NAME}'
                    sh 'ssh ${SSH_USER}@${EC2_HOST} sh prep.sh'
                    sh 'ssh ${SSH_USER}@${EC2_HOST} docker run --name foodie-app --env-file ${ENV_FILE_LOCATION} -p 8090:8090 -d ${IMAGE_NAME}'
                }
            }
        }
        stage('Clean up') {
            environment {
                SSH_KEY = 'AWS_CREDENTIALS'
                SSH_USER = 'ubuntu'
                EC2_HOST = '52.47.159.117'
            }
            steps {
                sshagent([SSH_KEY]) {
                    sh 'ssh ${SSH_USER}@${EC2_HOST} docker rm old-foodie-app 2> /dev/null'
                    sh 'ssh ${SSH_USER}@${EC2_HOST} docker container prune -f'
                    sh 'ssh ${SSH_USER}@${EC2_HOST} docker image prune -f'
                }
            }
        }
    }
}