pipeline {
  agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        metadata:
          labels:
            some-label: some-label-value
        spec:
          containers:
          - name: maven
            image: maven:3.9.9-eclipse-temurin-17
            resources:
              limits:
                memory: "512Mi"
                cpu: "200m"
              requests:
                memory: "512Mi"
                cpu: "200m"
            command:
            - cat
            tty: true
          - name: busybox
            image: busybox
            resources:
              limits:
                memory: "512Mi"
                cpu: "200m"
              requests:
                memory: "512Mi"
                cpu: "200m"
            command:
            - cat
            tty: true
        '''
      retries 2
    }
  }
  stages {
    stage('Run maven') {
      steps {
        container('maven') {
          sh 'mvn -version'
        }
        container('busybox') {
          sh '/bin/busybox'
        }
      }
    }
  }
}