ThisBuild / scalaVersion := "3.8.3"

lazy val root = (project in file("."))
  .settings(
    name := "AnchalYadav_ScalaPracticals",

    libraryDependencies += "org.scalanlp" %% "breeze" % "2.1.0",
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10"
  )