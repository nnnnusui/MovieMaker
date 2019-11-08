package com.github.nnnnusui

import scalafx.scene.canvas.Canvas
import scalafx.scene.image.Image

package object moviemaker {
  implicit class RichImage(val src: Image){
    val box = Box(src.width.value, src.height.value)
  }
  implicit class RichCanvas(val src: Canvas){
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
