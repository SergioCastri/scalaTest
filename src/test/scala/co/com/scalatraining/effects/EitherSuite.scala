package co.com.scalatraining.effects

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
    else Left(s"El numero $i es impar")
  }

  test("Either left or right"){

    assert(foo(2).isRight)
  }

  test("Un Either se debe poder fold por la derecha"){
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


  test("Un Either se debe poder fold por la izquierda"){
    val r: Int  = foo(7).fold[Int]( s => {
      assert(true)
      1
    }
      , i => {
        assert(false)
        6
      }
    )

    assert(r == 1)

  }

  test("Swap un Either"){
    val res: Either[String, Int] = foo(2)
    val res2: Either[Int, String] = res.swap
    assert(res2.isLeft)

  }

  test("Swap un Either Left"){
    val res: Either[String, Int] = foo(1)
    val res2: Either[Int, String] = res.swap
    assert(res2.isRight)
    assert(res2== Right("El numero 1 es impar"))
  }

  test("for comp en Either todos Right"){
    val res = for{
      x <- foo(2)
      y <- foo(4)
      z <- foo(6)
    } yield x + y + z

    assert(res == Right(12))
  }

  test("for comp en Either con un Left"){
    val res = for{
      x <- foo(2)
      y <- foo(1)
      z <- foo(6)
    } yield x + y + z

    assert(res == Left("El numero 1 es impar"))
  }

}
