podTemplate(
  containers: [
    containerTemplate(name: 'jnlp', image: 'jenkins/inbound-agent:latest-jdk21', args: '${computer.jnlpmac} ${computer.name}')
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