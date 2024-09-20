podTemplate(
  agentContainer: 'agent-container',
  agentInjection: true,
  containers: [
    containerTemplate(name: 'agent-container', image: 'jenkins/inbound-agent:latest-jdk11'),
    containerTemplate(name: 'jnlp', image: 'jenkins/inbound-agent:latest-jdk11', args: '${computer.jnlpmac} ${computer.name}')
  ]
) {
  node(POD_LABEL) {
    stage('example1') {
      container('jnlp') {
        sh 'ls -la'
      }
    }
  }
}