package com.github.wenjunhuang.scalajs.tasks

import org.scalajs.core.tools.linker.ModuleInitializer
import org.scalajs.core.tools.linker.backend.{ModuleKind, OutputMode}
import org.scalajs.core.tools.logging.Level
import org.scalajs.core.tools.sem.Semantics

import java.io.File
import java.net.URI

/**
  * Created by rick on 2017/6/25.
  */
case class ScalaOptions(cp: Seq[File] = Seq.empty,
                        moduleInitializers: Seq[ModuleInitializer] = Seq.empty,
                        output: File = null,
                        semantics: Semantics = Semantics.Defaults,
                        outputMode: OutputMode = OutputMode.ECMAScript51Isolated,
                        moduleKind: ModuleKind = ModuleKind.NoModule,
                        noOpt: Boolean = false,
                        fullOpt: Boolean = false,
                        prettyPrint: Boolean = false,
                        sourceMap: Boolean = false,
                        relativizeSourceMap: Option[URI] = None,
                        parallel: Boolean = true,
                        checkIR: Boolean = false,
                        batchMode: Boolean = false,
                        stdLib: Option[File] = None,
                        logLevel: Level = Level.Info) {

  def withPrettyPrint(newPrettyPrint: Boolean): ScalaOptions = {
    this.copy(prettyPrint = newPrettyPrint)
  }

  def withClasspath(newCp: Seq[File]): ScalaOptions = {
    this.copy(cp = newCp)
  }

  def withModuleInitializers(newModuleInitializers: Seq[ModuleInitializer]): ScalaOptions = {
    this.copy(moduleInitializers = newModuleInitializers)
  }

  def withOutput(newOutput: File): ScalaOptions = {
    this.copy(output = newOutput)
  }

  def withSemantics(newSemantics:Semantics): ScalaOptions = {
    this.copy(semantics = newSemantics)
  }

  def withOutputMode(newOutputMode: OutputMode): ScalaOptions = {
    this.copy(outputMode = newOutputMode)
  }

  def withModuleKind(newModuleKind: ModuleKind): ScalaOptions = {
    this.copy(moduleKind = newModuleKind)
  }

  def withSourceMap(newSourceMap: Boolean): ScalaOptions = {
    this.copy(sourceMap = newSourceMap)
  }

  def withRelativizeSourceMap(newRelativizeSourceMap: Option[URI]): ScalaOptions = {
    this.copy(relativizeSourceMap = newRelativizeSourceMap)
  }

  def withStdLib(newStdLib: Option[File]): ScalaOptions = {
    this.copy(stdLib = newStdLib)
  }

  def withLogLevel(newLogLevel: Level): ScalaOptions = {
    this.copy(logLevel = newLogLevel)
  }

  def withCheckIR(newCheckIR: Boolean): ScalaOptions = {
    this.copy(checkIR = newCheckIR)
  }

  def withBatchMode(newBatchMode: Boolean): ScalaOptions = {
    this.copy(batchMode = newBatchMode)
  }

  def withParallel(newParallel: Boolean): ScalaOptions = {
    this.copy(parallel = newParallel)
  }
}
