package co.com.scalatraining.modeling

import java.util.concurrent.Executors

import org.scalatest.FunSuite
import scala.language.postfixOps
import scala.util.{Try, Failure, Success}
import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ModelingSuite extends FunSuite {

  //GADT: Generic Algebraic Data Types
  //DDD: Estratégico y Táctico
  case class Poliza(numero:String, coberturas:List[Cobertura])
  case class Persona(dni:String, nombre:String)
  case class Asegurado(dni:String, nombre:String)
  case class Cobertura(cdgarantia:String, cdsubgarantia:String)
  case class Tarifa(prima:Int)

  // Algebra del API
  trait operacionesEspecificas {
    def tarifar(poliza:Poliza):Future[Tarifa]
    def consultarPoliza(numero:String):Future[Poliza]
    def adicionarCobertura(cobertura:Cobertura, poliza:Poliza): Future[Poliza]
    def crearCobertura(cdgarantia: String, cdsubgarantia:String): Future[Cobertura]
  }

  //Interpretacion del algebra
  trait interpretacion extends operacionesEspecificas{
    override def tarifar(poliza:Poliza):Future[Tarifa] = {
      Future(Tarifa(1000))
    }

    override def consultarPoliza(numero:String):Future[Poliza] = {
      Future(Poliza(numero, List(Cobertura("1", "1.1"))))
    }

    override def adicionarCobertura(cobertura:Cobertura, poliza:Poliza): Future[Poliza] = {
      Future{
        Poliza(poliza.numero, cobertura::poliza.coberturas)
      }
    }

    override def crearCobertura(cdgarantia: String, cdsubgarantia:String): Future[Cobertura] = Future{
      Cobertura(cdgarantia, cdsubgarantia)
    }
  }


  test("Smoke test"){
    assert(true)
  }

  test("Debemos poder componer operaciones del algebra"){

    object objInterpretacion extends interpretacion

    val npol = "04000000000034"

    val res = for {
      c <- objInterpretacion.crearCobertura("GAR", "SUB")
      p <- objInterpretacion.consultarPoliza(npol)
      pcc <- objInterpretacion.adicionarCobertura(c,p)
      t <- objInterpretacion.tarifar(pcc)
    }yield{
      t
    }

    val tarifa = Await.result(res, 10 seconds)
    assert(tarifa.prima == 1000)
  }

}
