pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: jnlp
            image: jenkins/inbound-agent:latest-jdk17
        '''
    }
  }
  stages {
    stage('example') {
      steps {
        container('jnlp') {
          sh 'tail -f /dev/null'
        }
      }
    }
  }
}