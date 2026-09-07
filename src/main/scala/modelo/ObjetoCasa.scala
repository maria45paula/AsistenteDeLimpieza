class ObjetoCasa(val nombre: String) {

  var ubicacionCorrecta = true

  var fragilidad = 50

  def romper(): Unit = {
    fragilidad = 0
  }

  def estaRoto(): Boolean = {
    fragilidad == 0
  }
}