pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: jnlp
            image: jenkins/inbound-agent:latest-jdk21
            resources:
              limits:
                cpu: 500m
                memory: 256Mi
              requests:
                cpu: 200m
                memory: 256Mi
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