package co.com.scalatraining.tuples

import org.scalatest.FunSuite

class TupleSuite  extends FunSuite {

  test("Una tupla se debe poder crear"){
    val tupla = (1, 2,"3", List(1, 2, 3))
    assert(tupla._2 == 2)
    assert(tupla._4.tail.head == 2)
  }

  test("Una tupla a partir del head de cada tupla"){
    val tupla : (List[Int],List[Int],List[Int],List[Int],List[Int])= ( List(1, 2, 3), List(1, 2, 3), List(1, 2, 3), List(1, 2, 3), List(1, 2, 3))
    val tupla2 : (Int,Int,Int,Int,Int) = (tupla._1.head,tupla._2.head,tupla._3.head,tupla._4.head,tupla._5.head)
    assert(tupla2 == (1,1,1,1,1))
  }


}
