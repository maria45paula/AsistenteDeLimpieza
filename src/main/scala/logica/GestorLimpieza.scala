package logica

import modelo.Casa

class GestorLimpieza(casa: Casa) {

  private var tareasCompletadas = 0

  def registrarTarea(): Unit = {
    tareasCompletadas += 1
  }

  def obtenerTareas(): Int = {
    tareasCompletadas
  }

  def porcentajeLimpieza(): Int = {
    casa.nivelLimpieza
  }
}