package top

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class SimpleTest extends AnyFlatSpec with ChiselScalatestTester {
  "DUT" should "add" in {
    test(new Top()) { dut =>
      // 设置输入信号
      dut.io.a.poke(1.U)
      dut.io.b.poke(1.U)

      // 运行一个时钟周期
      dut.clock.step()

      // 检查输出信号
      dut.io.c.expect(1.U)
    }
  }
}
