package logica

import modelo.{Casa, Robot}

import scala.util.Random

object MotorEventos {


  def generarEvento(
                     accion: String,
                     robot: Robot,
                     casa: Casa
                   ): String = {

    val numero = Random.nextInt(100)

    val lugar = robot.ubicacion


    accion match {

      // =====================================
      // LIMPIAR
      // =====================================

      case "limpiar" =>

        lugar match {


          // ---------- SALA ----------

          case "Sala" =>

            if (numero < 50) {

              "🧹 Limpié la sala perfectamente."

            } else if (numero < 70) {

              casa.empeorarLimpieza(5)

              "ERROR: Limpié la sala, pero ensucié el sofá."

            } else if (numero < 90) {

              "ERROR: Limpié la sala, pero moví todos los cojines al suelo."

            } else {

              casa.empeorarLimpieza(15)

              "ERROR: DESASTRE: limpié la sala y tiré los libros por todas partes."

            }


          // ---------- COCINA ----------

          case "Cocina" =>

            if (numero < 50) {

              "🧹 La cocina quedó muy limpia."

            } else if (numero < 70) {

              casa.empeorarLimpieza(5)

              "ERROR: Limpié la cocina, pero tiré algunos platos al suelo."

            } else if (numero < 90) {

              "ERROR: Limpié la cocina y puse las ollas dentro de la nevera."

            } else {

              casa.empeorarLimpieza(15)

              "ERROR: DESASTRE: limpié la cocina y derramé toda la comida."

            }


          // ---------- HABITACIÓN ----------

          case "Habitación" =>

            if (numero < 50) {

              "🧹 La habitación quedó limpia."

            } else if (numero < 70) {

              casa.empeorarLimpieza(5)

              "ERROR: Limpié la habitación, pero tiré la ropa al suelo."

            } else if (numero < 90) {

              "ERROR: Limpié la habitación y escondí los zapatos debajo de la cama."

            } else {

              casa.empeorarLimpieza(15)

              "ERROR: DESASTRE: intenté limpiar y terminé desordenando toda la habitación."

            }


          // ---------- BAÑO ----------

          case "Baño" =>

            if (numero < 50) {

              "🧹 El baño quedó perfectamente limpio."

            } else if (numero < 70) {

              casa.empeorarLimpieza(5)

              "ERROR: Limpié el baño, pero mojé todas las toallas."

            } else if (numero < 90) {

              "ERROR: Intenté limpiar el baño y terminé botando al gato junto con el arenero."

            } else {

              casa.empeorarLimpieza(15)

              "ERROR: DESASTRE: limpié el baño y rompí el papel higiénico por toda la habitación."

            }


          case _ =>

            "No conozco esta habitación."

        }


      // =====================================
      // RECOGER
      // =====================================

      case "recoger" =>

        lugar match {


          // ---------- SALA ----------

          case "Sala" =>

            if (numero < 50) {

              "👕 Recogí todo lo que estaba fuera de lugar en la sala."

            } else if (numero < 70) {

              "ERROR: Recogí los cojines y los escondí debajo del sofá."

            } else if (numero < 90) {

              "ERROR: Recogí los libros y los puse encima del televisor."

            } else {

              "ERROR: Recogí todo... pero ahora nadie encuentra nada."

            }


          // ---------- COCINA ----------

          case "Cocina" =>

            if (numero < 50) {

              "👕 Recogí todos los objetos de la cocina."

            } else if (numero < 70) {

              "ERROR: Recogí los platos y los guardé en la sala."

            } else if (numero < 90) {

              "ERROR: Recogí los vasos y los puse dentro de las ollas."

            } else {

              "ERROR: Recogí la comida y la escondí detrás de la nevera."

            }


          // ---------- HABITACIÓN ----------

          case "Habitación" =>

            if (numero < 50) {

              "👕 Recogí toda la ropa y los zapatos."

            } else if (numero < 70) {

              "ERROR: Recogí la ropa y la guardé en la nevera."

            } else if (numero < 90) {

              "ERROR: Recogí los zapatos y los puse sobre la cama."

            } else {

              "ERROR: Recogí todo... excepto lo que realmente debía recoger."

            }


          // ---------- BAÑO ----------

          case "Baño" =>

            if (numero < 50) {

              "👕 Recogí las cosas del baño correctamente."

            } else if (numero < 70) {

              "ERROR: Recogí las toallas y las puse en la cocina."

            } else if (numero < 90) {

              "ERROR: Recogí el papel higiénico y lo escondí detrás del espejo."

            } else {

              "ERROR: Recogí el arenero... pero dejé al gato encima del lavamanos."

            }


          case _ =>

            "No conozco esta habitación."

        }


      // =====================================
      // ORGANIZAR
      // =====================================

      case "organizar" =>

        lugar match {


          // ---------- SALA ----------

          case "Sala" =>

            if (numero < 50) {

              "📦 La sala quedó perfectamente organizada."

            } else if (numero < 70) {

              "ERROR: Organicé los cojines en el suelo."

            } else if (numero < 90) {

              "ERROR: Organicé los libros dentro de los cajones del televisor."

            } else {

              "ERROR: Organicé la sala... ahora parece más desordenada."

            }


          // ---------- COCINA ----------

          case "Cocina" =>

            if (numero < 50) {

              "📦 La cocina quedó perfectamente organizada."

            } else if (numero < 70) {

              "ERROR: Organicé los platos por tamaño... pero quedaron en el suelo."

            } else if (numero < 90) {

              "ERROR: Organicé las ollas dentro de la nevera."

            } else {

              "ERROR: Organicé la comida según colores. Nadie sabe por qué."

            }


          // ---------- HABITACIÓN ----------

          case "Habitación" =>

            if (numero < 50) {

              "📦 La habitación quedó perfectamente organizada."

            } else if (numero < 70) {

              "ERROR: Organicé la ropa debajo de la cama."

            } else if (numero < 90) {

              "ERROR: Organicé los zapatos encima de la almohada."

            } else {

              "ERROR: Organicé la habitación, pero cambié todo de lugar."

            }


          // ---------- BAÑO ----------

          case "Baño" =>

            if (numero < 50) {

              "📦 El baño quedó perfectamente organizado."

            } else if (numero < 70) {

              "ERROR: Organicé las toallas dentro de la ducha."

            } else if (numero < 90) {

              "ERROR: Organicé el papel higiénico alrededor del inodoro."

            } else {

              "ERROR: Organicé el baño, pero ahora el gato está en la ducha."

            }


          case _ =>

            "No conozco esta habitación."

        }


      case _ =>

        "No ocurrió nada."

    }

  }

}