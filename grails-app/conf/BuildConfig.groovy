grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        pom (true)
        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        runtime 'mysql:mysql-connector-java:5.1.20'
        build ":gson:2.2.2"
        build ":orient-commons:1.3.0"
        build ":orientdb-core:1.3.0"
        build ":orientdb-client:1.3.0"
        build ":orientdb-enterprise:1.3.0"
        build ":orientdb-object:1.3.0"
        build ":orientdb-graphdb:1.3.0"
        build ":gremlin-java:2.2.0-SNAPSHOT"
        build ":blueprints-orient-graph:2.2.0-SNAPSHOT"
        build ":blueprints-core:2.2.0-SNAPSHOT"
        build ":hgbdbje:1.2"
        build ":hgdb:1.2"
        build ":je:5.0.34"
        runtime ":gremlin-java:2.2.0-SNAPSHOT"
        runtime ":gremlin-groovy:2.2.0-SNAPSHOT"
        //build "org.springframework.data:spring-mongodb:1.2.0.RELEASE"
        build ":mongo:2.9.1"
        // runtime 'mysql:mysql-connector-java:5.1.20'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.0"
        runtime ":resources:1.1.6"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:$grailsVersion"

        runtime ":database-migration:1.1"

        compile ':cache:1.0.0'
    }
}
