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
            expression { env.BRANCH_NAME == "main" }
            expression { env.TAG_NAME ==~ ".*" }
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
          expression { env.TAG_NAME ==~ ".*" }
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