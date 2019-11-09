package com.github.nnnnusui.moviemaker

import scalafx.application.JFXApp
import scalafx.scene.Scene

object MovieMaker extends JFXApp{
  val fps = 60
  val _width  = 1280
  val _height = 720
  val player = new Player(fps)

  val _scene: Scene = new Scene {
    root = player.pane
    player.canvas.width  <== this.width
    player.canvas.height <== this.height - 50
  }
  stage = new JFXApp.PrimaryStage {
    title.value = "MovieMaker"
    width  = _width
    height = _height
    scene = _scene
  }
}