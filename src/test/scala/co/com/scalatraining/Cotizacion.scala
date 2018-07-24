
package co.com.scalatraining

import org.scalatest.FunSuite

class Cotizacion () extends FunSuite {


  test ("prueba"){
    case class Conti(periodo:String, aportante:String, ibc:Int, dias:Int)
    case class CotizacionTest (c1:Conti, c2:Conti) {
      def regla1:Boolean = c1.dias != 0 || c1.ibc != 0
      def regla2:Int = 30 * c1.ibc / c1.dias
      def regla3:Boolean = c1.aportante == c2.aportante && c1.dias == c2.dias && c1.ibc == c2.ibc && c1.periodo == c2.periodo
      def regla4:(Int, Int) = {
        var diasMayor = c1.ibc
        var salarioMayor = 0
        if(c1.aportante == c2.aportante && c1.periodo == c2.periodo){
          if(c1.dias < c2.dias){
            diasMayor = c2.dias
          }
          if(c1.ibc < c2.ibc){
            salarioMayor = c2.ibc
          }
        }
        (diasMayor, salarioMayor)
      }
      def regla5:Int = c1.ibc + c2.ibc
    }
    val c1: Conti = new Conti("2018/07", "S4N", 10, 10000000)
    val c2: Conti = new Conti("2018/07", "S4N", 20, 0)
    val c3: Conti = new Conti("2018/08", "S4N", 30, 20000000)
    val c4: Conti = new Conti("2018/08", "S4N", 30, 20000000)
    val c5: Conti = new Conti("2018/09", "S4N", 15, 10000000)
    val c6: Conti = new Conti("2018/09", "S4N", 10, 20000000)
    val c7: Conti = new Conti("2018/10", "S4N", 15, 10000000)
    val c8: Conti = new Conti("2018/10", "UDEA", 15, 5000000)
    val entrada = List(c1,c2,c3,c4,c5,c6,c7,c8)
    entrada.foreach((x) =>

    )
    println(entrada)
    val res = entrada.filter(x => x.)
  }
}

