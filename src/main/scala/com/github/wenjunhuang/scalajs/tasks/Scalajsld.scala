package com.github.wenjunhuang.scalajs.tasks

import java.io.File
import java.net.URI

import org.scalajs.core.tools.io.{IRFileCache, WritableFileVirtualJSFile}
import org.scalajs.core.tools.linker.backend.{LinkerBackend, ModuleKind, OutputMode}
import org.scalajs.core.tools.linker.frontend.LinkerFrontend
import org.scalajs.core.tools.linker.{Linker, ModuleInitializer}
import org.scalajs.core.tools.logging.{Level, ScalaConsoleLogger}
import org.scalajs.core.tools.sem.Semantics

/**
  * Created by rick on 2017/5/8.
  */
object Scalajsld {
  var options: Options = Options()

  def exec(): Unit = {
    val linker = generateLinker
    val cache = (new IRFileCache).newCache
    val irContainers = IRFileCache.IRContainer.fromClasspath(options.stdLib.toList ++ options.cp)
    linker.link(cache.cached(irContainers), options.moduleInitializers, WritableFileVirtualJSFile(options.output), new ScalaConsoleLogger(options.logLevel))
  }

  private[tasks] def generateLinker = {
    val frontendConfig: LinkerFrontend.Config = createFrontendConfig
    val backendConfig: LinkerBackend.Config = createBackendConfig
    val config: Linker.Config = createLinkerConfig(frontendConfig, backendConfig)
    val semantics: Semantics = createSementics

    Linker(semantics, options.outputMode, options.moduleKind, config)
  }

  private[tasks] def createLinkerConfig(frontendConfig: LinkerFrontend.Config, backendConfig: LinkerBackend.Config): Linker.Config = {
    val config = Linker.Config()
      .withBackendConfig(backendConfig)
      .withFrontendConfig(frontendConfig)
      .withClosureCompilerIfAvailable(options.fullOpt)
      .withOptimizer(!options.noOpt)
      .withParallel(options.parallel)
      .withSourceMap(options.sourceMap)
    config
  }

  private[tasks] def createBackendConfig: LinkerBackend.Config = {
    val backendConfig = LinkerBackend.Config()
      .withRelativizeSourceMapBase(options.relativizeSourceMap)
      .withPrettyPrint(options.prettyPrint)
    backendConfig
  }

  private[tasks] def createFrontendConfig: LinkerFrontend.Config = {
    val frontendConfig = LinkerFrontend.Config().withCheckIR(options.checkIR)
    frontendConfig
  }

  private[tasks] def createSementics: Semantics = {
    val semantics: Semantics = if (options.fullOpt) options.semantics.optimized else options.semantics
    semantics
  }
}

case class Options(cp: Seq[File] = Seq.empty,
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

  def withPrettyPrint(newPrettyPrint: Boolean): Options = {
    this.copy(prettyPrint = newPrettyPrint)
  }

  def withClasspath(newCp: Seq[File]): Options = {
    this.copy(cp = newCp)
  }

  def withModuleInitializers(newModuleInitializers: Seq[ModuleInitializer]): Options = {
    this.copy(moduleInitializers = newModuleInitializers)
  }

  def withOutput(newOutput: File): Options = {
    this.copy(output = newOutput)
  }

  def withSemantics(newSemantics:Semantics): Options = {
    this.copy(semantics = newSemantics)
  }

  def withOutputMode(newOutputMode: OutputMode): Options = {
    this.copy(outputMode = newOutputMode)
  }

  def withModuleKind(newModuleKind: ModuleKind): Options = {
    this.copy(moduleKind = newModuleKind)
  }

  def withSourceMap(newSourceMap: Boolean): Options = {
    this.copy(sourceMap = newSourceMap)
  }

  def withRelativizeSourceMap(newRelativizeSourceMap: Option[URI]): Options = {
    this.copy(relativizeSourceMap = newRelativizeSourceMap)
  }

  def withStdLib(newStdLib: Option[File]): Options = {
    this.copy(stdLib = newStdLib)
  }

  def withLogLevel(newLogLevel: Level): Options = {
    this.copy(logLevel = newLogLevel)
  }

  def withCheckIR(newCheckIR: Boolean): Options = {
    this.copy(checkIR = newCheckIR)
  }

  def withBatchMode(newBatchMode: Boolean): Options = {
    this.copy(batchMode = newBatchMode)
  }

  def withParallel(newParallel: Boolean): Options = {
    this.copy(parallel = newParallel)
  }
}
