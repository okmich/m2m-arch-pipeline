
import java.util.{Properties => JProperties}
import scala.io.Source


import scala.collection.JavaConverters._

object LowFormClassifier{

	val CLASS_NORMAL = "NOR"
	val CLASS_TURBULENCE = "TUB"
	val CLASS_LEAKAGE = "LKG"

    val RANGE_PRESSURE = "sensor.reading.var.prs";
    val RANGE_TEMPERATURE = "sensor.reading.var.tmp";
    val RANGE_FLOW_VELOCITY = "sensor.reading.var.flv";
    val RANGE_VOLUME = "sensor.reading.var.vol";
    val RANGE_EXTERNAL_BODY_FORCE = "sensor.reading.var.xbf";
	
	val fieldBoundaries : Map[String, (Float, Float)] = {
		def getBounds(s: String) : (Float, Float) = {
			val parts = s.split("-")
			(parts(0).toFloat, parts(1).toFloat)
		}

		val props = new JProperties();
		props.load(getClass.getResourceAsStream("/sensor_reading_range.properties"))

		val pMap = props.asScala //contains key=val-val

		pMap.map(m => (m._1 -> getBounds(m._2)))
	}

	def classify(reading: Reading) : Reading = {
		//we should check out for dummy flows (inactive flows)
		if (reading.isInactive){
			reading.fSts = "I"
			reading.cls = CLASS_NORMAL
		}else {
			//we will just focus on pressure, flow velocity and volume

			reading.xPrs
			reading.iPrs

			reading.xVol
			reading.iVol

			reading.xFlv
			reading.iFlv
		}

		reading
	}


	private def compareBoundary(key: String, value: Float) : Int = {
		val boundaries = fieldBoundariesget(key)

		if (value <= boundaries._1) -1
		else if (value > boundaries._2) 1
		else 0
	}
}