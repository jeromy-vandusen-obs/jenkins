import jenkins.model.JenkinsLocationConfiguration

jenkinsLocationConfiguration = JenkinsLocationConfiguration.get()
if (jenkinsLocationConfiguration.getUrl() == null) {
    def env = System.getenv()

    jenkinsLocationConfiguration.setUrl(env.ROOT_URL)
    jenkinsLocationConfiguration.save()
}
