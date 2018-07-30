package co.com.scalatraining.effects

import java.util.Random
import java.util.concurrent.Executors


import org.scalatest.FunSuite

import scala.collection.immutable.{IndexedSeq, Seq}
import scala.language.postfixOps
import scala.util.{Failure, Success}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class FutureSuite extends FunSuite {

  test("Un futuro se puede crear") {

    val hiloPpal = Thread.currentThread().getName

    var hiloFuture = ""

    println(s"Test 1 - El hilo ppal es ${hiloPpal}")

    val saludo: Future[String] = Future {
      hiloFuture = Thread.currentThread().getName
      println(s"Test 1 - El hilo del future es ${hiloFuture}")

     //Thread.sleep(500)
      "Hola"

    }
    println(saludo.foreach(println))


    val resultado: String = Await.result(saludo, 10 seconds)   //NUNCA un await
    assert(resultado == "Hola")
    assert(hiloPpal != hiloFuture)
  }

  test("map en Future") {


    val t1 = Thread.currentThread().getName
    println(s"Test 2 - El hilo del ppal es ${t1}")


    val saludo = Future {
      val t2 = Thread.currentThread().getName
      println(s"Test 2 - El hilo del future es ${t2}")

      Thread.sleep(500)
      "Hola"

    }

    Thread.sleep(5000)

    val saludo2 = Future{
      println(s"Test 2 - Hilo normal ${Thread.currentThread().getName}")
    }

    val saludoCompleto = saludo.map(mensaje => {
      val t3 = Thread.currentThread().getName
      println(s"Test 2 - El hilo del map es ${t3}")

      mensaje + " muchachos"
    })


    val resultado = Await.result(saludoCompleto, 10 seconds)
    assert(resultado == "Hola muchachos")
  }

  test("Se debe poder encadenar Future con for-comp") {
    val f1 = Future {
      Thread.sleep(200)
      1
    }

    val f2 = Future {
      Thread.sleep(200)
      2
    }

    val f3: Future[Int] = for {
      res1 <- f1
      res2 <- f2
    } yield res1 + res2
    val res = Await.result(f3, 10 seconds)
    assert(res == 3)
  }

  test("Se debe poder encadenar Future con for-comp 2") {
    val f1 = Future {
      Thread.sleep(200)
      1
    }

    val f2 = Future {
      Thread.sleep(200)
      2 / 0
    }

    val f3: Future[Int] = for {
      res1 <- f1
      res2 <- f2.recover{case e:Exception => 2}
      res3 <- f1
    } yield res1 + res2 +res3

    val res = Await.result(f3, 10 seconds)
    assert(res == 4)
  }

  test("Se debe poder manejar el error de un Future de forma imperativa") {
    val divisionCero = Future {
      Thread.sleep(100)
      10 / 0
    }
    var error = false

    val r: Unit = divisionCero.onFailure {
      case e: Exception => error = true
    }



    Thread.sleep(1000)

    assert(error == true)
  }

  test("Se debe poder manejar el exito de un Future de forma imperativa") {

    val division = Future {
      5
    }

    var r = 0

    val f: Unit = division.onComplete {
    case Success(res) => r = res
    case Failure(e) => r = 1
    }

    Thread.sleep(150)

    assert(r == 5)
  }

  test("Se debe poder manejar el exito de un Future de forma funcional") {

    val division = Future {
      5 / 0
    }

    var r = 0

    Thread.sleep(150)
    val fe: Future[Int] = division.map(x => x + 1).recover{case e: Exception => 4}

    r = r + Await.result(fe, 10 seconds)
    assert(r == 4)
  }

  test("Se debe poder manejar el exito de un Future de forma funcional 2") {

    val division = Future {
      5
    }

    var r = 0

    Thread.sleep(150)
    val fe: Future[Int] = division.map(x => x + 1).recover{case e: Exception => 4}

    r = r + Await.result(fe, 10 seconds)
    assert(r == 6)
  }

  test("Se debe poder manejar el error de un Future de forma funcional sincronicamente") {

    var threadName1 = ""
    var threadName2 = ""

    val divisionPorCero = Future {
      threadName1 = Thread.currentThread().getName
      Thread.sleep(100)
      10 / 0
    }.recover {
      case e: ArithmeticException => {
        threadName2 = Thread.currentThread().getName
        "No es posible dividir por cero"
      }
    }

    val res = Await.result(divisionPorCero, 10 seconds)

    assert(threadName1 == threadName2)
    assert(res == "No es posible dividir por cero")

  }

  test("Se debe poder manejar el error de un Future de forma funcional asincronamente") {

    var threadName1 = ""
    var threadName2 = ""

    implicit val ecParaPrimerHilo = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

    val f1 = Future {
      threadName1 = Thread.currentThread().getName
      2/0
    }(ecParaPrimerHilo)
    .recoverWith {
      case e: ArithmeticException => {

        implicit val ecParaRecuperacion = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

        Future{
          threadName2 = Thread.currentThread().getName
          1
        }(ecParaRecuperacion)
      }
    }

    val res = Await.result(f1, 10 seconds)

    println(s"Test en recoverWith thread del fallo: $threadName1")
    println(s"Test en recoverWith thread de recuperacion: $threadName2")

    assert(threadName1 != threadName2)
    assert(res==1)
  }

  test("Los future **iniciados** fuera de un for-comp deben iniciar al mismo tiempo") {

    val timeForf1 = 100
    val timeForf2 = 200
    val timeForf3 = 100

    val additionalTime = 50D

    val estimatedElapsed = (Math.max(Math.max(timeForf1, timeForf2), timeForf3) + additionalTime)/1000

    val f1 = Future {
      Thread.sleep(timeForf1)
      1
    }
    val f2 = Future {
      Thread.sleep(timeForf2)
      2
    }
    val f3 = Future {
      Thread.sleep(timeForf3)
      3
    }

    val t1: Long = System.nanoTime()

    val resultado = for {
      a <- f1
      b <- f2
      c <- f3
    } yield (a+b+c)

    val res = Await.result(resultado, 10 seconds)
    val elapsed = (System.nanoTime() - t1) / 1.0E09

    println(s"Future **iniciados** fuera del for-comp estimado: $estimatedElapsed real: $elapsed")
    assert(elapsed <= estimatedElapsed)
    assert(res == 6)

  }

  test("Los future **definidos** fuera de un for-comp deben iniciar secuencialmente") {

    val timeForf1 = 100
    val timeForf2 = 300
    val timeForf3 = 500

    val estimatedElapsed:Double = (timeForf1 + timeForf2 + timeForf3)/1000

    def f1 = Future {
      Thread.sleep(timeForf1)
      1
    }
    def f2 = Future {
      Thread.sleep(timeForf2)
      2
    }
    def f3 = Future {
      Thread.sleep(timeForf3)
      3
    }

    val t1 = System.nanoTime()

    val resultado = for {
      a <- f1
      b <- f2
      c <- f3
    } yield (a+b+c)

    val res = Await.result(resultado, 10 seconds)
    val elapsed = (System.nanoTime() - t1) / 1.0E09

    println(s"Future **definidos** fuera del for-comp estimado: $estimatedElapsed real: $elapsed")

    assert(elapsed >= estimatedElapsed)
    assert(res == 6)

  }

  test("Los future declarados dentro de un for-comp deben iniciar secuencialmente") {

    val t1 = System.nanoTime()

    val timeForf1 = 100
    val timeForf2 = 100
    val timeForf3 = 100

    val estimatedElapsed = (timeForf1 + timeForf2 + timeForf3)/1000

    val resultado = for {
      a <- Future {
        Thread.sleep(timeForf1)
        1
      }
      b <- Future {
        Thread.sleep(timeForf2)
        2
      }
      c <- Future {
        Thread.sleep(timeForf3)
        3
      }
    } yield (a+b+c)

    val res = Await.result(resultado, 10 seconds)
    val elapsed = (System.nanoTime() - t1) / 1.0E09

    assert(elapsed >= estimatedElapsed)
    assert(res == 6)
  }

  test("Future.sequence"){

    val listOfFutures: List[Future[Int]] = Range(1, 11).map(Future.successful(_)).toList

    val resSequence: Future[List[Int]] = Future.sequence {
      listOfFutures
    }

    val resFuture = resSequence.map(l => l.sum/l.size)

    val res = Await.result(resFuture, 10 seconds)

    assert(res ==  Range(1,11).sum/Range(1,11).size)

  }

  test("Future.traverse"){
    def foo(i:List[Int]):Future[Int]=Future.successful(i.sum/i.size)
    val resFuture = Future.traverse(Range(1,11).map(Future.successful(_))){
      x => x
    }.map(l => l.sum/l.size)

    val res = Await.result(resFuture, 10 seconds)

    assert(res ==  Range(1,11).sum/Range(1,11).size)

  }

  test("Clima") {
    def clima(ec: ExecutionContext): Future[String] = {
      val clima: Future[String] = Future {
        val hiloFutureClima = Thread.currentThread().getName
        " Estamos a 23 grados"
      }(ec)
      Await.result(clima, 10 seconds)

      clima
    }

    def guardar(ec: ExecutionContext): Future[String] = {
      val guardar: Future[String] = Future {
        var hiloFutureGuardar = Thread.currentThread().getName
        "Guardado en la base de datos"
      }(ec)
      val r = Await.result(guardar, 10 seconds)
      guardar
    }

    val contextoClima = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(5))
    val contextoGuardar = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))


    val res= Range(1,15).map(x=> clima(contextoClima).flatMap(y=> guardar(contextoGuardar).map(z=>z+y))).toList
    val r = res.map(x=> Await.result(x, 10 seconds))
    //val res= Range(1,10).map(x=> clima(contextoClima))
    println(r)
    assert(r == List("Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados","Guardado en la base de datos Estamos a 23 grados","Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados", "Guardado en la base de datos Estamos a 23 grados"))

  }




}