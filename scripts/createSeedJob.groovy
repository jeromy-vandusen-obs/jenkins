import hudson.model.FreeStyleProject
import hudson.plugins.git.*
import jenkins.model.Jenkins

import javaposse.jobdsl.plugin.*

jenkins = Jenkins.instance

def env = System.getenv()

if (env.GENERATE_SEED_JOB == 'true' && ! jenkins.getJobNames().contains(env.SEED_JOB_NAME)) {
    job = jenkins.createProject(FreeStyleProject, env.SEED_JOB_NAME)
    job.displayName = env.SEED_JOB_NAME
    job.scm = new GitSCM(env.SEED_JOB_GIT_URL)
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
