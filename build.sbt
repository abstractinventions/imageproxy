organization := "com.abstractinventions"

name := "imageProxy"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.2"

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

libraryDependencies ++= Seq(
   "net.databinder" %% "unfiltered-netty-server" % "0.6.8",
   "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
   "org.clapper" %% "avsl" % "1.0.1",
   "net.databinder" %% "unfiltered-spec" % "0.6.8" % "test"
)

resolvers ++= Seq(
  "jboss repo" at "http://repository.jboss.org/nexus/content/groups/public-jboss/"
)
