package top

import chisel3._
import gcd.GCD
import _root_.circt.stage.ChiselStage

/** Top module
  */
class Top extends Module {
  val io = IO(new Bundle {
    val value1        = Input(UInt(16.W))
    val value2        = Input(UInt(16.W))
    val loadingValues = Input(Bool())
    val outputGCD     = Output(UInt(16.W))
    val outputValid   = Output(Bool())
  })

  // 实例化 GCD 模块
  val gcd = Module(new GCD)

  // 连接 io 接口
  gcd.io.value1        := io.value1
  gcd.io.value2        := io.value2
  gcd.io.loadingValues := io.loadingValues
  io.outputGCD         := gcd.io.outputGCD
  io.outputValid       := gcd.io.outputValid
}

//*****************************************************************************

/** Generate Verilog sources and save it in file Top.v
  */

object TopToV extends App {
  val firtoolOptions = Array(
    "--lowering-options=" + List(
      // make yosys happy
      // see https://github.com/llvm/circt/blob/main/docs/VerilogGeneration.md
      "disallowLocalVariables",
      "disallowPackedArrays",
      "locationInfoStyle=wrapInAtSquareBracket"
      //   "locationInfoStyle=plain"
    ).reduce(_ + "," + _),
    // "-strip-debug-info",
    "-disable-all-randomization"
  )
  circt.stage.ChiselStage.emitSystemVerilogFile(new Top(), args, firtoolOptions)
}
