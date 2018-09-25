import jenkins.model.*
import hudson.model.User
import hudson.security.*

def env = System.getenv()

jenkins = Jenkins.instance

if (User.getById(env.JENKINS_USER, false) == null) {
    jenkins.setSecurityRealm(new HudsonPrivateSecurityRealm(false, false, null))
    jenkins.setAuthorizationStrategy(new GlobalMatrixAuthorizationStrategy())

    def user = jenkins.getSecurityRealm().createAccount(env.JENKINS_USER, env.JENKINS_PASS)
    user.save()

    jenkins.getAuthorizationStrategy().add(Jenkins.ADMINISTER, env.JENKINS_USER)
    jenkins.save()
}
