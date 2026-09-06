package modelo

case class Casa(
    objetos: List[ObjetoCasa] = List.empty,

    limpieza: Map[Habitacion, Int] =
      Habitacion.todas
        .map(habitacion => habitacion -> 40)
        .toMap,

    puntuacion: Int = 0
) {

  // ============================================================
  // OBJETOS DE LA CASA
  // ============================================================

  // Obtener los objetos que están en una habitación
  def objetosEn(habitacion: Habitacion): List[ObjetoCasa] =
    objetos.filter(_.habitacion == habitacion)

  // Agregar un objeto a la casa
  def agregarObjeto(objeto: ObjetoCasa): Casa =
    copy(
      objetos = objetos :+ objeto
    )

  // Mover un objeto a otra habitación
  def moverObjeto(
      objeto: Objeto,
      destino: Habitacion
  ): Casa =
    copy(
      objetos = objetos.map { objetoCasa =>
        if (objetoCasa.objeto == objeto)
          objetoCasa.copy(habitacion = destino)
        else
          objetoCasa
      }
    )

  // Eliminar un objeto de la casa
  def eliminarObjeto(objeto: Objeto): Casa =
    copy(
      objetos = objetos.filterNot(_.objeto == objeto)
    )

  // ============================================================
  // LIMPIEZA DE LA CASA
  // ============================================================

  // Cambiar el nivel de limpieza.
  // El nivel siempre queda entre 0 y 100.
  def cambiarLimpieza(
      habitacion: Habitacion,
      nivel: Int
  ): Casa =
    copy(
      limpieza = limpieza.updated(
        habitacion,
        nivel.max(0).min(100)
      )
    )

  // Aumentar el nivel de limpieza
  def mejorarLimpieza(
      habitacion: Habitacion,
      cantidad: Int
  ): Casa =
    cambiarLimpieza(
      habitacion,
      limpieza.getOrElse(habitacion, 0) + cantidad
    )

  // ============================================================
  // PUNTUACIÓN
  // ============================================================

  // Sumar puntos
  def sumarPuntos(cantidad: Int): Casa =
    copy(
      puntuacion = puntuacion + cantidad
    )

  // Restar puntos sin permitir una puntuación negativa
  def restarPuntos(cantidad: Int): Casa =
    copy(
      puntuacion = (puntuacion - cantidad).max(0)
    )

  // Aplicar una variación positiva o negativa
  def aplicarPuntos(cantidad: Int): Casa =
    copy(
      puntuacion = (puntuacion + cantidad).max(0)
    )

  // ============================================================
  // PUNTOS POR ACCIONES CORRECTAS
  // ============================================================

  // Puntos por limpiar una habitación correctamente
  def puntuarLimpieza: Casa =
    aplicarPuntos(Puntuacion.limpiarHabitacion)

  // Puntos por barrer correctamente
  def puntuarBarrido: Casa =
    aplicarPuntos(Puntuacion.barrer)

  // Puntos por organizar objetos correctamente
  def puntuarOrganizacion: Casa =
    aplicarPuntos(Puntuacion.organizarObjeto)

  // Puntos por mover un objeto correctamente
  def puntuarMovimientoCorrecto: Casa =
    aplicarPuntos(Puntuacion.moverObjetoCorrectamente)

  // Puntos por recoger un objeto correctamente
  def puntuarRecogida: Casa =
    aplicarPuntos(Puntuacion.recogerObjeto)

  // Obtener la puntuación actual
  def obtenerPuntuacion: Int =
    puntuacion

  // ============================================================
  // ESTADO DE LA PUNTUACIÓN
  // ============================================================

  def estadoPuntuacion: String =
    if (puntuacion <= 30)
      "Casa desordenada"
    else if (puntuacion <= 60)
      "Casa aceptable"
    else if (puntuacion <= 80)
      "Casa limpia"
    else
      "Casa impecable"

  // ============================================================
  // EVENTOS ESPECIALES
  // ============================================================

  // ------------------------------------------------------------
  // Evento: aumenta la basura
  // ------------------------------------------------------------

  def aumentarBasura(habitacion: Habitacion): Casa =
    agregarObjeto(
      ObjetoCasa(
        Objeto(
          TipoObjeto.Basura,
          "Basura"
        ),
        habitacion
      )
    ).aplicarPuntos(
      Puntuacion.basuraMisteriosa
    )

  // Agregar varias unidades de basura
  def aumentarBasura(
      habitacion: Habitacion,
      cantidad: Int
  ): Casa = {

    val casaConBasura =
      (1 to cantidad).foldLeft(this) { (casa, _) =>
        casa.agregarObjeto(
          ObjetoCasa(
            Objeto(
              TipoObjeto.Basura,
              "Basura"
            ),
            habitacion
          )
        )
      }

    casaConBasura.aplicarPuntos(
      Puntuacion.basuraMisteriosa * cantidad
    )
  }

  // ------------------------------------------------------------
  // Evento: objeto mojado
  // ------------------------------------------------------------

  def mojarObjeto(objeto: Objeto): Casa =
    copy(
      objetos = objetos.map { objetoCasa =>
        if (objetoCasa.objeto == objeto)
          objetoCasa.copy(mojado = true)
        else
          objetoCasa
      }
    ).aplicarPuntos(
      Puntuacion.objetoMojado
    )

  // ------------------------------------------------------------
  // Evento: ropa aparece en otra habitación
  // ------------------------------------------------------------

  def aparecerRopaEn(habitacion: Habitacion): Casa =
    agregarObjeto(
      ObjetoCasa(
        Objeto(
          TipoObjeto.Ropa,
          "Ropa"
        ),
        habitacion
      )
    ).aplicarPuntos(
      Puntuacion.ropaEnLugarIncorrecto
    )

  // ------------------------------------------------------------
  // Evento: escoba desaparece
  // ------------------------------------------------------------

  def desaparecerEscoba: Casa =
    copy(
      objetos = objetos.filterNot(
        _.objeto.nombre.equalsIgnoreCase("Escoba")
      )
    ).aplicarPuntos(
      Puntuacion.escobaDesaparece
    )

  // ------------------------------------------------------------
  // Evento: robot confunde un objeto con basura
  // ------------------------------------------------------------

  def confundirConBasura(objeto: Objeto): Casa =
    copy(
      objetos = objetos.map { objetoCasa =>
        if (objetoCasa.objeto == objeto)
          objetoCasa.copy(
            objeto = Objeto(
              TipoObjeto.Basura,
              s"Basura (${objeto.nombre})"
            )
          )
        else
          objetoCasa
      }
    ).aplicarPuntos(
      Puntuacion.confundirConBasura
    )

  // ============================================================
  // RESUMEN DE LA CASA
  // ============================================================

  def resumen: String =
    Habitacion.todas
      .map { habitacion =>
        val cantidadObjetos =
          objetosEn(habitacion).size

        val nivel =
          limpieza.getOrElse(habitacion, 0)

        s"$habitacion → Limpieza: $nivel%, Objetos: $cantidadObjetos"
      }
      .mkString("\n")
}