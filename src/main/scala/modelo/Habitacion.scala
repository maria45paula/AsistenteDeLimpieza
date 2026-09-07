class Habitacion(val nombre: String) {

  var limpieza = 50

  private var objetos: List[ObjetoCasa] = List()

  def agregarObjeto(nombre: String): Unit = {
    objetos = objetos :+ new ObjetoCasa(nombre)
  }

  def agregarObjeto(objeto: ObjetoCasa): Unit = {
    objetos = objetos :+ objeto
  }

  def limpiar(): Unit = {
    limpieza += 20

    if (limpieza > 100)
      limpieza = 100
  }

  def obtenerObjetos(): List[ObjetoCasa] = {
    objetos
  }
}