import hudson.util.Secret
import jenkins.model.Jenkins

import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import org.jenkinsci.plugins.plaincredentials.*
import org.jenkinsci.plugins.plaincredentials.impl.*

String credentials = System.getenv().CREDENTIALS

if (credentials != null && credentials.trim() != '') {
    domain = Domain.global()
    store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider').getAt(0).getStore()

    credentials.tokenize(',').each { entry ->
        List<String> tokens = entry.tokenize('|')
        type = tokens.getAt(0)
        id = tokens.getAt(1)

        if (type == 'UsernamePassword') {
            username = tokens.getAt(2)
            password = tokens.getAt(3)
            description = tokens.getAt(4)
            store.addCredentials(domain, new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, id, description, username, password))
        }
        else if (type == 'SecretText') {
            secret = tokens.getAt(2)
            description = tokens.getAt(3)
            store.addCredentials(domain, new StringCredentialsImpl(CredentialsScope.GLOBAL, id, description, Secret.fromString(secret)))
        }
    }
}
