import jenkins.model.Jenkins

jenkins = Jenkins.instance

jenkins.getDescriptor("jenkins.CLI").get().setEnabled(false)
jenkins.save()
