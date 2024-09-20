podTemplate(
  agentContainer: 'agent-container',
  agentInjection: true,
  containers: [
    containerTemplate(name: 'agent-container', image: 'jenkins/inbound-agent:latest-jdk11')
  ]
) {
  node(POD_LABEL) {
    stage('example1') {
      container('agent-container') {
        sh 'ls -la'
      }
    }
  }
}