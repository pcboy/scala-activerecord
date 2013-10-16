package com.github.aselab.activerecord.play

import com.github.aselab.activerecord.Plugin._

object Plugin extends sbt.Plugin {
  val activerecordSettings = generatorSettings

  new ControllerGenerator().register
  new RoutesGenerator().register
}
