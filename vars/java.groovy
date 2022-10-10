def call() {
  node() {
    common.codeCheckOut()
    stage('Download Dependencies') {
      sh '''
        mvn clean package 
      '''
    }
    common.sonarCheck()

  }
}
