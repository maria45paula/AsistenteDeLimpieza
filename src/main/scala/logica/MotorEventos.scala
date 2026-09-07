package logica

import modelo.Robot
import modelo.Casa
import scala.util.Random

object MotorEventos {

  def generarEvento(accion: String, robot: Robot, casa: Casa): String = {

    val numero = Random.nextInt(100)

    accion match {

      case "limpiar" =>

        if (numero < 50) {
          "Limpieza exitosa."
        }

        else if (numero < 70) {
          casa.empeorarLimpieza(5)
          "ERROR: Limpié, pero ensucié otra parte."
        }

        else if (numero < 90) {
          robot.ubicacion = "Baño"
          "ERROR: Me confundí de habitación."
        }

        else {
          casa.empeorarLimpieza(15)
          "ERROR: DESASTRE. El robot tiró todo al suelo."
        }

      case "recoger" =>

        if (numero < 40) {
          "Recogí todo correctamente."
        }

        else if (numero < 65) {
          "ERROR: Recogí la ropa y la guardé en la nevera."
        }

        else if (numero < 85) {
          "ERROR: Encontré la ropa, pero la puse en el baño."
        }

        else {
          casa.moverObjeto("Ropa", "Cocina")
          "ERROR: La ropa terminó en la cocina."
        }


      case "organizar" =>

        if (numero < 50) {
          "Todo organizado correctamente."
        }

        else if (numero < 75) {
          "ERROR: Organicé los libros en el baño."
        }

        else if (numero < 90) {
          "ERROR: Organicé los zapatos dentro de la nevera."
        }

        else {
          "ERROR: Creo que empeoré la organización."
        }


      case _ =>
        "No ocurrió nada."
    }
  }
}