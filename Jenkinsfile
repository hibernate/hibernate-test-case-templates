pipeline {
    agent {
        label 'Worker&&Containers'
    }
    tools {
        jdk 'OpenJDK 17 Latest'
        maven 'Apache Maven 3.9'
    }
    options {
        disableConcurrentBuilds(abortPrevious: true)
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn -B -q clean package -DskipTests=true"
            }
        }
        stage('Test') {
            steps {
                sh "mvn -B verify"
            }
        }
    }
}
