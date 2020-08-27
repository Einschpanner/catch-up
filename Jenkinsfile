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
        sh 'aws s3 cp /var/jenkins_home/workspace/CatchUp_EB_Deploy_master/build/libs/catch-up-0.0.1-SNAPSHOT.jar s3://catchup-jenkins/catch-up-0.0.1-SNAPSHOT.jar --region ap-northeast-2'
      }
    }

    stage('deploy') {
      steps {
        echo 'start deploy..'
      }
    }

  }
}
