import jenkins.model.Jenkins
import jenkins.security.s2m.*

Jenkins.instance.injector.getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false);
Jenkins.instance.save()
