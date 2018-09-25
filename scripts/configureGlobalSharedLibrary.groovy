import jenkins.model.Jenkins
import jenkins.plugins.git.GitSCMSource
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever

jenkins = Jenkins.instance

String globalSharedLibraries = System.getenv().GLOBAL_SHARED_LIBRARIES

if (globalSharedLibraries != null && globalSharedLibraries.trim() != '') {
    libraryConfigurations = []

    globalSharedLibraries.tokenize(',').each{ entry ->
        List<String> tokens = entry.tokenize('|')
        name = tokens.getAt(0)
        url = tokens.getAt(1)

        GitSCMSource gitSCMSource = new GitSCMSource(url)
        SCMSourceRetriever scmSourceRetriever = new SCMSourceRetriever(gitSCMSource)
        LibraryConfiguration libraryConfiguration = new LibraryConfiguration(name, scmSourceRetriever)
        libraryConfigurations << libraryConfiguration
    }

    jenkins.getDescriptor('org.jenkinsci.plugins.workflow.libs.GlobalLibraries').get().setLibraries(libraryConfigurations)
    jenkins.save()
}
