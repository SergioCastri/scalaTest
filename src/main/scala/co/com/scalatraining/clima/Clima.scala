package co.com.scalatraining.clima

import java.util.concurrent.Executors

import scala.concurrent.{Await, ExecutionContext, Future}

class Clima {


  def clima(ec: ExecutionContext): Future[String] = {
    val clima: Future[String] = Future {
      val hiloFutureClima = Thread.currentThread().getName
      " Estamos a 23 grados"
    }(ec)
    clima
  }

  def guardar(ec: ExecutionContext): Future[String] = {
    val guardar: Future[String] = Future {
      var hiloFutureGuardar = Thread.currentThread().getName
      "Guardado en la base de datos"
    }(ec)
    guardar
  }

}
