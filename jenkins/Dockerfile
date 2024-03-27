FROM ubuntu:latest

USER root

RUN apt-get update -y && apt-get upgrade -y

RUN apt-get install ca-certificates curl -y
RUN install -m 0755 -d /etc/apt/keyrings && curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
RUN chmod a+r /etc/apt/keyrings/docker.asc

RUN echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
      $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
    tee /etc/apt/sources.list.d/docker.list > /dev/null

RUN apt-get update -y

RUN apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y

RUN apt-get update && \
    apt-get install -y wget apt-transport-https ca-certificates curl gnupg2 software-properties-common openjdk-17-jdk

RUN wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | apt-key add - && \
    sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'

RUN apt-get update && \
    apt-get install -y jenkins

RUN usermod -aG docker jenkins

# Expose the Jenkins port
EXPOSE 8080

# Start Jenkins
CMD ["jenkins"]