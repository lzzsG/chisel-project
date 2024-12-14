package top

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
