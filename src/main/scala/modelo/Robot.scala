package modelo

import logica.{MemoriaRobot, MotorEventos}

class Robot(val nombre: String, val memoria: MemoriaRobot) {

  var energia = 100

  var ubicacion = "Sala"


  def ejecutar(accion: String,casa: Casa, lugar: String): String = {

    if (energia <= 0) {

      return "No puedo hacer nada. Estoy sin batería."

    }


    // El robot se mueve al lugar seleccionado
    ubicacion = lugar


    val resultado =
      accion match {

        case "limpiar" =>
          limpiar(casa)

        case "recoger" =>
          recoger(casa)

        case "organizar" =>
          organizar(casa)

        case _ =>
          " No entiendo esa orden."

      }


    memoria.guardar(resultado)

    resultado
  }


  private def limpiar(casa: Casa): String = {

    energia -= 10

    casa.limpiarHabitacion(ubicacion)

    MotorEventos.generarEvento("limpiar", this, casa)

  }


  private def recoger(casa: Casa): String = {

    energia -= 8

    MotorEventos.generarEvento("recoger", this, casa)

  }


  private def organizar(casa: Casa): String = {

    energia -= 12

    MotorEventos.generarEvento("organizar", this, casa)

  }


  def cargar(): Unit = {

    energia = 100

  }

}