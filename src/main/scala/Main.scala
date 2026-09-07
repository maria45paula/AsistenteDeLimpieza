
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import scala.swing.Swing
import modelo.Casa
import modelo.Robot
import interfaz.Interfaz
import logica.MemoriaRobot
import logica.GestorLimpieza


object Main extends App {

  val casa = new Casa()

  val memoria = new MemoriaRobot()

  val robot = new Robot("Robi", memoria)

  val gestor = new GestorLimpieza(casa)

  Swing.onEDT {
    val ventana = new Interfaz(robot, casa)
    ventana.visible = true
  }
}
