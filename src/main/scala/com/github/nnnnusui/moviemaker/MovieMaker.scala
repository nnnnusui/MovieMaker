package com.github.nnnnusui.moviemaker

import java.nio.file.{Files, Paths}

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.BorderPane
import scalafx.util.Duration

object MovieMaker extends JFXApp{
  val fps = 60
  val imageView = new ImageView {
    this.setPreserveRatio(true)
  }
  val keyFrame = KeyFrame(Duration(1000/fps), onFinished = _=> {
    Tester.draw(imageView)
  })
  val timeline = Timeline(Seq.fill(1) {keyFrame})
  timeline.cycleCount = Timeline.Indefinite

  val button = new Button {
    onAction = _ => Platform.runLater(timeline.play())
  }
  val pane = new BorderPane {
    center = imageView
    bottom = button
  }
  val _scene = new Scene {
    root = pane
    imageView.fitWidth  <== this.width
    imageView.fitHeight <== this.height - 50
  }
  stage = new JFXApp.PrimaryStage {
    title.value = "MovieMaker"
    width = 1200
    height = 1080
    scene = _scene
  }
}

/* TODO: "../~resource/test/_xxxx.bmp"
 * 連番BMPをImageViewで60FPS表示してみる……
 */