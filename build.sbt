name := "apex-utils"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

crossScalaVersions := Seq("2.10.4", "2.11.1")

libraryDependencies ++= Seq(
	"org.freehep" % "freehep-io" % "2.2.2",
	"joda-time" % "joda-time" % "2.5",
	"org.joda" % "joda-convert" % "1.7"
)

publishTo := {
//  val nexus = "https://oss.sonatype.org/"
  val repo = "../mvn-repo/"
  if (isSnapshot.value)
//    Some("snapshots" at nexus + "content/repositories/snapshots")
	Some(Resolver.file("file", new File(repo + "snapshots") ) ) 
  else
//    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
	Some(Resolver.file("file", new File(repo + "releases") ) )
}

//credentials += Credentials("Sonatype Nexus Repository Manager", "nexus.scala-tools.org", "admin", "admin123")
//credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")