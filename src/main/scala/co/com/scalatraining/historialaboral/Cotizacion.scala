package co.com.scalatraining.historialaboral

class Cotizacion (ibc:Int, dias:Int, salario:Int, aportante:String, periodo:String) {
  def regla1(ibc:Int, dias:Int):Boolean = dias != 0 || ibc != 0
  def regla2(salario: Int, dias:Int):Int = 30 * salario / dias
  def regla3(periodo1:String, aportante1:String, ibc1:Int, dias1:Int, periodo2:String, aportante2:String, ibc2:Int, dias2:Int):Boolean = aportante1 == aportante2 && dias1 == dias2 && ibc1 == ibc2 && periodo1 == periodo2
  def regla4(periodo1:String, aportante1:String, ibc1:Int, dias1:Int, periodo2:String, aportante2:String, ibc2:Int, dias2:Int):(Int, Int) = {
    var diasMayor = ibc1
    var salarioMayor = 0
    if(aportante1 == aportante2 && periodo1 == periodo2){
      if(dias1 < dias2){
        diasMayor = dias2
      }
      if(ibc1 < ibc2){
        salarioMayor = ibc2
      }
    }
    (diasMayor, salarioMayor)
  }
  def regla5(ibc1:Int, ibc2:Int):Int = ibc1 + ibc2
}
