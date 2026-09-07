package interfaz

import scala.swing._
import scala.swing.event.ButtonClicked 

class Interfaz(robot: Robot, casa: Casa) extends MainFrame {

  title = "🤖 Ayudante de Limpieza"
  preferredSize = new Dimension(700, 500)

  val mensaje = new TextArea {
    rows = 5
    lineWrap = true
    wordWrap = true
    editable = false
  }

  val estadoRobot = new Label()
  val estadoCasa = new Label()

  val limpiar = new Button("🧹 Limpiar")
  val recoger = new Button("👕 Recoger")
  val organizar = new Button("📦 Organizar")
  val cargar = new Button("🔋 Cargar")

  val botones = new BoxPanel(Orientation.Horizontal) {
    contents += limpiar
    contents += recoger
    contents += organizar
    contents += cargar
  }

  contents = new BoxPanel(Orientation.Vertical) {

    contents += new Label("🤖 AYUDANTE DE LIMPIEZA")
    contents += Swing.VStrut(15)

    contents += estadoRobot
    contents += estadoCasa

    contents += Swing.VStrut(20)

    contents += botones

    contents += Swing.VStrut(20)

    contents += new Label("📋 Registro del robot")
    contents += mensaje

    border = Swing.EmptyBorder(20, 20, 20, 20)
  }

  actualizarEstado()

  listenTo(limpiar)
  listenTo(recoger)
  listenTo(organizar)
  listenTo(cargar)

  reactions += {

    case ButtonClicked(`limpiar`) =>
      mensaje.text = robot.ejecutar("limpiar", casa)
      actualizarEstado()

    case ButtonClicked(`recoger`) =>
      mensaje.text = robot.ejecutar("recoger", casa)
      actualizarEstado()

    case ButtonClicked(`organizar`) =>
      mensaje.text = robot.ejecutar("organizar", casa)
      actualizarEstado()

    case ButtonClicked(`cargar`) =>
      robot.cargar()
      mensaje.text = "🔋 Robot cargado al 100%."
      actualizarEstado()
  }

  def actualizarEstado(): Unit = {
    estadoRobot.text =
      s"🤖 Energía: ${robot.energia}% | 📍 Ubicación: ${robot.ubicacion}"

    estadoCasa.text =
      s"🏠 Limpieza de la casa: ${casa.nivelLimpieza}%"
  }
}