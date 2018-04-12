name := "akka-x"

version := "1.0"

scalaVersion := "2.11.8"


import Dependencies._
import ProjectSettings._


lazy val commonUtil = BaseProject("common-util").settings(
  libraryDependencies ++= compileDependencies(akkaHttp.value ++ akka.value ++
    json4sNative.value ++ logback.value ++ typesafeConfig.value ++ slf4j.value ++ log4j.value ++
    logback.value ++ json4sNative.value ++ json4sEx.value ++ jodaDate.value)
    ++ testDependencies(mockito.value ++ scalaTest.value ++ spec2.value),

  parallelExecution in Test := false
)


lazy val remotingActorSelection = BaseProject("remoting-actor-selection").settings(
  libraryDependencies ++= compileDependencies(akkaHttp.value ++ akka.value ++ akkaRemote.value)
    ++ testDependencies(Nil),
  parallelExecution in Test := false).dependsOn(commonUtil)

lazy val remotingActorDeployment = BaseProject("remoting-actor-deployment").settings(
  libraryDependencies ++= compileDependencies(akkaHttp.value ++ akka.value ++ akkaRemote.value)
    ++ testDependencies(Nil),
  parallelExecution in Test := false).dependsOn(commonUtil)
