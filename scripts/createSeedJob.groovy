import hudson.model.FreeStyleProject
import hudson.plugins.git.*
import jenkins.model.Jenkins

import javaposse.jobdsl.plugin.*

jenkins = Jenkins.instance

String seedJobs = System.getenv().SEED_JOBS

if (seedJobs != null && seedJobs.trim() != '') {
    seedJobs.tokenize(',').each { entry ->
        List<String> tokens = entry.tokenize('|')
        name = tokens.getAt(0)
        url = tokens.getAt(1)

        if (! jenkins.getJobNames().contains(name)) {
            job = jenkins.createProject(FreeStyleProject, name)
            job.scm = new GitSCM(url)
            job.scm.branches = [new BranchSpec('*/master')]

            builder = new ExecuteDslScripts()
            builder.setTargets('seedJobs.groovy')
            builder.setIgnoreExisting(false)
            builder.setRemovedJobAction(RemovedJobAction.DELETE)
            builder.setRemovedViewAction(RemovedViewAction.DELETE)
            builder.setLookupStrategy(LookupStrategy.JENKINS_ROOT)

            job.buildersList.add(builder)
            job.save()
        }
    }
}
