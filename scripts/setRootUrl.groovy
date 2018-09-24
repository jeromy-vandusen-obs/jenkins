import jenkins.model.JenkinsLocationConfiguration

def env = System.getenv()

jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()
jenkinsLocationConfiguration.setUrl(env.ROOT_URL) 
jenkinsLocationConfiguration.save()
