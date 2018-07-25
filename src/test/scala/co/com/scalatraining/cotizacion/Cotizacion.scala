
package co.com.scalatraining.cotizacion

import co.com.scalatraining.historialaboral.{Conti, Limpiar}
import org.scalatest.FunSuite

class Cotizacion () extends FunSuite {

  test ("prueba"){
    val c1: Conti = new Conti("2018/07", "S4N", 10, 10000000)
    val c2: Conti = new Conti("2018/07", "S4N", 20, 0)
    val c3: Conti = new Conti("2018/08", "S4N", 30, 20000000)
    val c4: Conti = new Conti("2018/08", "S4N", 30, 20000000)
    val c5: Conti = new Conti("2018/09", "S4N", 15, 10000000)
    val c6: Conti = new Conti("2018/09", "S4N", 10, 20000000)
    val c7: Conti = new Conti("2018/10", "S4N", 15, 10000000)
    val c8: Conti = new Conti("2018/10", "UDEA", 15, 5000000)
    val entrada = List(c1,c2,c3,c4,c5,c6,c7,c8)

    val prueba: Limpiar = new Limpiar()
    assert(prueba.clean(entrada) == Map("2018/08" -> 40000000, "2018/07" -> 20000000, "2018/10" -> 30000000, "2018/09" -> 40000000))
  }
}

