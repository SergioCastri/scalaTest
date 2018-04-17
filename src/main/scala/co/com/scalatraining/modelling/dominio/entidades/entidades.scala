package co.com.scalatraining.modelling.dominio.entidades

case class Poliza(numero:String, coberturas:List[Cobertura])
case class Persona(dni:String, nombre:String)
case class Asegurado(dni:String, nombre:String)
case class Cobertura(cdgarantia:String, cdsubgarantia:String)
case class Tarifa(prima:Int)
