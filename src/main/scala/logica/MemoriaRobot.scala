package logica

class MemoriaRobot {

  private var historial: List[String] = List()

  def guardar(accion: String): Unit = {
    historial = historial :+ accion
  }

  def obtenerHistorial(): List[String] = {
    historial
  }

  def ultimaAccion(): String = {

    if (historial.isEmpty)
      "Todavía no he hecho nada."
    else
      historial.last
  }

  def cantidadErrores(): Int = {
    historial.count(accion => accion.contains("ERROR"))
  }
}