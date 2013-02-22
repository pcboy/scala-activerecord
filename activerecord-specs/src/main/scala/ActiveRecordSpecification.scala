package com.github.aselab.activerecord

import org.specs2.mutable._
import org.specs2.execute._
import org.specs2.specification._

trait AutoRollback extends BeforeAfterExample {
  def schema: ActiveRecordTables

  def before = schema.startTransaction
  def after = schema.rollback
}

trait BeforeAfterAllExamples extends Specification {
  def beforeAll: Unit
  def afterAll: Unit
  override def map(fs: => Fragments) = {
    Step {
      beforeAll
    } ^ fs ^ Step {
      afterAll
    }
  }
}

trait ActiveRecordSpecification extends BeforeAfterAllExamples {
  sequential

  def beforeAll = {
    System.setProperty("run.mode", "test")
    schema.initialize(config)
    schema.reset
  }

  def afterAll = dsl.transaction {
    schema.cleanup
    System.clearProperty("run.mode")
  }

  def config: Map[String, String] = Map()

  lazy val schema: ActiveRecordTables = new DefaultConfig().schema
}
