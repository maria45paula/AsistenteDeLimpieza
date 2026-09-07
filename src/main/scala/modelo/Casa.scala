package modelo

class Casa {

  var nivelLimpieza = 50


  val habitaciones: List[Habitacion] = List(

    new Habitacion("Sala"),

    new Habitacion("Cocina"),

    new Habitacion("Habitación"),

    new Habitacion("Baño")

  )


  def limpiar(cantidad: Int): Unit = {

    nivelLimpieza += cantidad

    if (nivelLimpieza > 100)
      nivelLimpieza = 100

  }


  def empeorarLimpieza(cantidad: Int): Unit = {

    nivelLimpieza -= cantidad

    if (nivelLimpieza < 0)
      nivelLimpieza = 0

  }


  def buscarHabitacion(
                        nombre: String
                      ): Option[Habitacion] = {

    habitaciones.find(
      habitacion =>
        habitacion.nombre == nombre
    )

  }


  def limpiarHabitacion(
                         nombre: String
                       ): Unit = {

    val habitacion =
      buscarHabitacion(nombre)

    habitacion match {

      case Some(habitacionEncontrada) =>

        habitacionEncontrada.limpiar()

        nivelLimpieza += 5

        if (nivelLimpieza > 100)
          nivelLimpieza = 100


      case None =>

        println(
          "Habitación no encontrada."
        )

    }

  }


  def moverObjeto(
                   objeto: String,
                   habitacionDestino: String
                 ): Unit = {

    val destino =
      buscarHabitacion(habitacionDestino)

    destino match {

      case Some(habitacion) =>

        habitacion.agregarObjeto(objeto)


      case None =>

        println(
          "Habitación no encontrada."
        )

    }

  }

}