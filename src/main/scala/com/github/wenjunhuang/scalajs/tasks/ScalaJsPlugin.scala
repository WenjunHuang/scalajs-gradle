package com.github.wenjunhuang.scalajs.tasks

import org.gradle.api.{Plugin, Project}

/**
  * Created by rick on 2017/6/25.
  */
class ScalaJsPlugin extends Plugin[Project] {
    override def apply(project: Project): Unit = {
        project.getExtensions.create("compileScala2Js", classOf[CompileScala2JsExtension])
        project.getTasks.create("compileScala2Js")
    }
}

class CompileScala2JsExtension {

}
