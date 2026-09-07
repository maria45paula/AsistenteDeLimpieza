package modelo

class Habitacion(
                  val nombre: String
                ) {

  var limpieza = 50

  private var objetos: List[ObjetoCasa] = List()

  // Objetos iniciales de cada habitación
  nombre match {

    case "Sala" =>
      agregarObjeto("Sofá")
      agregarObjeto("Cojines")
      agregarObjeto("Televisor")
      agregarObjeto("Libros")

    case "Cocina" =>
      agregarObjeto("Platos")
      agregarObjeto("Vasos")
      agregarObjeto("Ollas")
      agregarObjeto("Comida")

    case "Habitación" =>
      agregarObjeto("Ropa")
      agregarObjeto("Zapatos")
      agregarObjeto("Almohadas")
      agregarObjeto("Juguetes")

    case "Baño" =>
      agregarObjeto("Gato")
      agregarObjeto("Arenero")
      agregarObjeto("Toallas")
      agregarObjeto("Papel higiénico")

    case _ =>
  }


  def agregarObjeto(nombre: String): Unit = {

    objetos = objetos :+
      new ObjetoCasa(nombre)

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