package com.github.nnnnusui.moviemaker

import java.nio.file.{Files, Paths}

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.Platform
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.control.{Button, Slider}
import scalafx.scene.image.Image
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.util.Duration

class Player(fps: Int){
  import scala.jdk.CollectionConverters._
  private val path = Paths.get("../~resource/test/")
  val files = Files.list(path).iterator().asScala
    .map(_.toUri.toString)
    .map(it=> new Image(it))
    .toList
  val maxIndex = files.size -1

  val canvas = new Canvas()
  private val keyFrame = KeyFrame(Duration(1000/fps), onFinished = _=> {
    Platform.runLater(incDraw(canvas.graphicsContext2D, canvas.box))
  })
  private val timeline = Timeline(Seq.fill(1) {keyFrame})
  timeline.cycleCount = Timeline.Indefinite

  private val slider = new Slider(0, maxIndex, 0) {
    value.addListener((_, _, _)=> draw(canvas.graphicsContext2D, canvas.box))
    //    onMouseEntered = _=> pause()
    //    onMouseExited  = _=> play()
    //    onMouseClicked  = _=> pause()
    //    onDragDetected  = _=> timeline.pause()
    //    onDragOver      = _=> Tester.draw(canvas.graphicsContext2D, canvas.box)
    //    onMouseReleased = _=> Tester.draw(canvas.graphicsContext2D, canvas.box)
  }
  private val playButton  = new Button("⏵") { onAction = _=> play() }
  private val pauseButton = new Button("⏸") { onAction = _=> pause() }
  val pane: BorderPane = new BorderPane {
    center = canvas
    bottom = new BorderPane{
      center = slider
      right = new HBox {
        children.addAll(playButton, pauseButton)
      }
    }
  }


  def pause(): Unit ={
    timeline.pause()
  }
  def play(): Unit ={
    timeline.play()
  }

  private def incDraw(graphicsContext: GraphicsContext, targetBox: Box): Unit ={
    draw(graphicsContext, targetBox)

    if (slider.value.value < maxIndex)
      slider.value.value += 1
    else
      slider.value.value = 0
  }
  private def draw(graphicsContext: GraphicsContext, targetBox: Box): Unit ={
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