package co.com.scalatraining.collections

import org.scalatest.FunSuite

import scala.collection.{BitSet, SortedSet}
import scala.collection.immutable.ListSet

class SetSuite extends FunSuite {

  test("Construccion de un Set"){
    val set = Set(1,2,3)
    assert(set.size==3)
  }

  test("Adicion de un elemento a un Set"){
    val set = Set.empty[Int]
    val set2 = set + 1
    assert(set == Set.empty[Int])
    assert(set2 == Set(1))
  }

  test("Un conjunto no debe tener elementos repetidos"){
    val set = Set.empty[Int]
    val set2 = set + 1
    val set3 = set2 + 1
    assert(set == Set.empty[Int])
    assert(set2 == Set(1))
    assert(set3 == Set(1))
  }

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

  // -------------------------------------------------------

  test("SortedSet"){
    val s = SortedSet(1,4,3,2)

    assert(s.head==1)
    assert(s.tail.head==2)
    assert(s.tail.tail.head==3)
    assert(s.tail.tail.tail.head==4)
  }


  // Elements are stored internally in reversed insertion order
  test("ListSet"){
    val s = ListSet.empty[Int]
    val r = s + 1 + 4 + 3 + 2

    println(r)
    println(r.head)
    assert(r.head==1)
    assert(r.tail.head==4)
    assert(r.tail.tail.head==3)
    assert(r.tail.tail.tail.head==2)
  }

  test("BitSet"){
    val s = BitSet.empty
    val r = s + 1 + 2 + 3 + 0
    assert(r.head == 0)
    assert(r.tail.head == 1)
    assert(r.tail.tail.head == 2)
    assert(r.tail.tail.tail.head == 3)
  }

}