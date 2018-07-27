package co.com.scalatraining.consulta


import java.util.concurrent.Executors

import scala.concurrent.{Await, ExecutionContext, Future}

class Consultas (){

    implicit val context = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))

    val repo1 = new Repositorio("sergio", "scalaTraining", "scala", 100)
    val repo2 = new Repositorio("carlos", "pi", "scala", 200)
    val repo3 = new Repositorio("sergio", "fizzbuzz", "go", 30)
    val repo4 = new Repositorio("sergio", "javaTraining", "java", 500)

    val listaUsuarios = List(repo1, repo2, repo3, repo4)

    def obtenerUsuarioCompleto(nombre: String): Future[List[RepositorioPorUsuario]] = {
      val repo = Future {
        val nameFutureC1 = Thread.currentThread().getName
        listaUsuarios.filter(x => x.nickname == "sergio").map(y => RepositorioPorUsuario(y.nombre, y.lenguaje, y.lineas))
      }(context)
      repo
    }

  def obtenerTodo(lista : List[RepositorioPorUsuario]): Future[List[Respuesta]] = {
    val listaCantidadLenguajes = Future {
    val nameFutureC2 = Thread.currentThread().getName
      val ouc = obtenerUsuarioCompleto("sergio")
      ouc.map(x => x.groupBy(x=> x.lenguaje).map(y => y._1 -> y._2.length).toList)
    }(context)
    listaCantidadLenguajes
  }


}
