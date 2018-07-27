package co.com.scalatraining.consulta

case class Repositorio(nickname:String, nombre:String, lenguaje:String,  lineas:Int)
case class RepositorioPorUsuario(nombre:String, lenguaje:String, lineas:Int)
case class ResRepositorio(lenguaje:String, lineas:Int)
case class NumeroLenguaje(lenguaje: String, cantidad: Int)
case class Respuesta(repos: List[RepositorioPorUsuario], cantidadLenguajes:List[NumeroLenguaje])
case class UsuarioCompleto(repositorios: List[Repositorio])