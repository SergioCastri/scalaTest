package co.com.scalatraining.modeling

import java.util.concurrent.Executors

import org.scalatest.FunSuite
import scala.language.postfixOps
import scala.util.{Try, Failure, Success}
import scala.concurrent.{ExecutionContext, Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ModelingSuite extends FunSuite {


  test("Debemos poder componer operaciones del algebra"){

    import co.com.scalatraining.modelling.dominio.servicios.InterpretacionServicioPoliza._

    val npol = "04000000000034"

    val res = for {
      c   <- crearCobertura("GAR", "SUB")
      p   <- consultarPoliza(npol)
      pcc <- adicionarCobertura(c,p)
      t   <- tarifar(pcc)
    }yield{
      t
    }

    val tarifa = Await.result(res, 10 seconds)
    assert(tarifa.prima == 1000)
  }


}
