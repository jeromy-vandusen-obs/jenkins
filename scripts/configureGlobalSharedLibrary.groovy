import jenkins.model.Jenkins
import jenkins.plugins.git.GitSCMSource
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever

def env = System.getenv()

if (env.CONFIGURE_GLOBAL_LIB == 'true') {
    jenkins = Jenkins.instance
    def globalLibraries = jenkins.getDescriptor('org.jenkinsci.plugins.workflow.libs.GlobalLibraries')
    List libraryConfigurations = []

    names = env.GLOBAL_LIB_NAME.tokenize(',')
    gitUrls = env.GLOBAL_LIB_GIT_URL.tokenize(',')
    for (i = 0; i < names.size(); i ++) {
        GitSCMSource gitSCMSource = new GitSCMSource(gitUrls.get(i))
        SCMSourceRetriever scmSourceRetriever = new SCMSourceRetriever(gitSCMSource)
        LibraryConfiguration libraryConfiguration = new LibraryConfiguration(names.get(i), scmSourceRetriever)
        libraryConfigurations << libraryConfiguration
    }

    globalLibraries.get().setLibraries(libraryConfigurations)
    jenkins.save()
}
