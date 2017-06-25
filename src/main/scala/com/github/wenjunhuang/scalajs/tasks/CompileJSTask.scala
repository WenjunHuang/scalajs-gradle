package com.github.wenjunhuang.scalajs.tasks

import org.gradle.api.tasks.{Input, TaskAction}
import org.gradle.api.{Action, DefaultTask}

/**
  * Created by rick on 2017/5/8.
  */
class CompileJSTask extends DefaultTask {

    @Input
    val scalaOptions: ScalaOptions = ScalaOptions()
    @TaskAction
    def apply(): Unit = {

    }
}
