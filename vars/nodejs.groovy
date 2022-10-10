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
env
            npm install
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