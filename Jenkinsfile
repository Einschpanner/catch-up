pipeline {
  agent any
  stages {
    stage('deploy') {
    when { branch 'develop' }
      steps {
        echo 'start deploy to develop..'
        sh 'eb deploy catch-up-dev'
      }
      post {
        success {
            resultSlackSend("good", "SUCCESS")
        }
        failure {
            resultSlackSend("danger", "FAILURE")
        }
        aborted {
            resultSlackSend("warning", "ABORTED")
        }
      }
    }
  }
}

def resultSlackSend(bar_color, result) {
  slackSend color: bar_color, message:"${env.JOB_NAME} - #${env.BUILD_NUMBER} ${result} after ${currentBuild.durationString.split(" and")[0]} (<${env.BUILD_URL}|Open>)"
}
