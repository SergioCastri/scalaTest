package co.com.scalatraining.scalatest.specs

import org.scalatest.FreeSpec

class SetFreeSpec extends FreeSpec {

  "A Set" - {
    val s = Set.empty
    "when empty" - {
      "should have size 0" in {
        assert(s.size == 0)
      }

      "should produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          s.head
        }
      }
    }
  }
}
