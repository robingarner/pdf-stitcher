# Version number for this release
VERSION_NUMBER = "3"
# Group identifier for your projects
GROUP = "robingarner.pdfstitcher"
COPYRIGHT = "Robin Garner"

require 'net/http'

require 'buildr/jdepend'
require 'buildr/checkstyle'

repositories.remote << "https://nexus.scu.edu.au/nexus/content/groups/public/"
repositories.remote << "https://nexus.scu.edu.au/nexus/content/repositories/releases"
repositories.remote << "https://nexus.scu.edu.au/nexus/content/repositories/snapshots"


httpclient_version = "4.5.1"
httpcore_version = "4.4.4"
httpmime_version = "4.5.1"
hib_ver = "5.1.0.Final"

v_pdfbox = "2.0.15"

PDFBOX = transitive("org.apache.pdfbox:pdfbox::#{v_pdfbox}")

XERCES = artifact('xerces:xercesImpl:jar:2.9.1')

ARGS4J = artifact('args4j:args4j:jar:2.0.29')

HTTP_CLIENT = artifact("org.apache.httpcomponents:httpclient:jar:#{httpclient_version}")
HTTP_CORE   = artifact("org.apache.httpcomponents:httpcore:jar:#{httpcore_version}")
HTTP_MIME   = artifact("org.apache.httpcomponents:httpmime:jar:#{httpmime_version}")

CMN_LOGGING = artifact("commons-logging:commons-logging:jar:1.2")

NEKOHTML = artifact('net.sourceforge.nekohtml:nekohtml:jar:1.9.22')
#NEKOHTML_XNI = artifact('net.sourceforge.nekohtml:nekohtmlXni:jar:1.9.22')

XML_APIS = artifact('xml-apis:xml-apis:jar:1.3.04')

SLF4J_API = artifact("org.slf4j:slf4j-api:jar:1.7.2")
SLF4J_LOG4J = artifact("org.slf4j:slf4j-log4j12:jar:1.7.2")
LOG4J = artifact('log4j:log4j:jar:1.2.16')

JCABI_MANIFESTS = artifact('com.jcabi:jcabi-manifests:jar:1.1')
JCABI_LOG = artifact('com.jcabi:jcabi-log:jar:0.14')

JCABI = [ JCABI_MANIFESTS, JCABI_LOG ]
  
jackson_version = '2.8.9'
JACKSON_CORE = artifact("com.fasterxml.jackson.core:jackson-core:jar:#{jackson_version}")
JACKSON_DATABIND = artifact("com.fasterxml.jackson.core:jackson-databind:jar:#{jackson_version}")
JACKSON_ANN = artifact("com.fasterxml.jackson.core:jackson-annotations:jar:#{jackson_version}")

JACKSON = [ JACKSON_CORE, JACKSON_DATABIND, JACKSON_ANN ]

GUICE = transitive('com.google.inject:guice:jar:4.2.2')

ONE_JAR = artifact('scu.one-jar:one-jar:jar:0.98-scu.2')

def package_as_one_jar(file_name) #:nodoc:
  file_name = file_name.to_s.sub(/.one_jar$/, ".one.jar")
  jarfile = file_name.to_s.sub(/.one.jar$/, ".jar")
  dependencies = compile.dependencies.map { |d| "-c #{d}"}.join(" ")
  result = Packaging::Java::JarTask.define_task(file_name)
  result.with( 
      :manifest=> { 'Main-Class' => 'com.simontuffs.onejar.Boot'
      }) .merge(ONE_JAR)
      
  result.include(jarfile, :path=>'main/', :as=>'main.jar')
  result.include(compile.dependencies, :path=>'lib/')
  result
end

#
# Collections of dependencies
#
DEPS = [ PDFBOX, ARGS4J, GUICE, SLF4J_LOG4J, LOG4J ] << JACKSON


desc "PDF Stitcher"
define "pdf-stitcher" do
  project.version = VERSION_NUMBER
  project.group = GROUP

  package_with_javadoc # :except=> ['one-jar']

  test.using :testng, :java_args => [ '-Xmx256m' ]

  compile.with(DEPS)
  package(:jar).with( 
    :manifest=> { 'Main-Class' => "#{GROUP}.Main"
      })
  package(:one_jar)

  checkstyle.source_paths << _(:src, :main, :java)
end
