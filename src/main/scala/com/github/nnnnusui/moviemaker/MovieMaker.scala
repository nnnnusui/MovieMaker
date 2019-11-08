package com.github.nnnnusui.moviemaker

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Slider}
import scalafx.scene.layout.BorderPane
import scalafx.util.Duration

object MovieMaker extends JFXApp{
  val fps = 60
  val _width  = 1280
  val _height = 720

  val canvas = new Canvas()
  val slider = new Slider(0, Tester.files.size, 0)
  Tester.counter <==> slider.value

  val button: Button = new Button {
    onAction = _ => Platform.runLater(timeline.play())
  }
  val pane: BorderPane = new BorderPane {
//    left = button
    center = canvas
    bottom = slider
  }
  val _scene: Scene = new Scene {
    root = pane
    canvas.width  <== this.width
    canvas.height <== this.height - 50
  }
  stage = new JFXApp.PrimaryStage {
    title.value = "MovieMaker"
    width  = _width
    height = _height
    scene = _scene
  }
  val keyFrame = KeyFrame(Duration(1000/fps), onFinished = _=> {
    Tester.draw(canvas.graphicsContext2D, canvas.box)
  })
  val timeline = Timeline(Seq.fill(1) {keyFrame})
  timeline.cycleCount = Timeline.Indefinite
  timeline.play()
}

/* TODO: "../~resource/test/_xxxx.bmp"
 * 連番BMPをImageViewで60FPS表示してみる……
 */