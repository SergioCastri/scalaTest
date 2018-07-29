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
    Await.result(res, 30 seconds)
    println(res)
    val res2 = Future(Respuesta(List(RepositorioPorUsuario("javaTraining","java",500), RepositorioPorUsuario("scalaTraining","scala",100), RepositorioPorUsuario("fizzbuzz","scala",30)),List(NumeroLenguaje("scala",2), NumeroLenguaje("java",1))))
    Await.result(res2, 20 seconds)
    assert(res ==  res2)
  }

  test ("prueba de sevicios de github  2"){
    val consulta = new Consulta()
    val res: Future[Respuesta] = consulta.obtenerUsuarioCompleto("carlos")
    Await.result(res, 30 seconds)
    println(res)
    Thread.sleep(1000)
    val res2 = Future(Respuesta(List(RepositorioPorUsuario("pi","scala",200)),List(NumeroLenguaje("scala",1))))
    Await.result(res2, 30 seconds)

    println("1. " + res)
    println("2. " + res2)
    assert(res == res2)
  }
}
