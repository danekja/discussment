pipeline {
    agent {
        kubernetes {
            cloud 'openshift'
            label 'maven'
        }
    }
    environment {
        MAVEN_OPTS='-Dfile.encoding=UTF-8'
    }
    stages {
        stage('checkout') {
            steps {
                checkout scm
                //git branch: '${BRANCH}', changelog: false, credentialsId: 'jenkins-ssh', poll: false, url: 'git@github.com:danekja/discussment.git'
            }
        }

        stage('build') {
            steps {
                sh '/bin/bash -c "mvn clean install -Dskip.flyway=true -DskipTests=true -Dmaven.wagon.http.ssl.insecure=true"'
                sh '/bin/bash -c "mvn clean install -f ./discussment-examples/article/pom.xml -Dskip.flyway=true -DskipTests=true -Dmaven.wagon.http.ssl.insecure=true"'
                sh '/bin/bash -c "mvn clean install -f ./discussment-examples/forum/pom.xml -Dskip.flyway=true -DskipTests=true -Dmaven.wagon.http.ssl.insecure=true"'
                sh '/bin/bash -c "mvn clean install -f ./discussment-examples/spring/pom.xml -Dskip.flyway=true -DskipTests=true -Dmaven.wagon.http.ssl.insecure=true"'
            }
        }
    }
}