pipeline {
    agent {
        docker {
            image 'docker pull gradle:jdk18-alpine'
        }
    }
    environment {
        CI = 'true'
    }
    stages {
        parallel {
            stage('Build key-generator') {
                steps {
                    sh 'cd key-generator && ./gradlew build'
                }
            }
            stage('Build tiny-url-api') {
                steps {
                    sh 'cd tiny-url-api && ./gradlew build'
                }
            }
        }
    }
}
