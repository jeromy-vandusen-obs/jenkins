import jenkins.model.Jenkins

Jenkins.instance.setSlaveAgentPort(-1);
Jenkins.instance.save()
