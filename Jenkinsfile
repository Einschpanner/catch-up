pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew build'
      }
    }

    stage('deploy') {
    when { branch 'develop' }
      steps {
        sh 'eb deploy'
      }
    }

  }

  post {
          success {
              # 빌드의 결과가 성공일경우
              resultSlackSend("good", "SUCCESS")
          }
          failure {
              # 빌드의 결과가 실패일경우
              resultSlackSend("danger", "FAILURE")
          }
          aborted {
              # 빌드를 중간에 멈추는 경우
              resultSlackSend("warning", "ABORTED")
          }
      }
}

def resultSlackSend(bar_color, result) {
  slackSend color: bar_color, message:"${env.JOB_NAME} - #${env.BUILD_NUMBER} ${result} after ${currentBuild.durationString.split(" and")[0]} (<${env.BUILD_URL}|Open>)"
}