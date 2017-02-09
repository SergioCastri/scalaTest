package co.com.scalatraining.collections

import org.scalatest.FunSuite
import scala.language.postfixOps

class OperatorsSuite extends FunSuite{

  test("smoke test"){
    assert(true)
  }

  test("Operadores infijos 1"){
    val a:Int = 1
    val b:Int = 1

    // Todo operador de un solo parametro se puede usar con notacion infija
    val r1 = a + 1
    val r2 = a.+(1)

    assert(r1 == r2)
  }

  test("Operadores infijos 2"){

    class Op(i:Int) {
      def operar(o:Int) = i + o
    }

    val o1 = new Op(1)
    val o2 = o1.operar(2)
    val o3 = o1 operar 2

    assert(o2 == o3)
  }

  test("Operadores prefijos 1"){

    class Stereo {
      def unary_+ = "on"

      def unary_- = "off"
    }

    val stereo = new Stereo
    val res = +stereo
    assert(res=="on")
    assert(-stereo =="off")
  }

}
