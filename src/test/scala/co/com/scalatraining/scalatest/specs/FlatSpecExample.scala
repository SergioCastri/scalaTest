package co.com.scalatraining.scalatest.specs

import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "Hello" should "have tests" in {
    true should === (true)
  }

  "Hello" should "assert false" in {
    assert(true)
  }


}
