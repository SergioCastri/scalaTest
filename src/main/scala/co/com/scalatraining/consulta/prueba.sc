import java.util.concurrent.Executors

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps
import scala.concurrent.duration._

case class Repositorio(nickname:String, nombre:String, lenguaje:String,  lineas:Int)
case class RepositorioPorUsuario(nombre:String, lenguaje:String,  lineas:Int)
case class NumeroLenguaje(lenguaje: String, cantidad: Int)

val repo1 = new Repositorio("sergio", "scalaTraining", "scala", 100)
val repo2 = new Repositorio("carlos", "pi", "scala", 200)
val repo3 = new Repositorio("sergio", "fizzbuzz", "scala", 30)
val repo4 = new Repositorio("sergio", "javaTraining", "java", 500)

val listaUsuarios = List(repo1, repo2, repo3, repo4)

val context = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))


val repo = Future{
  listaUsuarios.filter(x => x.nickname == "sergio").map(y => RepositorioPorUsuario(y.nombre, y.lenguaje, y.lineas))

}(context)

Thread.sleep(1000)
Await.result(repo, 10 seconds)

val r = List(RepositorioPorUsuario("scalaTraining","scala",100), RepositorioPorUsuario("fizzbuzz","scala",30), RepositorioPorUsuario("javaTraining","java",500))

val mapa = r.groupBy(x=> x.lenguaje).map(x => x._1 -> x._2.size).toList
