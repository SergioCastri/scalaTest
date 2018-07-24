package co.com.scalatraining.collections


import org.scalatest.FunSuite

import scala.collection.immutable.Stack

class StackSuite extends FunSuite {
  test("en una pila se puede apilar") {

    val pila = Stack[Int](1,2,3)
    val pila2 = pila.push(4)
    assert(pila2 == Stack(4,1,2,3))
  }

  test("en una pila se puede desapilar") {

    val pila = Stack[Int](1,2,3)
    val pila2 = pila.pop
    assert(pila2 == Stack(2,3))
  }

  test("en una pila se puede desapilar en tuplas entre lo que se desapilo y el resto del pila") {

    val pila = Stack[Int](1,2,3,4,5)
    val pila2 = pila.pop2
    println(pila2)
    assert(pila2 == (1,Stack(2,3,4,5)))
  }
  test("se puede conocer el tope") {

    val pila = Stack[Int](1,2,3,4,5)
    val tope = pila.head
    assert(tope == 1)

  }

  test("se puede recorrer una pila") {

    val pila = Stack[Int](1,2,3,4,5)
    var suma = 0
    pila.foreach((x) =>
      suma += x)
    println(suma)
    assert(suma == 15)

  }



  test("una pila puede estar vacia") {

    val pila = Stack[Int]()
    println(pila)
    assert(true == pila.isEmpty)

  }

  test("una pila puede se puede reversar") {

    val pila = Stack[Int](1,2)
    val res = pila.reverse
    assert(Stack(2,1) == res)

  }

  test("puedo filtrar elementos de una pila" ){

    val pila = Stack[Int](1,2,3,4)
    val pila2 = Stack[Int](3,4,5,6)

    val res = pila.filter(x=>x%2 == 0)

    assert(Stack(2,4) == res)

  }



}


