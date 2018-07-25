package co.com.scalatraining.historialaboral

class Limpiar () {
  def clean(entrada: List[Conti]):Map[String, Int] = {
    entrada
      .filter(x => x.dias != 0 && x.ibc != 0)
      .map(x => Conti(x.periodo, x.aportante, x.dias, x.ibc * 30 / x.dias))
      .distinct
      .groupBy(x => (x.periodo, x.aportante))
      .map(x => x._1 -> x._2.foldLeft(0){(acu, conti) =>
        if (acu < conti.ibc){
          conti.ibc
        }else{
          acu
        }})
      .groupBy(x => x._1._1)
      .map(x => x._1 -> x._2
        .foldLeft(0){(acu, item) => acu + item._2})
  }
}
