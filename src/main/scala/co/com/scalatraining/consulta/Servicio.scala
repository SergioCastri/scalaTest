package co.com.scalatraining.consulta

class Servicio {

  def servicioGithub ():List[Repositorio] ={
    val repo1 = new Repositorio("sergio", "scalaTraining", "scala", 100)
    val repo2 = new Repositorio("carlos", "pi", "scala", 200)
    val repo3 = new Repositorio("sergio", "fizzbuzz", "scala", 30)
    val repo4 = new Repositorio("sergio", "javaTraining", "java", 500)

    List(repo1, repo2, repo3, repo4)
  }


}