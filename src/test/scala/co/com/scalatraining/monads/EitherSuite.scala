package co.com.scalatraining.monads

import org.scalatest.FunSuite

class EitherSuite extends FunSuite{

  test("Either left"){
    val e = Left(1)
    assert(e.isLeft)
  }

  test("Either right"){
    val e = Right(1)
    assert(e.isRight)
  }

  def foo(i:Int): Either[String, Int] = {
    if(i%2==0) Right(i)
    else Left("El numero es impar")
  }

  test("Either left or right"){

    assert(foo(2).isRight)
  }

  test("Either test"){
    val r: Int  = foo(2).fold[Int]( s => {
        assert(false)
        1
      }
    , i => {
        assert(true)
        6
      }
  )

    assert(r == 6)

  }

  test("Swap un Either"){
    val res: Either[Int, String] = foo(1).swap

    assert(res.isRight)



  }

}
