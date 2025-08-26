pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'feature', url: 'https://github.com/daniilsuzdaleujazzteam/demo-swing'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }
}
