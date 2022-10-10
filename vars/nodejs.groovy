def call() {
  node() {

      stage('Download Dependencies') {
        sh '''
            npm install
          '''
      }


      common.sonarCheck()

//      stage('Make release - Upload Artifact') {
//        when {
//          expression { env.TAG_NAME ==~ ".*" }
//        }
//        steps {
//          sh '''
//            echo artifacts
//          '''
//        }
//      }

    post {
      always {
        cleanWs()
      }
    }

  }

}