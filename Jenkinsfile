pipeline {
    agent any

    parameters {
        string(name: 'SUITE', defaultValue: 'testng-smoke.xml', description: 'TestNG suite XML file to run')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run browser in headless mode')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                bat "mvn -B test -DsuiteXmlFile=${params.SUITE} -Dheadless=${params.HEADLESS}"
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/screenshots/**', fingerprint: true
        }
    }
}