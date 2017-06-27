package com.github.wenjunhuang.scalajs

import com.github.wenjunhuang.scalajs.tasks.CompileScala2JsTask
import org.gradle.api.{Plugin, Project}

/**
  * Created by rick on 2017/6/25.
  */
class ScalaJsPlugin extends Plugin[Project] {
    override def apply(project: Project): Unit = {
        project.getTasks.create("compileScala2Js", classOf[CompileScala2JsTask])
    }
}
