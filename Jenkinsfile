pipeline {
    agent any
    environment {
        CI = 'true'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'jenkins', url: 'https://github.com/vinay-verma/system-design-tiny-url.git'
            }
        }
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
