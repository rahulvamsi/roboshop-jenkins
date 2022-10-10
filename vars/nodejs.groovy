def call() {
  node() {

    stage('CheckOut Code') {
      sh 'rm -rf *'
      git branch: 'main', url: 'https://github.com/raghudevopsb66/cart'
    }

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

  }

}