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
//  def draw(graphicsContext: GraphicsContext, targetWidth: Double, targetHeight: Double)
//    = draw(graphicsContext, Box(targetWidth, targetHeight))
  def draw(graphicsContext: GraphicsContext, targetBox: Box): Unit ={
    if (counter >= files.size)
      counter = 0

    graphicsContext.clearRect(Pos(0, 0), targetBox)
    val image = files(counter)
    val imageBox = image.box

    val resizedBox = imageBox.getResizedTo(targetBox)
    val startPos = resizedBox.getCenteringPosTo(targetBox)
    graphicsContext.drawImage(image, startPos, resizedBox)
    counter += 1
  }
  implicit class RichGraphicsContext(val src: GraphicsContext){
    def clearRect(pos: Pos, box: Box): Unit
      = src.clearRect(pos.x, pos.y, box.width, box.height)
    def drawImage(image: Image, pos: Pos, box: Box): Unit
      = src.drawImage(image, pos.x, pos.y, box.width, box.height)
  }
}