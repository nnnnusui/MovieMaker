package com.github.nnnnusui.moviemaker

import java.nio.file.{Files, Paths}

import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.image.{Image, ImageView}

object Tester{
  import scala.jdk.CollectionConverters._
  val path = Paths.get("../~resource/test/")
  val files = Files.list(path).iterator().asScala
    .map(_.toUri.toString)
    .map(it=> new Image(it))
    .toList

  var counter = 0
  def draw(graphicsContext: GraphicsContext, _width: Double, _height: Double): Unit ={
    if (counter >= files.size) {
      counter = 0
      return
    }
    val image = files(counter)
    val ratio = {
      val widthRatio  = _width  / image.width.value
      val heightRatio = _height / image.height.value
      Seq(widthRatio, heightRatio).min
    }
    val width  = image.width.value  * ratio
    val height = image.height.value * ratio
    graphicsContext.drawImage(image, 0, 0, width, height)
    counter += 1
  }
}
