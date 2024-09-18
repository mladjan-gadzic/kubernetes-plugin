agent {
    kubernetes {
        defaultContainer 'jnlp-slave'
        yaml '''
apiVersion: v1
kind: Pod
metadata:
  name: jenkins-agent-pod
  namespace: default
spec:
  containers:
    - name: jenkins-agent
      image: eclipse-temurin:21.0.4_7-jdk-jammy
      command:
        - sh
      args:
        - -c
        - tail -f /dev/null
      resources:
        limits:
          memory: 1Gi
          cpu: 2
        requests:
          memory: 1Gi
          cpu: 2
'''
   }
}