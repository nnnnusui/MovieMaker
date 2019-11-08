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
  def draw(graphicsContext: GraphicsContext): Unit ={
    if (counter >= files.size) {
      counter = 0
      return
    }
    val image = files(counter)
    graphicsContext.drawImage(image, 0, 0)
    counter += 1
  }
}
