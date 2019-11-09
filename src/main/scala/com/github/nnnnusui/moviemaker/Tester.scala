package com.github.nnnnusui.moviemaker

import java.nio.file.{Files, Paths}

import com.github.nnnnusui.moviemaker.MovieMaker.canvas
import scalafx.beans.property.IntegerProperty
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.control.Slider
import scalafx.scene.image.Image

object Tester{
  import scala.jdk.CollectionConverters._
  val path = Paths.get("../~resource/test/")
  val files = Files.list(path).iterator().asScala
    .map(_.toUri.toString)
    .map(it=> new Image(it))
    .toList
  val maxIndex = files.size -1

//  val counter = IntegerProperty(0)
  val slider = new Slider(0, maxIndex, 0) {
    value.addListener((_, _, _)=> Tester.draw(canvas.graphicsContext2D, canvas.box))
    //    onMouseEntered = _=> pause()
    //    onMouseExited  = _=> play()
    //    onMouseClicked  = _=> pause()
    //    onDragDetected  = _=> timeline.pause()
    //    onDragOver      = _=> Tester.draw(canvas.graphicsContext2D, canvas.box)
    //    onMouseReleased = _=> Tester.draw(canvas.graphicsContext2D, canvas.box)
  }
  def incDraw(graphicsContext: GraphicsContext, targetBox: Box): Unit ={
    draw(graphicsContext, targetBox)

    if (slider.value.value < maxIndex)
      slider.value.value += 1
    else
      slider.value.value = 0
  }
  def draw(graphicsContext: GraphicsContext, targetBox: Box): Unit ={
    graphicsContext.clearRect(Pos(0, 0), targetBox)
    val image = files(slider.value.toInt)
    val imageBox = image.box

    val resizedBox = imageBox.getResizedTo(targetBox)
    val startPos = resizedBox.getCenteringPosTo(targetBox)
    graphicsContext.drawImage(image, startPos, resizedBox)
  }
  implicit class RichGraphicsContext(val src: GraphicsContext){
    def clearRect(pos: Pos, box: Box): Unit
      = src.clearRect(pos.x, pos.y, box.width, box.height)
    def drawImage(image: Image, pos: Pos, box: Box): Unit
      = src.drawImage(image, pos.x, pos.y, box.width, box.height)
  }
}