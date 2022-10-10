def call() {
  pipeline {
    agent any

    options {
      ansiColor('xterm')
    }

    stages {

      common.sonarCheck()

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