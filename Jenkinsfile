pipeline {
    // may need to use docker agent with target image
    agent any
    environment {
        CI = 'true'
    }
    stages {
        // probably not needed
        stage('Checkout') {
            steps {
                git branch: 'jenkins', url: 'https://github.com/vinay-verma/system-design-tiny-url.git'
            }
        }
        stage('Build key-generator') {
            steps {
                sh 'java --version'
                sh 'cd key-generator && ./gradlew --no-daemon --info --stacktrace clean build'
            }
        }
        stage('Build tiny-url-api') {
            steps {
                sh 'java --version'
                sh 'cd tiny-url-api && ./gradlew build'
            }
        }
    }
}
