import jenkins.model.Jenkins

jenkins = Jenkins.instance

HashSet<String> newProtocols = new HashSet<>(jenkins.getAgentProtocols())
if (newProtocols.removeAll(Arrays.asList("JNLP3-connect", "JNLP2-connect", "JNLP-connect", "CLI-connect"))) {
    jenkins.setAgentProtocols(newProtocols)
    jenkins.save()
}
