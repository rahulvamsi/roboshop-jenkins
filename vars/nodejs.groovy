def call() {
  pipeline {
    agent any

    options {
      ansiColor('xterm')
    }

    stages {

      stage('Download Dependencies') {
        steps {
          sh '''
            npm install
          '''
        }
      }

      stage('Code Quality') {
        when {
          anyOf {
            expression { BRANCH_NAME == "main" }
            expression { TAG_NAME ==~ ".*" }
          }
        }
        steps {
          sh '''
            echo Code 
          '''
        }
      }

      stage('Make release - Upload Artifact') {
        when {
          expression { TAG_NAME ==~ ".*" }
        }
        steps {
          sh '''
            echo artifacts
          '''
        }
      }

    }

    post {
      always {
        cleanWs()
      }
    }

  }

}