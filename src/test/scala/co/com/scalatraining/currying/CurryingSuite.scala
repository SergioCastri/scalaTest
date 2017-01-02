package co.com.scalatraining.currying

import org.scalatest.{Matchers, FunSuite}

class CurryingSuite extends FunSuite with Matchers{

  test("Una funcion puede recibir mas de un parametro gracias al currying"){
    //Esta es una función curreada
    def f(a:Int)(b:Int) ={
      a+b
    }

    //La evaluación de f con uno de sus parámetros retorna
    //otra función con la firma Tipo_del_segundo_par => Tipo_del_res
    // HINT: presione alt + enter al lado de b para que extracte el tipo de b
    val e1 = f(1)_

    //b puede ser entonces evaluada
    val e2 =  e1(1)

    assert(e2==2)
  }

  test("Una funcion curreada puede ser expresada de otra forma"){
    //Esta es una función curreada
    def f(a:Int) = { b:Int =>
      a+b
    }

    val e1 = f(1)

    //b puede ser entonces evaluada
    val e2 =  e1(1)

    assert(e2==2)
  }

}