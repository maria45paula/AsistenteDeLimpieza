package modelo

object PruebaCasa extends App {

// ============================================================
// OBJETOS DE PRUEBA
// ============================================================

val ropa = Objeto(
TipoObjeto.Ropa,
"Camisa"
)

val libro = Objeto(
TipoObjeto.Libro,
"Libro de Scala"
)

val plato = Objeto(
TipoObjeto.Plato,
"Plato"
)

val escoba = Objeto(
TipoObjeto.Juguete,
"Escoba"
)

// ============================================================
// CREAR CASA
// ============================================================

val casaInicial =
Casa()
.agregarObjeto(
ObjetoCasa(
ropa,
Habitacion.Dormitorio
)
)
.agregarObjeto(
ObjetoCasa(
libro,
Habitacion.Sala
)
)
.agregarObjeto(
ObjetoCasa(
plato,
Habitacion.Cocina
)
)
.agregarObjeto(
ObjetoCasa(
escoba,
Habitacion.Cocina
)
)

println("======================================")
println("         CASA INICIAL")
println("======================================")

println(casaInicial.resumen)
println(s"Puntuacion: ${casaInicial.puntuacion}")

// ============================================================
// ACCIONES CORRECTAS
// ============================================================

val casaConPuntos =
casaInicial
.puntuarLimpieza
.puntuarBarrido
.puntuarOrganizacion
.puntuarMovimientoCorrecto
.puntuarRecogida

println()
println("======================================")
println("       ACCIONES CORRECTAS")
println("======================================")

println("Se realizaron 5 acciones correctas.")
println(s"Puntuacion: ${casaConPuntos.puntuacion}")

// ============================================================
// EVENTO: OBJETO MOJADO
// ============================================================

val casaMojada =
casaConPuntos.mojarObjeto(plato)

println()
println("======================================")
println("       EVENTO: OBJETO MOJADO")
println("======================================")

println(
s"Objetos en Cocina: ${casaMojada.objetosEn(Habitacion.Cocina)}"
)

println(s"Puntuacion: ${casaMojada.puntuacion}")

// ============================================================
// EVENTO: ROPA EN LUGAR INCORRECTO
// ============================================================

val casaConRopa =
casaMojada.aparecerRopaEn(Habitacion.Bano)

println()
println("======================================")
println("     EVENTO: ROPA EN LUGAR INCORRECTO")
println("======================================")

println(
s"Objetos en Bano: ${casaConRopa.objetosEn(Habitacion.Bano)}"
)

println(s"Puntuacion: ${casaConRopa.puntuacion}")

// ============================================================
// EVENTO: BASURA MISTERIOSA
// ============================================================

val casaConBasura =
casaConRopa.aumentarBasura(
Habitacion.Patio,
3
)

println()
println("======================================")
println("       EVENTO: BASURA MISTERIOSA")
println("======================================")

println(
s"Objetos en Patio: ${casaConBasura.objetosEn(Habitacion.Patio)}"
)

println(s"Puntuacion: ${casaConBasura.puntuacion}")

// ============================================================
// EVENTO: ESCOBA DESAPARECE
// ============================================================

val casaSinEscoba =
casaConBasura.desaparecerEscoba

println()
println("======================================")
println("       EVENTO: ESCOBA DESAPARECE")
println("======================================")

println(
s"Objetos en Cocina: ${casaSinEscoba.objetosEn(Habitacion.Cocina)}"
)

println(s"Puntuacion: ${casaSinEscoba.puntuacion}")

// ============================================================
// EVENTO: OBJETO CONFUNDIDO CON BASURA
// ============================================================

val casaConfundida =
casaSinEscoba.confundirConBasura(libro)

println()
println("======================================")
println("     EVENTO: OBJETO CONFUNDIDO")
println("======================================")

println(
s"Objetos en Sala: ${casaConfundida.objetosEn(Habitacion.Sala)}"
)

println(s"Puntuacion: ${casaConfundida.puntuacion}")

// ============================================================
// RESUMEN FINAL
// ============================================================

println()
println("======================================")
println("          RESUMEN FINAL")
println("======================================")

println(casaConfundida.resumen)

println()

println(
s"PUNTUACION FINAL: ${casaConfundida.puntuacion}"
)

println(
s"ESTADO DE LA CASA: ${casaConfundida.estadoPuntuacion}"
)
}
