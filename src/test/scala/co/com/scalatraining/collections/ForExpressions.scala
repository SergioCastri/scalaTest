package co.com.scalatraining.collections

import org.scalatest.FunSuite

class ForExpressions extends FunSuite{

  test("Smoke test"){
    assert(true)
  }

  test("for-comp imperativo 1"){
    val l = 1 to 10

    var cont = 0
    for{
      a <- l
    } cont+=1

    assert(cont==10)
  }

  test("for-comp imperativo 2"){
    case class CC()

    assertDoesNotCompile {
      "for{ " +
      " a <- CC() " +
      "} println(a)"
    }

  }
}
