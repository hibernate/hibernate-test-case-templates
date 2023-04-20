pipeline {
    agent {
        label 'Worker&&Containers'
    }
    tools {
        jdk 'OpenJDK 11 Latest'
        maven 'Apache Maven 3.8'
    }
    options {
        disableConcurrentBuilds(abortPrevious: true)
    }
    stages {
        stage('Build') {
            steps {
                sh "./ci/build-all.sh"
            }
        }
    }
}
