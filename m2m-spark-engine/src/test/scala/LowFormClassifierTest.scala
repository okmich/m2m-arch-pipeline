
import collection.mutable.Stack
import org.scalatest._

import model.Reading

class LowFormClassifierTest extends FlatSpec {

  "A 00001;1494153635396;1.0122;13.314285;0.005589;12.0345;0.0054|00002;1494153635396;1.0122;13.314285;0.005589;12.0345;0.0054|30" should "resolve to NOR" in {
  	val reading = Reading("00001;1494153635396;1.0122;13.314285;0.005589;12.0345;0.0054|00002;1494153635396;1.0122;13.314285;0.005589;12.0345;0.0054|30")
  	val classifiedReading = LowFormClassifier.classify(reading)
	  assertResult("NOR")(classifiedReading.cls)  	
  }

  "A 00001;1494153635412;0.83000004;13.5;0.0;0.0;0.0035|00002;1494153635412;0.83000004;13.5;0.0;0.0;0.0035|20" should "resolve to NOR" in {
  	val reading = Reading("00001;1494153635412;0.83000004;13.5;0.0;0.0;0.0035|00002;1494153635412;0.83000004;13.5;0.0;0.0;0.0035|20")
  	val classifiedReading = LowFormClassifier.classify(reading)
	  assertResult("NOR")(classifiedReading.cls)  	
  }

  "A 00001;1494153635396;1.0122;13.314285;0.0055899997;12.0345;0.0054|00002;1494153635412;0.95000005;14.15;0.004125;10.285714;0.004|30" should "resolve to LKG" in {
	  val reading = Reading("00001;1494153635396;1.0122;13.314285;0.0055899997;12.0345;0.0054|00002;1494153635412;0.95000005;14.15;0.004125;10.285714;0.004|30")
  	val classifiedReading = LowFormClassifier.classify(reading)
	  assertResult("LKG")(classifiedReading.cls)  	
  }

  "A 00001;1494153635412;1.115;20.0;0.012375;25.5;0.00675|00002;1494153635412;1.1675;20.0;0.012375;25.5;0.00675|30" should "resolve to TUB" in {
	  val reading = Reading("00001;1494153635412;1.115;20.0;0.012375;25.5;0.00675|00002;1494153635412;1.1675;20.0;0.012375;25.5;0.00675|30")
  	val classifiedReading = LowFormClassifier.classify(reading)
	  assertResult("TUB")(classifiedReading.cls)
  }

  "A 00001;1494153635396;1.0122;13.314285;0.0055899997;12.0345;0.0054|00002;1494153635412;1.1675;20.0;0.012375;25.5;0.00675|10" should "resolve to TUB" in {
    val reading = Reading("00001;1494153635396;1.0122;13.314285;0.0055899997;12.0345;0.0054|00002;1494153635412;1.1675;20.0;0.012375;25.5;0.00675|10")
    val classifiedReading = LowFormClassifier.classify(reading)
    assertResult("TUB")(classifiedReading.cls)
  }

  "A |00001;1494153635396;1.0122;13.314285;0.005589;12.0345;0.0054|10" should "resolve to NOR" in {
    val reading = Reading("|00001;1494153635396;1.0122;13.314285;0.005589;12.0345;0.0054|10")
    val classifiedReading = LowFormClassifier.classify(reading)
    assertResult("NOR")(classifiedReading.cls)
  }

  "A |00002;1494153635412;0.95000005;14.15;0.004125;10.285714;0.004|30" should "resolve to LKG" in {
    val reading = Reading("|00002;1494153635412;0.95000005;14.15;0.004125;10.285714;0.004|30")
    val classifiedReading = LowFormClassifier.classify(reading)
    assertResult("LKG")(classifiedReading.cls)
  }

  "A |00002;1494153635412;1.1675;20.0;0.012375;25.5;0.00675|30" should "resolve to TUB" in {
    val reading = Reading("|00002;1494153635412;1.1675;20.0;0.012375;25.5;0.00675|30")
    val classifiedReading = LowFormClassifier.classify(reading)
    assertResult("TUB")(classifiedReading.cls)
  }
}