package model

class Reading (val xDevId : String, val xts : Long, val xPrs : Float, val xTmp : Float, val xVol : Float,
				val xFlv : Float, val xXbr : Float,
			 val iDevId : String, val its : Long, val iPrs : Float, val iTmp : Float, val iVol : Float,
			 val iFlv : Float, val iXbr : Float, val dist : Float, var cls : String = "", var incd : Float = 0f
		 ) extends java.io.Serializable {

	//rowkey
	//xDevId
	//xts
	//xPrs
	//xTmp
	//xVol
	//xFlv
	//xXbr
	//iDevId
	//its
	//iPrs
	//iTmp
	//iVol
	//iFlv
	//iXbr
	//dist
	//cls
	def toTuple  = {
		(xDevId + ":" + (-1 * xts).toString, xDevId, xts, xPrs, xTmp, xVol, xFlv, xXbr, iDevId, its, iPrs, iTmp, iVol, iFlv, iXbr, dist, cls, incd)
	}

	/**
	 *
	 * @return String
	 */
	override def toString : String = "xDvId:" + xDevId + ";xts:" +
								xts + ";xPrs:" +
								xPrs + ";xTmp:" +
								xTmp + ";xVol:" +
								xVol + ";xFlv:" +
								xFlv + ";xXbr:" +
								xXbr + ";iDvId:" +
								iDevId + ";its:" +
								its + ";iPrs:" +
								iPrs + ";iTmp:" +
								iTmp + ";iVol:" +
								iVol + ";iFlv:" +
								iFlv + ";iXbr:" +
								iXbr + ";dist:" +
								dist + ";cls:" +
								cls + ";incd:" +
								incd
}

/**
 * Companion Reading object
 *
 */
object Reading {
	def apply(payload : String) : Reading = {
		val v = parseWholeReading(payload)

		new Reading(v._1._1, v._1._2, v._1._3, v._1._4, v._1._5, v._1._6, v._1._7,
		 						v._2._1, v._2._2, v._2._3, v._2._4, v._2._5, v._2._6, v._2._7,
							v._3)
	}

	private def parseWholeReading(s : String) = {
		val parts = s.split("\\|")
		(parseReading(parts(0)), parseReading(parts(1)), parts(2).toFloat)
	}

  // return "devId:" + this.devId + ";ts:" + timestamp
  //         + ";prs:" + pressure + ";tmp:" + temperature + ";vol:" + volume
  //         + ";flv:" + flowVelocity + ";xbf:" + extBodyForce;
	private def parseReading(s: String) = {
		val parts = s.split(";")
		(parts(0), 						//devId
			parts(1).toLong, 		//ts
			parts(2).toFloat, 	//prs
			parts(3).toFloat, 	//tmp
			parts(4).toFloat, 	//vol
			parts(5).toFloat, 	//flv
			parts(6).toFloat)		//xbf
	}
}
