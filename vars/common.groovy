def sonarCheck() {
  stage('Code Quality') {
    environment {
      SONAR=credentials('SONAR')
    }
    when {
      anyOf {
        expression { env.BRANCH_NAME == "main" }
        expression { env.TAG_NAME ==~ ".*" }
      }
    }
    steps {
      sh '''
            sonar-scanner -Dsonar.host.url=http://172.31.94.112:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} -Dsonar.qualitygate.wait=true
          '''
    }
  }
}
