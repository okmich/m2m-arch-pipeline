
import java.util.{Properties => JProperties}
import scala.io.Source

import model.Reading

import scala.collection.JavaConverters._

object LowFormClassifier{

	val CLASS_NORMAL = "NOR"
	val CLASS_TURBULENCE = "TUB"
	val CLASS_LEAKAGE = "LKG"

    val RANGE_PRESSURE = "sensor.reading.range.prs";
    val RANGE_TEMPERATURE = "sensor.reading.range.tmp";
    val RANGE_FLOW_VELOCITY = "sensor.reading.range.flv";
    val RANGE_VOLUME = "sensor.reading.range.vol";
    val RANGE_EXTERNAL_BODY_FORCE = "sensor.reading.range.xbf";
	
	val fieldBoundaries : Map[String, (Float, Float)] = {
		def getBounds(s: String) : (Float, Float) = {
			val parts = s.split("-")
			(parts(0).toFloat, parts(1).toFloat)
		}

		val props = new JProperties();
		props.load(getClass.getResourceAsStream("/sensor_reading_range.properties"))

		val pMap = props.asScala //contains key=val-val

		pMap.map(m => (m._1 -> getBounds(m._2))).toMap
	}

	def classify(reading: Reading) : Reading = {
		//we should check out for dummy flows (inactive flows)
		if (reading.isInactive){
			reading.fSts = "I"
			reading.cls = CLASS_NORMAL
		} else if (!reading.sourceExist) {
			reading.fSts = "A"

			val pressure = compareBoundary(RANGE_PRESSURE, reading.iPrs)
			val volume = compareBoundary(RANGE_VOLUME, reading.iVol)
			val flowVelocity = compareBoundary(RANGE_FLOW_VELOCITY, reading.iFlv)

			if (pressure == -1 && volume == -1 && flowVelocity == -1) 
				reading.cls = CLASS_LEAKAGE
			else if (pressure == 1)
				reading.cls = CLASS_TURBULENCE
			else 
				reading.cls = CLASS_NORMAL
		} else {
			//we will just focus on pressure, flow velocity and volume
			reading.fSts = "A"

			val pressure = btw(RANGE_PRESSURE, reading.xPrs, reading.iPrs)
			val volume = btw(RANGE_VOLUME, reading.xVol, reading.iVol)
			val flowVelocity = btw(RANGE_FLOW_VELOCITY, reading.xFlv, reading.iFlv)

			if (pressure == -1 && (volume == -1 || flowVelocity == -1)){
				reading.cls = CLASS_LEAKAGE
			} else if (pressure == 1){
				reading.cls= CLASS_TURBULENCE
			} else{
				reading.cls = CLASS_NORMAL
			}
		}
		reading
	}

	/**
	 *
	 *
	 */
	private def btw(key: String, x: Float, i: Float) : Int = {
		val xLevel = compareBoundary(key,x)
		val iLevel = compareBoundary(key,i)
		if (xLevel == iLevel) 0
		else if (xLevel > iLevel) -1
		else 1
	}

	/**
	 *
	 *
	 */
	private def compareBoundary(key: String, value: Float) : Int = {
		val boundaries = fieldBoundaries(key)

		if (value <= boundaries._1) -1
		else if (value > boundaries._2) 1
		else 0
	}
}