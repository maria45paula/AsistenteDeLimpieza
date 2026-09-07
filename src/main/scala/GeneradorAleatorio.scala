import scala.util.Random

object GeneradorAleatorio {

  def numero(maximo: Int): Int = {
    Random.nextInt(maximo)
  }

  def porcentaje(probabilidad: Int): Boolean = {
    Random.nextInt(100) < probabilidad
  }
}