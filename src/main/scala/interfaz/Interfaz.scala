package interfaz

import scala.swing.*
import scala.swing.event.ButtonClicked
import modelo.Casa
import modelo.Robot
import java.awt.Image
import javax.swing.ImageIcon
import java.awt.Color
import java.awt.Font

class Interfaz(robot: Robot, casa: Casa) extends MainFrame {

  // 1. Textos
  val titulo = new Label("🤖 AYUDANTE DE LIMPIEZA") {
    foreground = Color.white
    font = new Font("Verdana", Font.BOLD, 20)
  }
  titulo.xLayoutAlignment = 0.5

  val estadoRobot = new Label() {
    foreground = Color.white
    font = new Font("Verdana", Font.PLAIN, 14)
  }
  estadoRobot.xLayoutAlignment = 0.5

  val estadoCasa = new Label() {
    foreground = Color.white
    font = new Font("Verdana", Font.PLAIN, 14)
  }
  estadoCasa.xLayoutAlignment = 0.5

  val etiquetaRespuesta = new Label(" Tu asistente de limpieza fav :") {
    foreground = Color.white
    font = new Font("Verdana", Font.BOLD, 14)
  }
  etiquetaRespuesta.xLayoutAlignment = 0.5

  val mensaje = new Label {
    foreground = Color.white
    horizontalAlignment = Alignment.Center
    font = new Font("Verdana", Font.PLAIN, 16)
  }
  mensaje.xLayoutAlignment = 0.5

  // 2. Imagen
  val ruta = getClass.getResource("/imagenes/robot.jpg")
  val iconoOriginal = new ImageIcon(ruta)
  val imagenEscalada = iconoOriginal.getImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH)

  val imagenRobot = new Label {
    icon = new ImageIcon(imagenEscalada)
  }
  imagenRobot.xLayoutAlignment = 0.5

  // 3. Botones (esto es lo que se había perdido)
  val fuenteBotones = new Font("Verdana", Font.BOLD, 14)
  val colorBotones = new Color(100, 149, 237)

  val limpiar = new Button("🧹 Limpiar") {
    font = fuenteBotones
    background = colorBotones
    foreground = Color.white
  }
  val recoger = new Button("👕 Recoger") {
    font = fuenteBotones
    background = colorBotones
    foreground = Color.white
  }
  val organizar = new Button("📦 Organizar") {
    font = fuenteBotones
    background = colorBotones
    foreground = Color.white
  }
  val cargar = new Button("🔋 Cargar") {
    font = fuenteBotones
    background = colorBotones
    foreground = Color.white
  }

  val botones = new FlowPanel(FlowPanel.Alignment.Center)(limpiar, recoger, organizar, cargar)
  botones.background = Color.black
  botones.xLayoutAlignment = 0.5


  contents = new BoxPanel(Orientation.Vertical) {
    background = Color.black
    contents += titulo
    contents += Swing.VStrut(15)
    contents += imagenRobot
    contents += Swing.VStrut(15)
    contents += estadoRobot
    contents += estadoCasa
    contents += Swing.VStrut(20)
    contents += botones
    contents += Swing.VStrut(20)
    contents += etiquetaRespuesta
    contents += mensaje
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