package com.github.nnnnusui.moviemaker

import java.nio.file.{Files, Path}

import scalafx.scene.control.{Button, Label, ScrollPane, Slider, SplitPane}
import scalafx.scene.image.Image
import scalafx.scene.input.TransferMode
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scala.jdk.CollectionConverters._

class Timeline {
  val length = 220
  val layerBox = new VBox {
    val button = new Button("➕"){
      onAction = _=>{
        val layer = new Layer(length)
        children.add(children.size()-1, layer.pane)
      }
    }
    val slider = new Slider(0, length-1, 0)
    children.add(slider)
    children.add(button)
  }
//  val layerBox = new VBox{
//    val slider = new Slider(0, length-1, 0)
//    children.add(slider)
//  }
//  val layerHeaders = new ScrollPane {
//    content = new VBox {
//      val button = new Button("➕"){
//        onAction = _=>{
//          val layer = new Layer(length)
//          children.add(children.size()-1, layer.header)
//          layerBox.children.add(layer.contentBox)
//        }
//      }
//      children.add(new Label("top"))
//      children.add(button)
//    }
//  }
  val pane = new BorderPane {
    center = layerBox
//    center = new SplitPane {
//      val layerContents = new ScrollPane {
//        content = layerBox
//      }
//      items.addAll(layerHeaders, layerContents)
//    }
  }
}
class Layer(length: Int) {
  val header = new Label("Layer"){
    onDragOver = event=>{
      if (event.getDragboard.hasFiles)
        event.acceptTransferModes(TransferMode.Move)
    }
    onDragDropped = event=>{
      val files = event.getDragboard.getFiles
      files.forEach(it=>{
        val entity = new Entity(it.toPath)
        contentBox.children.add(entity.pane)
      })
    }
  }
  val contentBox = new HBox{
    Seq.fill(length){new Empty().node}.foreach(it=> children.add(it))
  }
  val pane = new BorderPane{
    left   = header
    center = contentBox
  }
}
class Entity(val path: Path){
//  import scala.jdk.CollectionConverters._
//  val files = Files.list(path).iterator().asScala
//    .map(_.toUri.toString)
//    .map(it=> new Image(it))
//    .toList
  val pane = new BorderPane{
    center = new Rectangle {
      width = 5
      height = 25
      fill = Color.DarkRed
      stroke = Color.Black
    }
  }
}
class Empty{
  val node = new Rectangle {
    width = 5
    height = 25
    fill = Color.White
    stroke = Color.Silver
  }
}