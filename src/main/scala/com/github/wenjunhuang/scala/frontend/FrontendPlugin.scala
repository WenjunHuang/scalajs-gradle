package com.github.wenjunhuang.scala.frontend

import org.gradle.api.{Plugin, Project}

class FrontendPlugin extends Plugin[Project]{
    override def apply(project: Project): Unit = {
       val fontend = project.getTasks.create("scalaFrontend")
    }
}
