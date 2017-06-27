package com.github.wenjunhuang.scalajs.tasks

import org.apache.commons.io.FileUtils
import org.junit.rules.TemporaryFolder
import org.scalactic.source.Position

import java.net.{URI, URLClassLoader}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FlatSpec, Matchers}

import scala.collection.JavaConverters._

import java.io.File

/**
  * Created by rick on 2017/5/8.
  */
class ScalaJsLdTest extends FlatSpec with Matchers with BeforeAndAfterAll {
    var tempFile: TemporaryFolder = null
    var scalaJsJarFile: File = null

    override def beforeAll(): Unit = {
        tempFile = new TemporaryFolder()
        tempFile.create()
        scalaJsJarFile = tempFile.newFile("scalajs-library.jar")

        val cpUrls = getClass.getClassLoader.asInstanceOf[URLClassLoader].getURLs
        val scalaJsLibJarUrl = cpUrls.filter { url => url.toExternalForm.contains("scalajs-library") }.head
        FileUtils.copyURLToFile(scalaJsLibJarUrl, scalaJsJarFile)
    }

    override def afterAll(): Unit = {
        tempFile.delete()
    }

    "createFrontendConfig" should "set checkIR property" in {
        val scalajsld = ScalaJsLd
        val expected = true
        scalajsld.options = ScalaOptions().withCheckIR(expected)

        val result = scalajsld.createFrontendConfig

        result.checkIR should equal(expected)
    }

    "createBackendConfig" should "set correct properties" in {
        val relativizeSourceMap = Some(new URI("http://foo.com"))
        val prettyPrint = true
        ScalaJsLd.options = ScalaOptions().withRelativizeSourceMap(relativizeSourceMap).withPrettyPrint(prettyPrint)

        val result = ScalaJsLd.createBackendConfig

        result.relativizeSourceMapBase should equal(relativizeSourceMap)
        result.prettyPrint should equal(prettyPrint)
    }

    "compile 01.scala to a temp file" should "ok" in {
        val outputFile = tempFile.newFile("temp.js")
        ScalaJsLd.options = ScalaOptions()
            .withStdLib(Some(scalaJsJarFile))
            .withClasspath(Seq(new File("01.scala")))
            .withOutput(outputFile)

        ScalaJsLd.exec()

        outputFile.length() should not be (0)
    }
}
