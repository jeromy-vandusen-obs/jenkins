import jenkins.model.Jenkins

jenkins = Jenkins.instance

if (jenkins.getSlaveAgentPort() != -1) {
    jenkins.setSlaveAgentPort(-1)
    jenkins.save()
}
