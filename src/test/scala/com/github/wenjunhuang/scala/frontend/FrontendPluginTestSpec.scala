package com.github.wenjunhuang.scala.frontend

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.testkit.runner.GradleRunner
import org.junit.rules.TemporaryFolder
import org.scalatest.{BeforeAndAfterAll, FunSuite, Matchers}

import java.io.{BufferedWriter, File, FileWriter}
import org.gradle.testkit.runner.TaskOutcome._

import scala.collection.JavaConverters._
import org.gradle.testkit.runner.internal.PluginUnderTestMetadataReading

/**
  * Created by rick on 2017/6/25.
  */
class FrontendPluginTestSpec extends FunSuite with Matchers with BeforeAndAfterAll {
    var testProjectDir: TemporaryFolder = null
    var buildFile: File = null

    test("project builder") {
        val project = ProjectBuilder.builder().build()
        project.getPluginManager.apply(classOf[FrontendPlugin])
        project.getTasks.getByName("scalaFrontend") should not be (null)
    }

    test("setup should be ok") {
        val plugin = new FrontendPlugin
        val buildFileContent =
            """task helloWorld {
              | doLast {
              |    println 'Hello world!'
              | }
              |}
            """.stripMargin

        writeFile(buildFile, buildFileContent)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot)
            .withArguments("helloWorld")
            .build()

        result.getOutput should include("Hello world!")
        result.task(":helloWorld").getOutcome shouldBe (SUCCESS)
    }

    override def beforeAll(): Unit = {
        testProjectDir = new TemporaryFolder()
        testProjectDir.create()
        buildFile = testProjectDir.newFile("build.gradle")
    }

    override def afterAll(): Unit = {
        testProjectDir.delete()
    }

    def writeFile(destination: File, content: String): Unit = {
        var output: BufferedWriter = null
        try {
            output = new BufferedWriter(new FileWriter(destination))
            output.write(content)
        } finally {
            if (output != null)
                output.close()
        }
    }

}
