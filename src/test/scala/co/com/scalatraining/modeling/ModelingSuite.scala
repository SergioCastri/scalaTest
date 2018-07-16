package co.com.scalatraining.modeling

import java.awt.color.ColorSpace
import java.util.concurrent.Executors

import org.scalatest.FunSuite

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}
import scala.concurrent.{Await, ExecutionContext, Future}
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

  //Sustantivos

  trait Color
  case class Rojo() extends Color
  case class Azul() extends Color

  trait Marca
  case class Mazda() extends Marca
  case class Chevrolet() extends Marca

  trait Vehiculo{
    val color: Color
    val marca: Marca
  }

  case class Carro(color:Color, marca: Marca) extends Vehiculo
  case class Moto(color:Color, marca: Marca) extends Vehiculo

  val crojo = Carro(Rojo(), Mazda())
  val cazul = Carro(Azul(), Chevrolet())

  sealed trait servicioTallerAlgebra{
    def pintar(c:Carro, color:Color): Carro
  }

  sealed trait servicioTallerInterprete extends servicioTallerAlgebra{
    def pintar(carro: Carro, color: Color): Carro = {
      Carro(color,carro.marca)
    }
  }

  object servicioTallerInterprete extends servicioTallerInterprete

  //Comportamientos
  //Soporte a módulos -> Conjunto de comportamientos (cohesivos)

  // Algebra del dominio (funciones sin implementar)
  sealed trait servicioDeDominio{
    def operarA(i:Int): Int
    def operarB(i:Int): Int
    def operarC(i:Int): Int
  }


  // Interprete de un algebra
  sealed trait interpreteDeAlgebra extends servicioDeDominio{
    def operarA(i:Int): Int = {
      i + 1
    }
    def operarB(i:Int): Int = {
      i + 2
    }
    def operarC(i:Int): Int = {
      i + 3
    }
  }

  // trait object
  object interpreteDeAlgebra extends interpreteDeAlgebra

  test("Usar un servicio inadecuadamente"){

    class miCliente extends interpreteDeAlgebra{
      def operarPropioDeCliente(i:Int): Int = {
        operarA(i) + 1
      }
    }

    val c = new miCliente;
    val res = c.operarPropioDeCliente(1)

    assert(res == 3)

  }

  test("Usar un servicio adecuadamente"){
    import interpreteDeAlgebra._
    val res = operarA(1) + 1
    assert(res == 3)
  }


  test("Seamos presumidos"){

    val t = new servicioDeDominio {
      override def operarA(i: Int): Int = i + 1

      override def operarB(i: Int): Int = i + 2

      override def operarC(i: Int): Int = i + 3
    }

    val res = t.operarA(1) + 1
    assert(res == 3)
  }

  test("Pintar mi carro"){
    import servicioTallerInterprete._
    val res = pintar(Carro(Azul(),Mazda()), Rojo())
    assert(res.color == Rojo())
  }


}
