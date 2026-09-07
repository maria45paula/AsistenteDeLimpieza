package interfaz

import scala.swing._
import scala.swing.event.ButtonClicked
import modelo.Casa
import modelo.Robot


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

  val limpiar = new Button("🧹 Limpiar") {
    background = new Color (0, 240, 255)
  }
  
  val recoger = new Button("👕 Recoger") {
    background = new Color (161, 0, 255)
  }
  
  val organizar = new Button("📦 Organizar") {
    background = new Color (255, 0, 85)
  }
  
  val cargar = new Button("🔋 Cargar") {
    background = new Color(57, 255, 20)
  }


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
    contents += new Label("📋 Última respuesta")
    contents += mensaje
    border = Swing.EmptyBorder(20, 20, 20, 20)
  }

  actualizarEstado()

  listenTo(limpiar, recoger, organizar, cargar)

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

