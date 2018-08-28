pipeline {
    agent {
        kubernetes {
            cloud 'openshift'
            label 'maven'
        }
    }
    environment {
        MAVEN_OPTS='-Dfile.encoding=UTF-8'
        JDBC_DRIVER='org.mariadb.jdbc.Driver'
        JDBC_URL='jdbc:mysql://mariadb.discussment/sampledb?serverTimezone=UTC'
        JDBC_USER='userSSK'
        JDBC_PASSWORD='CXAI5KegsAOnM5dM'
        JDBC_DATABASE='sampledb'
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
                sh '/bin/bash -c "mvn clean install -P jpa -Dmaven.wagon.http.ssl.insecure=true"'
                sh '/bin/bash -c "mvn clean install -f ./discussment-examples/article/pom.xml -Dskip.flyway=true -DskipTests=true -Dmaven.wagon.http.ssl.insecure=true"'
                sh '/bin/bash -c "mvn clean install -f ./discussment-examples/forum/pom.xml -Dskip.flyway=true -DskipTests=true -Dmaven.wagon.http.ssl.insecure=true"'
                sh '/bin/bash -c "mvn clean install -f ./discussment-examples/spring/pom.xml -Dskip.flyway=true -DskipTests=true -Dmaven.wagon.http.ssl.insecure=true"'
            }
        }
    }
}