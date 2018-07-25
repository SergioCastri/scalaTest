package co.com.scalatraining.effects

import org.scalatest.FunSuite

import scala.collection.immutable.Seq

class OptionSuite extends FunSuite {

  test("Se debe poder crear un Option con valor"){
    val s = Option{
      1
    }
    assert(s == Some(1))
  }

  test("Se debe poder crear un Option para denotar que no hay valor"){
    val s = None
    assert(s == None)
  }

  test("Es inseguro acceder al valor de un Option con get"){
    val s = None
    assertThrows[NoSuchElementException]{
      val r = s.get
    }


  }

  test("Se debe poder hacer pattern match sobre un Option") {
    val lista: Seq[Option[String]] = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre: Option[String] = lista(1)
    var res = ""
    res = nombre match {
      case Some(nom) => nom
      case None => "NONAME"
    }
    assert(res == "NONAME")
  }

  test("Fold en Option"){
    val o = Option(1)

    val res: Int = o.fold{
      10
    }{
      x => x + 20
    }

    assert(res == 21)
  }

  test("Se debe poder saber si un Option tiene valor con isDefined") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(0)
    assert(nombre.isDefined)
  }

  test("Se debe poder acceder al valor de un Option de forma segura con getOrElse") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(1)
    val res = nombre.getOrElse("NONAME")
    assert(res == "NONAME")
  }

  test("Un Option se debe poder transformar con un map") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(0)
    val nombreCompleto: Option[String] = nombre.map(s => s + " Felipe")
    assert(nombreCompleto.getOrElse("NONAME") == "Andres Felipe")
  }

  test("Un Option se debe poder transformar con flatMap en otro Option") {
    val lista = List(Some("Andres"), None, Some("Luis"), Some("Pedro"))
    val nombre = lista(0)

    val resultado: Option[String] = nombre.flatMap(s => Option(s.toUpperCase))
    resultado.map( s => assert( s == "ANDRES"))
  }

  test("Un Option se debe poder filtrar con una hof con filter") {
    val lista = List(Some(5), None, Some(40), Some(20))
    val option0 = lista(0)
    val option1 = lista(1)
    val res0 = option0.filter(_>10)
    val res1 = option1.filter(_>10)

    assert(res0 == None)
    assert(res1 == None)
  }

  test("for comprehensions en Option") {
    val lista = List(Some(5), None, Some(40), Some(20))
    val s1 = lista(0)
    val s2 = lista(2)

    val resultado = for {
      x <- s1
      y <- s2
    } yield x+y

    assert(resultado == Some(45))
  }

  test("for comprehensions en Option 3") {
    val o1 = Some(1)
    val o2 = Some(2)
    val o3 = Some(2)
    val res: Option[Int] = o1.flatMap(x =>
      o2.flatMap(y=>
        o3.flatMap(z =>
          Option(x + y +z))))


    val resultado = for {
      x <- o1
      y <- o2
      z <- o3
    } yield x+y +z
    assert(res == resultado)
  }

  test("for comprehesions None en Option") {
    val consultarNombre = Some("Andres")
    val consultarApellido = Some("Estrada")
    val consultarEdad = None
    val consultarSexo = Some("M")

    val resultado = for {
      nom <- consultarNombre
      ape <- consultarApellido
      eda <- consultarEdad
      sex <- consultarSexo
    //} yield (nom+","+ape+","+eda+","+sex)
    } yield (s"$nom $ape, $eda,$sex")

    assert(resultado == None)
  }

  test("for comprehesions None en Option 2") {

    def consultarNombre(dni:String): Option[String] = Some("Felix")
    def consultarApellido(dni:String): Option[String] = Some("Vergara")
    def consultarEdad(dni:String): Option[String] = None
    def consultarSexo(dni:String): Option[String] = Some("M")

    val dni = "8027133"
    val resultado = for {
      nom <- consultarNombre(dni)
      ape <- consultarApellido(dni)
      eda <- consultarEdad(dni)
      sex <- consultarSexo(dni)
    //} yield (nom+","+ape+","+eda+","+sex)
    } yield (s"$nom $ape, $eda,$sex")

    assert(resultado == None)
  }

  test("pattern match en flapMap"){
    val option: Option[Int] = Some(5)

    def foo(i:Int):Option[Int]= {
      if(i%2 == 0){
        Option(i)
      }else
        None
    }

   val res1: Option[Int] = option match {
      case None => None
      case Some(x) => foo(x)
    }

    val res2 = option.flatMap(foo(_))
    assert(res1 == res2)
  }

  test("pattern match en map") {
    val option: Option[Int] = Some(6)

    def foo(i: Int): Option[Int] = {
      if (i % 2 == 0) {
        Option(i)
      } else
        None
    }

    val res1 = option match {
      case None => None
      case Some(x) => Some(foo(x))
    }
    val res2 = option.map(foo(_))
    assert(res1 ==res2)
  }

  test("pattern match en foreach") {
    val option: Option[Int] = Some(6)
    var paso = 0
    def foo(i: Int): Option[Int] = {
      if (i % 2 == 0) {
        paso = i
        Option(i)
      } else
        None
   }
    val res1 = option match {
      case None => ()
      case Some(x) => foo(x)
    }
  assert(res1 == Some(paso))
  }


  test("pattern match en exist") {
    val option: Option[Int] = Some(6)

    val res1 = option match {
      case None => false
      case Some(x) => x == 5
    }
    val res2 =  option.exists(x => x == 5)
    assert(res1 ==res2)
  }

  test("pattern match en orElse") {
    val option: Option[Int] = Some(6)
    val res1 = option.orElse(Some(2))
    val res2 = None.orElse(Some(2))
    assert(res1 == Some(6))
    assert(res2 == Some(2))

  }

  test("pattern match en getOrElse") {
    val option: Option[Int] = Some(6)
    val res1 = option.getOrElse(Option(3))
    val res2 = None.getOrElse(3)
    println(res1)
    println(res2)
    assert(res1 == 6)
    assert(res2 == 3)

  }
}





