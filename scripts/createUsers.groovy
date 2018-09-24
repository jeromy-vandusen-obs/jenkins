import jenkins.model.*
import hudson.security.*

def env = System.getenv()

Jenkins.instance.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
Jenkins.instance.setAuthorizationStrategy(new GlobalMatrixAuthorizationStrategy())

def user = Jenkins.instance.getSecurityRealm().createAccount(env.JENKINS_USER, env.JENKINS_PASS)
user.save()

Jenkins.instance.getAuthorizationStrategy().add(Jenkins.ADMINISTER, env.JENKINS_USER)
Jenkins.instance.save()
