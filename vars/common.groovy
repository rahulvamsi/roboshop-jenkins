def sonarCheck() {
  if (env.BRANCH_NAME == "main") {
    stage('Code Quality') {
      withCredentials([usernamePassword(credentialsId: 'SONAR', passwordVariable: 'SONAR_PSW', usernameVariable: 'SONAR_USR')]) {
        sh '''
      sonar-scanner -Dsonar.host.url=http://172.31.94.112:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} -Dsonar.qualitygate.wait=true
    '''
      }
    }
  }

}

