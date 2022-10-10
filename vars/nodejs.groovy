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
        environment {
          SONAR=credentials('SONAR')
          COMPONENT = env.COMPONENT
        }
        when {
          anyOf {
            expression { env.BRANCH_NAME == "main" }
            expression { env.TAG_NAME ==~ ".*" }
          }
        }
        steps {
          sh '''
env
            sonar-scanner -Dsonar.host.url=http://172.31.94.112:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT}
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