def call() {
  pipeline {
    agent any

    options {
      ansiColor('xterm')
    }

    parameters {
      choice(name: 'ENV', choices: ['', 'dev', 'prod'], description: 'Pick Env')
    }

    stages {

      stage('Terraform Apply') {
        steps {
          sh '''
          cd mutable
          terrafile 
          terraform init -backend-config=env/${ENV}-backend.tfvars
          terraform apply -auto-approve -var-file=env/${ENV}.tfvars
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