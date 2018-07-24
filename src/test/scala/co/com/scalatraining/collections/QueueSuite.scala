package co.com.scalatraining.collections

import org.scalatest.FunSuite
import scala.collection.immutable.Queue


class QueueSuite extends FunSuite {
  test("en una cola se puede encolar") {

    val cola = Queue[Int](1,2,3)
    val cola2 = cola.enqueue(4)
    assert(cola2 == Queue(1,2,3,4))
  }

  test("en una cola se puede desencolar") {

    val cola = Queue[Int](1,2,3)
    val cola2 = cola.dequeue
    assert(cola2 == (1,(Queue(2,3))))
  }



  test("se puede conocer el primero en una cola") {

    val cola = Queue[Int](1,2,3,4,5)
    val primero = cola.head
    assert(primero == 1)

  }

  test("se puede recorrer una cola") {

    val cola = Queue[Int](1,2,3,4,5)
    var suma = 0
    cola.foreach((x) =>
      suma += x)
    println(suma)
    assert(suma == 15)

  }



  test("una cola puede estar vacia") {

    val cola = Queue[Int]()
    assert(true == cola.isEmpty)

  }

  test("una cola puede se puede reversar") {

    val cola = Queue[Int](1,2)
    val res = cola.reverse
    assert(Queue(2,1) == res)

  }

  test("puedo filtrar elementos de una cola" ){

    val cola = Queue[Int](1,2,3,4)

    val res = cola.filter(x=>x%2 == 0)

    assert(Queue(2,4) == res)

  }

  test("puedo juntar dos colas" ){

    val cola = Queue[Int](1,2,3,4)
    val cola2 = Queue[Int](1,2,3,4)
    val res = cola.enqueue(cola2.filter(x=>x%1==0))

    assert(Queue(1,2,3,4,1,2,3,4) == res)

  }

}
