package com.github.wenjunhuang.scalajs.tasks

import java.net.URI

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by rick on 2017/5/8.
  */
class ScalajsldTest extends FlatSpec with Matchers{
  "createFrontendConfig" should "set checkIR property" in {
    val scalajsld = Scalajsld
    val expected = true
    scalajsld.options = ScalaOptions().withCheckIR(expected)

    val result = scalajsld.createFrontendConfig

    result.checkIR should equal(expected)
  }

  "createBackendConfig" should "set correct properties" in {
    val relativizeSourceMap = Some(new URI("http://foo.com"))
    val prettyPrint = true
    Scalajsld.options = ScalaOptions().withRelativizeSourceMap(relativizeSourceMap).withPrettyPrint(prettyPrint)

    val result = Scalajsld.createBackendConfig

    result.relativizeSourceMapBase should equal(relativizeSourceMap)
    result.prettyPrint should equal(prettyPrint)
  }

}
