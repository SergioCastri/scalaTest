package co.com.scalatraining.collections

import org.scalatest.FunSuite

import scala.collection.SortedMap

class MapSuite extends FunSuite {

  test ("Creacion vacia") {
      val mapa1 = Map()
      val mapa2 = Map.empty
      assert(mapa1.isEmpty)
      assert(mapa2.isEmpty)
      assert(mapa1 == mapa2)
  }

  test("foreach en un Map") {
    val map = Map("1" -> 1, "2" -> 2, "3" -> 3)
    assertResult(6) {
      var sum = 0
      map.foreach((x) =>
        sum += x._2
      )
      sum
    }
  }

  test("Un Map se debe poder operar en un for-comp"){
    val mapa = Map(1->"uno", 2->"dos")

    val res = for{
      i <- mapa
      if i._1 == 1
    } yield(i)


    assert(res.keys.size === 1)
    assert(res.keys.head === 1)

    val x: Option[String] = res.get(1)
    assert(x == Some("uno"))
  }

  test("crear nuevo Map con un item mas") {
    val map = Map("1" -> 1, "2" -> 2, "3" -> 3)
    assertResult(Map("1" -> 1, "2" -> 2, "3" -> 3, "4" -> 4)) {
      map + ("4" -> 4)
    }
  }

  test("head en un Map") {
    val map = Map("1" -> 1, "2" -> 2, "3" -> 3)
    assertResult("1" -> 1) {
      map.head
    }
  }


  test("head en un Map vacio") {
    val map = Map.empty

    assertThrows[NoSuchElementException]{
      val x = map.head
    }
  }

  test("head en un Map (verificacion con tupla)") {
    val map = Map("1" -> 1, "2" -> 2, "3" -> 3)

    val x: (String, Int) = map.head

    assert(x._1 == "1")
    assert(x._2 == 1)

  }

  test("tail en un Map") {
    val map = Map("1" -> 1, "2" -> 2, "3" -> 3)
    assertResult(Map("2" -> 2, "3" -> 3)) {
      map.tail
    }
  }

  test("split en un Map") {
    val map = Map("1" -> 1, "2" -> 2, "3" -> 3)
    val (map2, map3) = map.splitAt(2)
    assert(map2 == Map("1" -> 1, "2" -> 2) && map3 == Map("3" -> 3))
  }


  test("drop en un Map") {


   val map = Map("1" -> 1, "2" -> 2, "3" -> 3)
    assertResult(Map("2" -> 2, "3" -> 3)) {
      map.drop(1)
    }
  }

  test("dropRight en un Map") {
    val map = Map("1" -> 1, "2" -> 2, "3" -> 3)
    assertResult(Map("1" -> 1, "2" -> 2)) {
      map.dropRight(1)
    }
  }


  // -----------------------------------------


  test("filter en un Map") {
    val map = Map("1" -> 1, "2" -> 2, "3" -> 3, "4" -> 4)
    assertResult(Map("2" -> 2, "4" -> 4)) {
      map.filter(dato =>
        dato._2 % 2 == 0
      )
    }
  }

  test("Transformacion de un Map con map"){
    val m = Map("1"->1,"2"->2,"3"->3)
    val res = m.map(kv => (kv._1+" the key", kv._2))
    assert(res("1 the key")==1)
  }

  test("mapValues en un Map") {
    val map: Map[String, Int] = Map("1" -> 1, "2" -> 2, "3" -> 3)
    assertResult(Map("1" -> 1, "2" -> 4, "3" -> 9)) {
      map.mapValues(valor => valor * valor)
    }
  }

}
