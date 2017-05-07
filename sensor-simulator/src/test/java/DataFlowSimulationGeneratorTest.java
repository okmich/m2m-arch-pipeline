
import com.okmich.sensor.simulator.DataFlowSimulationGenerator;
import static com.okmich.sensor.simulator.DataFlowSimulationGenerator.generate;
import static com.okmich.sensor.simulator.FlowStates.*;
import com.okmich.sensor.simulator.OptionRegistry;
import static com.okmich.sensor.simulator.OptionRegistry.*;
import com.okmich.sensor.simulator.model.Reading;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ABME340
 */
public class DataFlowSimulationGeneratorTest {

    private final Map<String, float[]> boundariesMap = new HashMap<>(5);
    private static final Logger LOG = Logger.getLogger("DataFlowSimulationGeneratorTest");

    public DataFlowSimulationGeneratorTest() {
        OptionRegistry.initialize(null);
        DataFlowSimulationGenerator.initialize();

        boundariesMap.put(RANGE_EXTERNAL_BODY_FORCE, DataFlowSimulationGenerator.X_BODY_FORCE_VARIATIONS);
        boundariesMap.put(RANGE_FLOW_VELOCITY, DataFlowSimulationGenerator.FLOW_VELOCITY_VARIATIONS);
        boundariesMap.put(RANGE_PRESSURE, DataFlowSimulationGenerator.PRESSURE_VARIATIONS);
        boundariesMap.put(RANGE_TEMPERATURE, DataFlowSimulationGenerator.TEMP_VARIATIONS);
        boundariesMap.put(RANGE_VOLUME, DataFlowSimulationGenerator.VOL_VARIATIONS);
        
    }

    @Test
    public void testNormalRange() {
        Reading reading = generate(value(INIT_DATA), NORMAL.toString());
        LOG.log(Level.INFO, reading.toString());

        Assert.assertTrue("error with pressure", compareBoundary(RANGE_PRESSURE, reading.getPressure()) == 0);
        Assert.assertTrue("error with temperature", compareBoundary(RANGE_TEMPERATURE, reading.getTemperature()) == 0);
        Assert.assertTrue("error with volume", compareBoundary(RANGE_VOLUME, reading.getVolume()) == 0);
        Assert.assertTrue("error with flow velocity", compareBoundary(RANGE_FLOW_VELOCITY, reading.getFlowVelocity()) == 0);
        Assert.assertTrue("error with body force", compareBoundary(RANGE_EXTERNAL_BODY_FORCE, reading.getExtBodyForce()) == 0);
    }

    @Test
    public void testLeakageRange() {
        Reading reading = generate(value(INIT_DATA), LEAKAGE.toString());
        LOG.log(Level.INFO, reading.toString());

        Assert.assertTrue("error with pressure", compareBoundary(RANGE_PRESSURE, reading.getPressure()) == -1);
        Assert.assertTrue("error with temperature", compareBoundary(RANGE_TEMPERATURE, reading.getTemperature()) == 0);
        Assert.assertTrue("error with volume", compareBoundary(RANGE_VOLUME, reading.getVolume()) < 0);
        Assert.assertTrue("error with flow velocity", compareBoundary(RANGE_FLOW_VELOCITY, reading.getFlowVelocity()) < 0);
        Assert.assertTrue("error with body force", compareBoundary(RANGE_EXTERNAL_BODY_FORCE, reading.getExtBodyForce()) == 0);
    }

    @Test
    public void testTurblenceRange() {
        Reading reading = generate(value(INIT_DATA), TURBULENCE.toString());
        LOG.log(Level.INFO, reading.toString());
                
        Assert.assertTrue("error with pressure", compareBoundary(RANGE_PRESSURE, reading.getPressure()) == 1);
        Assert.assertTrue("error with temperature", compareBoundary(RANGE_TEMPERATURE, reading.getTemperature()) == 0);
        Assert.assertTrue("error with volume", compareBoundary(RANGE_VOLUME, reading.getVolume()) >= 0);
        Assert.assertTrue("error with flow velocity", compareBoundary(RANGE_FLOW_VELOCITY, reading.getFlowVelocity()) >= 0);
        Assert.assertTrue("error with body force", compareBoundary(RANGE_EXTERNAL_BODY_FORCE, reading.getExtBodyForce()) == 1);
    }

    @Test
    public void testDisconnectedRange() {
        Reading reading = generate(value(INIT_DATA), DISCONNECTION.toString());
        LOG.log(Level.INFO, reading.toString());

    }

    @Test
    public void testInactiveRange() {
        Reading reading = generate(value(INIT_DATA), STOPFLOW.toString());
        LOG.log(Level.INFO, reading.toString());

        Assert.assertTrue("error with pressure", compareBoundary(RANGE_PRESSURE, reading.getPressure()) == -1);
        Assert.assertTrue("error with temperature", compareBoundary(RANGE_TEMPERATURE, reading.getTemperature()) == 0);
        Assert.assertTrue("error with volume", reading.getVolume() == 0);
        Assert.assertTrue("error with flow velocity", reading.getFlowVelocity() == 0);
        Assert.assertTrue("error with body force - " + reading.getExtBodyForce(), compareBoundary(RANGE_EXTERNAL_BODY_FORCE, reading.getExtBodyForce()) == 0);
    }

    private int compareBoundary(String key, float value) {
        float[] val = boundariesMap.get(key);
        if (value <= val[0]) {
            return -1;
        } else if (value > val[1]) {
            return 1;
        } else {
            return 0;
        }
    }
}
