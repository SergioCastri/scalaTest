package co.com.scalatraining.collections

import org.scalatest.FunSuite

class SetSuite extends FunSuite {

  test("map en un set") {
    val set = Set("1", "2", "3")
    val set2 = set.map(dato => dato + "prueba")
    assert(set != set2)
  }

  test("head en un set") {
    val set = Set(1, 2, 3, 4)
    assertResult(1) {
      set.head
    }
  }


  test("tail en un set") {
    val set = Set(1, 2, 3, 4)
    assertResult(Set(2, 3, 4)) {
      set.tail
    }
  }

  test("split en un set") {
    val set = Set(1, 2, 3, 4)
    val (set2, set3) = set.splitAt(2)
    assert(set2 == Set(1, 2) && set3 == Set(3, 4))
  }

  test("crear nuevo set con un item mas") {
    val set = Set(1, 2, 3, 4)
    assertResult(Set(1, 2, 3, 4, 5)) {
      set + 5
    }
  }

  test("apply en set") {
    val set = Set(1, 2, 3, 4)
    assertResult(true) {
      set.apply(4)
    }
  }

  test("drop en set") {
    val set = Set(1, 2, 3, 4)
    assertResult(Set(3, 4)) {
      set.drop(2)
    }
  }

  test("dropRight set") {
    val set = Set(1, 2, 3, 4)
    assertResult(Set(1, 2)) {
      set.dropRight(2)
    }
  }


  test("filter en set") {
    val set = Set(1, 2, 3, 4)
    assertResult(Set(2, 4)) {
      set.filter(x =>
        x % 2 == 0
      )
    }
  }

  test("foreach en set") {
    val set = Set(1, 2, 3, 4)
    assertResult(10) {
      var sum = 0
      set.foreach((x) =>
        sum += x
      )
      sum
    }
  }

  test("mkString en set") {
    val set = Set(1, 2, 3, 4)
    assertResult("1&2&3&4") {
      set.mkString("&")
    }
  }

  test("sum en set") {
    val set = Set(1, 2, 3, 4)
    assertResult(10) {
      set.sum
    }
  }

}