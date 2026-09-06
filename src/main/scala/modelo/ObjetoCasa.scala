package modelo

case class ObjetoCasa(
    objeto: Objeto,
    habitacion: Habitacion,
    mojado: Boolean = false
)