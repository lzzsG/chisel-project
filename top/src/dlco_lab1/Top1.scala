package top

import chisel3._
import chisel3.util._
import selector._
import _root_.circt.stage.ChiselStage

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {
    val X0 = Input(UInt(2.W))
    val X1 = Input(UInt(2.W))
    val X2 = Input(UInt(2.W))
    val X3 = Input(UInt(2.W))
    val Y  = Input(UInt(2.W))
    val F  = Output(UInt(2.W))
  })

  val sel = Module(new Nto1Selector(2, 2))
  sel.io.sel := io.Y
  sel.io.in  := VecInit(io.X0, io.X1, io.X2, io.X3)
  io.F       := sel.io.out
}
