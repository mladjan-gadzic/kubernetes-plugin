pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: jnlp
            image: jenkins/inbound-agent:latest-jdk11
        '''
    }
  }
  stages {
    stage('example') {
      steps {
        container('jnlp') {
          sh 'ls -la'
        }
      }
    }
  }
}