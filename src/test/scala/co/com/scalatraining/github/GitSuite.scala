package co.com.scalatraining.github
import co.com.scalatraining.consulta.{Consulta, NumeroLenguaje, Repositorio, RepositorioPorUsuario, ResRepositorio, Respuesta, UsuarioCompleto}
import org.scalatest.FunSuite

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

class GitSuite () extends FunSuite{
  test ("prueba de sevicios de github"){
    val consulta = new Consulta()
    val res: Future[Respuesta] = consulta.obtenerUsuarioCompleto("sergio")

    val r = Await.result(res, 30 seconds)
    val res2 = Respuesta(List(RepositorioPorUsuario("javaTraining","java",500), RepositorioPorUsuario("scalaTraining","scala",100), RepositorioPorUsuario("fizzbuzz","scala",30)),List(NumeroLenguaje("scala",2), NumeroLenguaje("java",1)))
    assert(r ==  res2)
  }

  test ("prueba de sevicios de github 2"){
    val consulta = new Consulta()
    val res: Future[Respuesta] = consulta.obtenerUsuarioCompleto("carlos")
    val r = Await.result(res, 30 seconds)
    val res2 = Respuesta(List(RepositorioPorUsuario("pi","scala",200)),List(NumeroLenguaje("scala",1)))
    assert(r == res2)
  }
}
