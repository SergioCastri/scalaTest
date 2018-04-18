package co.com.scalatraining.modeling

import java.util.concurrent.Executors

import org.scalatest.FunSuite
import scala.language.postfixOps
import scala.util.{Try, Failure, Success}
import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ModelingSuite extends FunSuite {


  trait Instruccion

  object Instruccion {
    def newInstruccion(c:Char):Instruccion ={
      c match {
        case 'A' => A()
        case 'D' => D()
        case 'I' => I()
        case _ => throw new Exception(s"Caracter invalido para creacion de instruccion: $c")
      }
    }
  }

  case class A() extends Instruccion
  case class I() extends Instruccion
  case class D() extends Instruccion

  test("testing smart constructor"){

    import Instruccion._

    val instruccionA = Try(newInstruccion('A'))
    val instruccionD = Try(newInstruccion('D'))
    val instruccionI = Try(newInstruccion('I'))
    val instruccionInvalida = Try(newInstruccion('.'))

    assert(instruccionA == Success(A()))
    assert(instruccionD == Success(D()))
    assert(instruccionI == Success(I()))
    assert(instruccionInvalida.isFailure)

  }


}
