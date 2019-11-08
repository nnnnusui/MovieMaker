package com.github.nnnnusui.moviemaker

import java.nio.file.{Files, Paths}

import com.sun.prism.impl.Disposer.Target
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
  def draw(graphicsContext: GraphicsContext, targetWidth: Double, targetHeight: Double): Unit ={
    if (counter >= files.size)
      counter = 0

    graphicsContext.clearRect(0, 0, targetWidth, targetHeight)
    val image = files(counter)
    val imageBox = image.box
    val targetBox = Box(targetWidth, targetHeight)

    val resizedBox = imageBox.getResizedTo(targetBox)
    val startPos = resizedBox.getCenteringPosTo(targetBox)
    graphicsContext.drawImage(image, startPos.x, startPos.y, resizedBox.width, resizedBox.height)
    counter += 1
  }

  implicit class RichImage(val src: Image){
    val box = Box(src.width.value, src.height.value)
  }
  case class Pos(x: Double, y: Double)
  case class Box(width: Double, height: Double){
    def getResizedTo(target: Box): Box ={
      val ratio = Seq(target.width  / width
        ,target.height / height).min
      Box(width * ratio, height * ratio)
    }
    def getCenteringPosTo(target: Box): Pos
    = Pos((target.width  - width ) /2
      ,(target.height - height) /2)
  }
}