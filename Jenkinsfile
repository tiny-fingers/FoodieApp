pipeline {
    agent any
    stages {
        stage ('Clean') {
            environment {
                SSH_KEY = 'AWS_CREDENTIALS'
                SSH_USER = 'ubuntu'
                EC2_HOST = '16.16.232.87'
            }
            steps {
                sshagent([SSH_KEY]) {
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh "ssh ${SSH_USER}@${EC2_HOST} 'docker stop \$(docker ps -a -q)'"
                        sh "ssh ${SSH_USER}@${EC2_HOST} 'docker rm \$(docker ps -a -q)'"
                        sh "ssh ${SSH_USER}@${EC2_HOST} 'docker rmi \$(docker images -a -q)'"
                    }
                }
            }
        }
        stage('Login to dockerhub') {
            environment {
                DOCKERHUB_CREDENTIALS = credentials('DOCKERHUB_CREDENTIALS')
            }
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }
        stage('Deploy server') {
            environment {
                IMAGE_NAME='tinyfingersdocker/foodie-app:latest'
                ENV_FILE_LOCATION='.env'
                SSH_KEY = 'AWS_CREDENTIALS'
                SSH_USER = 'ubuntu'
                EC2_HOST = '16.16.232.87'
                DATABASE_CREDENTIALS = credentials('DATABASE_CREDENTIALS')
            }
            steps {
                sshagent([SSH_KEY]) {
                    sh 'ssh ${SSH_USER}@${EC2_HOST} docker run -d -p "8090:8090" --network bridge -e SERVER_PORT=8090 -e CROSS_ORIGIN_URL=http://16.16.232.87:8000 -e DATABASE_USER=${DATABASE_CREDENTIALS_USR} -e DATABASE_PASSWORD=${DATABASE_CREDENTIALS_PSW} ${IMAGE_NAME}'
                }
            }
        }

        stage('Deploy ui') {
            environment {
                IMAGE_NAME='tinyfingersdocker/foodie-ui:latest'
                ENV_FILE_LOCATION='.env'
                SSH_KEY = 'AWS_CREDENTIALS'
                SSH_USER = 'ubuntu'
                EC2_HOST = '16.16.232.87'
                DATABASE_CREDENTIALS = credentials('DATABASE_CREDENTIALS')
            }
            steps {
                sshagent([SSH_KEY]) {
                    sh 'ssh ${SSH_USER}@${EC2_HOST} docker run -d -p "8000:80" --network bridge ${IMAGE_NAME}'
                }
            }
        }
    }
}