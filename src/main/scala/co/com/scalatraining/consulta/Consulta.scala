package co.com.scalatraining.consulta
import java.util.concurrent.Executors
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
class Consulta () {
  implicit val context = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(3))
  val servicio = new Servicio()
  def obtenerUsuarioCompleto(nombre: String): Future[Respuesta] = {
    val repo = Future {
      val nameFutureC1 = Thread.currentThread().getName
      val listaUsuarios = servicio.servicioGithub()
      val lista =  listaUsuarios.filter(x => x.nickname == nombre).map(y => RepositorioPorUsuario(y.nombre, y.lenguaje, y.lineas))
      val cantidadLenguajes = obtenerLenguajes(lista)
      val tamañoRepo = obtenerTamaños(lista)
      cantidadLenguajes.map(x=>tamañoRepo.map(y=>Respuesta(y,x)))
    }(context)
    //Await.result(repo, 10 seconds)
    //println(repo.flatten.flatten)
    repo.flatten.flatten
  }
  def obtenerLenguajes(lista: List[RepositorioPorUsuario]): Future[List[NumeroLenguaje]] = {
    val listaCantidadLenguajes = Future {
      val nameFutureC2 = Thread.currentThread().getName
      lista.groupBy(x => x.lenguaje).map(y => y._1 -> y._2.length).toList.map(z=>NumeroLenguaje(z._1, z._2))
    }(context)
    //  Await.result(listaCantidadLenguajes, 10 seconds)
    listaCantidadLenguajes
  }
  def obtenerTamaños(lista: List[RepositorioPorUsuario]): Future[List[RepositorioPorUsuario]] = {
    val listaTamañoRepo = Future {
      val nameFutureC3 = Thread.currentThread().getName
      lista.sortBy(x=>x.lineas).reverse
    }(context)
    //    Await.result(listaTamañoRepo, 10 seconds)
    listaTamañoRepo
  }
}