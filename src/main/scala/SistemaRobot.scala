import scala.collection.mutable

// MODELOS BÁSICOS

/**Habitaciones de la casa. */
sealed trait Habitacion
object Habitacion {
  case object Dormitorio extends Habitacion // 
  case object Cocina     extends Habitacion // 
  case object Sala       extends Habitacion // 
  case object Bano       extends Habitacion // 
  case object Patio      extends Habitacion // 

  val todas: List[Habitacion] = List(Dormitorio, Cocina, Sala, Bano, Patio)
}

/** Tipos de objetos que el robot puede manipular. */
sealed trait TipoObjeto
object TipoObjeto {
  case object Ropa    extends TipoObjeto // 
  case object Libro   extends TipoObjeto // 
  case object Zapato  extends TipoObjeto // 
  case object Plato   extends TipoObjeto // 
  case object Basura  extends TipoObjeto // 
  case object Juguete extends TipoObjeto // 
  case object Comida  extends TipoObjeto //
}

final case class Objeto(tipo: TipoObjeto, nombre: String)

/** Posibles estados internos del robot. */
sealed trait EstadoRobot
object EstadoRobot {
  case object Inactivo   extends EstadoRobot
  case object Limpiando  extends EstadoRobot
  case object Moviendose extends EstadoRobot
  case object Atascado   extends EstadoRobot
  case object SinBateria extends EstadoRobot
}

/** Órdenes que el usuario le puede dar al robot. */
sealed trait Accion
object Accion {
  final case class Limpiar(lugar: Habitacion)                     extends Accion
  final case class Barrer(lugar: Habitacion)                      extends Accion
  final case class RecogerObjeto(objeto: Objeto)                  extends Accion
  final case class Organizar(objetos: List[Objeto], lugar: Habitacion) extends Accion
  case object SacarBasura                                         extends Accion
  final case class GuardarObjeto(objeto: Objeto, lugar: Habitacion) extends Accion
  final case class MoverObjeto(objeto: Objeto, destino: Habitacion) extends Accion
  final case class Moverse(destino: Habitacion)                   extends Accion
}

/** Resultado de ejecutar una acción: */
final case class ResultadoAccion(
    mensaje: String,
    exito: Boolean,
    energia: Int,
    estado: EstadoRobot,
    ubicacion: Habitacion,
    nivelLimpieza: Map[Habitacion, Int]
)

/**
 * Punto de conexión: Recibe la acción original y el
 * resultado "correcto" que calculó el robot, y puede devolver otro
 * resultado distinto (por ejemplo, el robot metiendo la ropa en la nevera).
 * Si no está conectado todavía, se usa `Modificador.ninguno`.
 */
trait Modificador {
  def aplicar(accion: Accion, resultadoBase: ResultadoAccion): ResultadoAccion
}
object Modificador {
  val ninguno: Modificador = (_: Accion, resultado: ResultadoAccion) => resultado
}

//
// EL ROBOT
// 

class RobotAspiradora(
    val nombre: String = "Ayudante",
    private var modificador: Modificador = Modificador.ninguno
) {
  import Accion._
  import EstadoRobot._

  private var _energia: Int = 100
  private var _estado: EstadoRobot = Inactivo
  private var _ubicacion: Habitacion = Habitacion.Sala
  private val _nivelLimpieza: mutable.Map[Habitacion, Int] =
    mutable.Map(Habitacion.todas.map(_ -> 40)*)
  private var _inventario: List[Objeto] = List.empty

  // ---- Getters de solo lectura para la GUI ----
  def energia: Int = _energia
  def estado: EstadoRobot = _estado
  def ubicacion: Habitacion = _ubicacion
  def nivelLimpieza: Map[Habitacion, Int] = _nivelLimpieza.toMap
  def inventario: List[Objeto] = _inventario

  /** Permite que Integrante 3 conecte (o desconecte) su sistema de eventos. */
  def conectarModificador(m: Modificador): Unit = modificador = m

  // ---- Punto de entrada único usado por la GUI ----
  def ejecutar(accion: Accion): ResultadoAccion = {
    val base = calcularResultadoBase(accion)
    modificador.aplicar(accion, base)
  }

  // ---- Lógica "normal" de cada acción (sin aleatoriedad) ----
  private def calcularResultadoBase(accion: Accion): ResultadoAccion = {
    if (_estado == SinBateria && accion != Moverse(_ubicacion)) {
      return snapshot(s"$nombre no responde: ¡se quedó sin batería! 🔋", exito = false)
    }
    accion match {
      case Limpiar(lugar) =>
        irA(lugar, costoMovimiento = 3)
        consumirEnergia(10)
        _nivelLimpieza(lugar) = 100
        _estado = Limpiando
        snapshot(s"$nombre limpió $lugar a fondo. ¡Quedó impecable! 🧼")

      case Barrer(lugar) =>
        irA(lugar, costoMovimiento = 3)
        consumirEnergia(5)
        _nivelLimpieza(lugar) = math.min(100, _nivelLimpieza(lugar) + 30)
        _estado = Limpiando
        snapshot(s"$nombre barrió $lugar. 🧹")

      case RecogerObjeto(objeto) =>
        consumirEnergia(5)
        _inventario = objeto :: _inventario
        snapshot(s"$nombre recogió ${objeto.nombre}. ✋")

      case Organizar(objetos, lugar) =>
        irA(lugar, costoMovimiento = 3)
        consumirEnergia(8)
        _inventario = _inventario.filterNot(objetos.contains)
        _nivelLimpieza(lugar) = math.min(100, _nivelLimpieza(lugar) + 15)
        snapshot(s"$nombre organizó ${objetos.map(_.nombre).mkString(", ")} en $lugar. 📚")

      case SacarBasura =>
        consumirEnergia(15)
        val (basura, resto) = _inventario.partition(_.tipo == TipoObjeto.Basura)
        _inventario = resto
        snapshot(s"$nombre sacó ${basura.size} bolsa(s) de basura de la casa. 🗑️")

      case GuardarObjeto(objeto, lugar) =>
        irA(lugar, costoMovimiento = 3)
        consumirEnergia(5)
        _inventario = _inventario.filterNot(_ == objeto)
        snapshot(s"$nombre guardó ${objeto.nombre} en $lugar. 📦")

      case MoverObjeto(objeto, destino) =>
        consumirEnergia(6)
        irA(destino, costoMovimiento = 3)
        snapshot(s"$nombre movió ${objeto.nombre} hasta $destino. 🚚")

      case Moverse(destino) =>
        irA(destino, costoMovimiento = 5)
        snapshot(s"$nombre se desplazó a $destino. ➡️")
    }
  }

  private def irA(destino: Habitacion, costoMovimiento: Int): Unit = {
    if (_ubicacion != destino) {
      consumirEnergia(costoMovimiento)
      _estado = Moviendose
      _ubicacion = destino
    }
  }

  private def consumirEnergia(cantidad: Int): Unit = {
    _energia = math.max(0, _energia - cantidad)
    if (_energia == 0) _estado = SinBateria
  }

  private def snapshot(mensaje: String, exito: Boolean = true): ResultadoAccion =
    ResultadoAccion(mensaje, exito, _energia, _estado, _ubicacion, nivelLimpieza)

  /** Recarga el robot (por ejemplo, cuando el usuario lo pone en su base). */
  def recargar(): ResultadoAccion = {
    _energia = 100
    _estado = Inactivo
    snapshot(s"$nombre está recargado y listo para (des)ordenar todo. 🔌")
  }

  def estadoResumen: String =
    f"$nombre%s | 🔋${_energia}%% | Estado: ${_estado} | 📍${_ubicacion} | " +
      f"Limpieza: ${nivelLimpieza.map { case (h, v) => s"$h=$v%" }.mkString(", ")}"
}

// 
// DEMO — ejemplo de uso para probar el módulo de forma independiente
// (Se reemplazará esto por botones reales de la GUI)
// 
object DemoSistemaRobot extends App {
  import Accion._
  import Habitacion._
  import TipoObjeto._

  val robot = new RobotAspiradora("R2-Limpio")

  println("=== Comportamiento base (sin trucos de Integrante 3) ===")
  println(robot.ejecutar(Limpiar(Sala)).mensaje)
  println(robot.ejecutar(RecogerObjeto(Objeto(Ropa, "camiseta azul"))).mensaje)
  println(robot.ejecutar(GuardarObjeto(Objeto(Ropa, "camiseta azul"), Dormitorio)).mensaje)
  println(robot.estadoResumen)

  println("\n=== Ejemplo de integración con Integrante 3 (aleatoriedad) ===")
  // Este modificador es solo un EJEMPLO de cómo se conectaría su
  // sistema de eventos aleatorios; la lógica real de azar vive en su módulo.
  val modificadorCaotico: Modificador = (accion: Accion, resultado: ResultadoAccion) =>
    accion match {
      case Limpiar(Sala) =>
        resultado.copy(
          mensaje = "🤖 Iba a limpiar la sala... pero terminó ensuciando la cocina, " +
            "movió los muebles, encontró una moneda, se llevó un zapato, limpió el mismo " +
            "punto 17 veces y declaró la casa '100% limpia'. 🧠❌"
        )
      case _ => resultado
    }

  robot.conectarModificador(modificadorCaotico)
  println(robot.ejecutar(Limpiar(Sala)).mensaje)
}
