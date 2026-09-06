package modelo

sealed trait Habitacion

object Habitacion {
  case object Dormitorio extends Habitacion
  case object Cocina extends Habitacion
  case object Sala extends Habitacion
  case object Bano extends Habitacion
  case object Patio extends Habitacion

  val todas: List[Habitacion] =
    List(Dormitorio, Cocina, Sala, Bano, Patio)
}