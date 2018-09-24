import jenkins.model.Jenkins;

pluginManager = Jenkins.instance.pluginManager
updateCenter = Jenkins.instance.updateCenter

pluginManager.plugins.each {
    it.disable()
}

deployed = false

[
    "ace-editor",
    "ant",
    "antisamy-markup-formatter",
    "apache-httpcomponents-client-4-api",
    "authentication-tokens",
    "bouncycastle-api",
    "branch-api",
    "build-timeout",
    "cloudbees-folder",
    "command-launcher",
    "credentials",
    "credentials-binding",
    "display-url-api",
    "docker-commons",
    "docker-workflow",
    "durable-task",
    "email-ext",
    "git",
    "git-client",
    "github",
    "github-api",
    "github-branch-source",
    "git-server",
    "gradle",
    "handlebars",
    "jackson2-api",
    "jdk-tool",
    "job-dsl",
    "jquery-detached",
    "jsch",
    "junit",
    "ldap",
    "mailer",
    "mapdb-api",
    "matrix-auth",
    "matrix-project",
    "momentjs",
    "pam-auth",
    "pipeline-build-step",
    "pipeline-github-lib",
    "pipeline-graph-analysis",
    "pipeline-input-step",
    "pipeline-milestone-step",
    "pipeline-model-api",
    "pipeline-model-declarative-agent",
    "pipeline-model-definition",
    "pipeline-model-extensions",
    "pipeline-rest-api",
    "pipeline-stage-step",
    "pipeline-stage-tags-metadata",
    "pipeline-stage-view",
    "plain-credentials",
    "resource-disposer",
    "scm-api",
    "script-security",
    "slack",
    "ssh-credentials",
    "ssh-slaves",
    "structs",
    "subversion",
    "timestamper",
    "token-macro",
    "workflow-aggregator",
    "workflow-api",
    "workflow-basic-steps",
    "workflow-cps",
    "workflow-cps-global-lib",
    "workflow-durable-task-step",
    "workflow-job",
    "workflow-multibranch",
    "workflow-scm-step",
    "workflow-step-api",
    "workflow-support",
    "ws-cleanup"
].each {
    if (! pluginManager.getPlugin(it)) {
        deployment = updateCenter.getPlugin(it).deploy(true)
        deployment.get()
    }

    activatePlugin(pluginManager.getPlugin(it))
}

def activatePlugin(plugin) {
    if (! plugin.isEnabled()) {
        plugin.enable()
        deployed = true
    }
    
    plugin.getDependencies().each {
        activatePlugin(pluginManager.getPlugin(it.shortName))
    }
}
