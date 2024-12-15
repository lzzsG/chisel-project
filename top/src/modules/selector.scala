package selector

import chisel3._
import chisel3.util._

/** N-to-1 Selector (2^n:1 MUX)
  * @param n
  *   Number of selector bits (log2 of inputs)
  * @param w
  *   Width of each input signal
  */
class Nto1Selector(val n: Int, val w: Int) extends Module {
  val io = IO(new Bundle {
    val sel = Input(UInt(n.W))              // n 位选择信号
    val in  = Input(Vec(1 << n, UInt(w.W))) // 2^n 个宽度为 w 的输入信号
    val out = Output(UInt(w.W))             // 选择的输出信号
  })

  // 使用选择信号直接选择输入
  io.out := io.in(io.sel)
}
