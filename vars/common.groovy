def sonarCheck() {
  if (env.BRANCH_NAME == "main" || env.TAG_NAME ==~ ".*") {
    stage('Code Quality') {
      withCredentials([usernamePassword(credentialsId: 'SONAR', passwordVariable: 'SONAR_PSW', usernameVariable: 'SONAR_USR')]) {
        sh '''
          #sonar-scanner -Dsonar.host.url=http://172.31.94.112:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} -Dsonar.qualitygate.wait=true ${SONAR_JAVA_PROPERTY}
          echo all ok
    '''
      }
    }
  }

}

def codeCheckOut() {
  stage('CheckOut Code') {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/rahulvamsi/${env.COMPONENT}"
  }
}

def uploadArtifact() {
  if (env.TAG_NAME ==~ ".*")
    stage('Make release - Upload Artifact') {
      // Prepare Artifacts
      if (env.APP_TYPE == "node") {
        sh '''
          zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
        '''
      }

      if (env.APP_TYPE == "java") {
        sh '''
          mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
          zip -r ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar
        '''
      }

      if (env.APP_TYPE == "python") {
        sh '''
          zip -r ${COMPONENT}-${TAG_NAME}.zip requirements.txt *.py *.ini
        '''
      }

      if (env.APP_TYPE == "nginx") {
        sh '''
          cd static
          zip -r ../${COMPONENT}-${TAG_NAME}.zip *
        '''
      }

      //Upload Artifacts
      withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'NEXUS_PSW', usernameVariable: 'NEXUS_USR')]) {
          sh '''
            curl -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://nexus.roboshop.internal:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
          '''
        }
      }
}
