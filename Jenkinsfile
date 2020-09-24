pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew clean build'
      }
      post {
        success {
            resultSlackSend("CI:Build", "good", "SUCCESS")
        }
        failure {
            resultSlackSend("CI:Build", "danger", "FAILURE")
        }
        aborted {
            resultSlackSend("CI:Build", "warning", "ABORTED")
        }
      }
    }

    stage('deploy') {
    when { branch 'develop' }
      steps {
        echo 'start deploy to develop..'
        sh 'eb deploy catch-up-dev'
      }
      post {
        success {
            resultSlackSend("CD:Deploy", "good", "SUCCESS")
        }
        failure {
            resultSlackSend("CD:Deploy", "danger", "FAILURE")
        }
        aborted {
            resultSlackSend("CD:Deploy", "warning", "ABORTED")
        }
      }
    }
  }
}

def resultSlackSend(stage_name ,bar_color, result) {
  slackSend color: bar_color, message:"${env.JOB_NAME} - ${stage_name} #${env.BUILD_NUMBER}  ${result} after ${currentBuild.durationString.split(" and")[0]} (<${env.BUILD_URL}|Open>)"
}
