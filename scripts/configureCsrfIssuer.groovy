import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.model.Jenkins

jenkins = Jenkins.instance

if (jenkins.getCrumbIssuer() == null) {
    jenkins.setCrumbIssuer(new DefaultCrumbIssuer(true))
    jenkins.save()
}
