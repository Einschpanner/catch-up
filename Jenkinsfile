pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew build'
      }
    }

    stage('upload') {
      steps {
        echo 'start upload..'
      }
    }

    stage('deploy') {
      steps {
        echo 'start deploy..'
      }
    }

  }
}
