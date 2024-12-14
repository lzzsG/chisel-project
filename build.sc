// Import Mill dependencies
import mill._
import mill.define.Sources
import mill.modules.Util
import mill.scalalib._
import mill.scalalib.TestModule.ScalaTest
import mill.bsp._

object playground extends ScalaModule { m =>
  // 指定 Mill 项目的源码路径
  override def millSourcePath = os.pwd

  // 设置 Scala 版本
  override def scalaVersion = "2.13.15"

  // 编译选项
  override def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit"
  )

  // 添加 Chisel 相关依赖项
  override def ivyDeps = Agg(
    ivy"org.chipsalliance::chisel:6.6.0"
  )

  // 添加 Chisel 插件
  override def scalacPluginIvyDeps = Agg(
    ivy"org.chipsalliance:::chisel-plugin:6.6.0"
  )

  // 配置测试模块
  object test extends ScalaTests with TestModule.ScalaTest {
    override def ivyDeps = m.ivyDeps() ++ Agg(
      ivy"org.scalatest::scalatest::3.2.16"
    )
  }

  // 配置 BSP 支持
  def repositoriesTask = T.task {
    Seq(
      coursier.MavenRepository("https://repo.scala-sbt.org/scalasbt/maven-releases"),
      coursier.MavenRepository("https://oss.sonatype.org/content/repositories/releases"),
      coursier.MavenRepository("https://oss.sonatype.org/content/repositories/snapshots")
    ) ++ super.repositoriesTask()
  }
}
