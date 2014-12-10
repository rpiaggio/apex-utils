name := "apex-utils"

organization := "com.rpiaggio"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.4"

crossScalaVersions := Seq("2.10.4", "2.11.4")

libraryDependencies ++= Seq(
	"org.freehep" % "freehep-io" % "2.2.2",
	"joda-time" % "joda-time" % "2.5",
	"org.joda" % "joda-convert" % "1.7",
	"com.typesafe.play" %% "play" % "2.3.6",
	"com.typesafe.play" %% "play-ws" % "2.3.6",
	"com.typesafe.slick" %% "slick" % "2.1.0",
	"com.typesafe.play" %% "play-slick" % "0.8.0"
)

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
//  val repo = "../mvn-repo/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
//	Some(Resolver.file("file", new File(repo + "snapshots") ) ) 
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
//	Some(Resolver.file("file", new File(repo + "releases") ) )
}

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>http://rpiaggio.com/apex-utils</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:rpiaggio/apex-utils.git</url>
    <connection>scm:git:git@github.com:rpiaggio/apex-utils.git</connection>
  </scm>
  <developers>
    <developer>
      <id>rpiaggio</id>
      <name>Raul Piaggio</name>
      <url>http://rpiaggio.com</url>
    </developer>
  </developers>)
