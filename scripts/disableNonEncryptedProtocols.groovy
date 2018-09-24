import jenkins.model.Jenkins

HashSet<String> newProtocols = new HashSet<>(Jenkins.instance.getAgentProtocols())
newProtocols.removeAll(Arrays.asList("JNLP3-connect", "JNLP2-connect", "JNLP-connect", "CLI-connect"))
Jenkins.instance.setAgentProtocols(newProtocols)
Jenkins.instance.save()
