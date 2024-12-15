package top

import chisel3._
import _root_.circt.stage.ChiselStage

/** Top module
  */
class TopX extends Module {
  val io = IO(new Bundle {
    val a = Input(Bool())
    val b = Input(Bool())
    val c = Output(Bool())
  })

  io.c := io.a && io.b

}
