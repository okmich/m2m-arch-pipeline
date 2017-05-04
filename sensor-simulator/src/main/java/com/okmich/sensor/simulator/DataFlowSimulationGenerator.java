/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator;

import com.okmich.sensor.simulator.model.Reading;
import static com.okmich.sensor.simulator.OptionRegistry.*;
import java.util.Random;

/**
 *
 * @author m.enudi
 */
public final class DataFlowSimulationGenerator {

    private static float[] PRESSURE_VARIATIONS;
    private static float[] TEMP_VARIATIONS;
    private static float[] VOL_VARIATIONS;
    private static float[] FLOW_VELOCITY_VARIATIONS;
    private static float[] X_BODY_FORCE_VARIATIONS;

    private static final Random RAND = new Random();

    private DataFlowSimulationGenerator() {
    }

    public static void initilize() {
        VOL_VARIATIONS = getRange(value(RANGE_VOLUME));
        FLOW_VELOCITY_VARIATIONS = getRange(value(RANGE_FLOW_VELOCITY));
        PRESSURE_VARIATIONS = getRange(value(RANGE_PRESSURE));
        X_BODY_FORCE_VARIATIONS = getRange(value(RANGE_EXTERNAL_BODY_FORCE));
        TEMP_VARIATIONS = getRange(value(RANGE_TEMPERATURE));
    }

    /**
     *
     * @param dataFlow
     * @param simMode
     * @return
     */
    public static Reading generate(String dataFlow, String simMode) {
        Reading reading = Reading.newFromString(dataFlow, value(DEVICE_ID));
        if (reading.getFlowVelocity() == 0.0f || reading.getVolume() == 0.0f) {
            return reading;
        }
        //check the mode
        //we will use the mode to control various ways of simulating different actions in the pipeline story
        if (simMode == null || simMode.trim().isEmpty() || simMode.equalsIgnoreCase("steady")) {
            //generate data within the range of acceptable variance for each field
            reading.setFlowVelocity(getRandomValueWithinRange(FLOW_VELOCITY_VARIATIONS));
            reading.setPressure(getRandomValueWithinRange(PRESSURE_VARIATIONS));
            reading.setVolume(getRandomValueWithinRange(VOL_VARIATIONS));
        } else if (simMode.equalsIgnoreCase("turbulence")) {
            reading.setFlowVelocity(getRandomValueAboveRange(FLOW_VELOCITY_VARIATIONS));
            reading.setPressure(getRandomValueAboveRange(PRESSURE_VARIATIONS));
            reading.setVolume(getRandomValueAboveRange(VOL_VARIATIONS));
        } else if (simMode.equalsIgnoreCase("leakage")) {
            reading.setFlowVelocity(getRandomValueBelowRange(FLOW_VELOCITY_VARIATIONS));
            reading.setPressure(getRandomValueBelowRange(PRESSURE_VARIATIONS));
            reading.setVolume(getRandomValueBelowRange(VOL_VARIATIONS));
        } else { //disconnection
            //no need to check
            reading.setFlowVelocity(0f);
            reading.setPressure(getRandomValueBelowRange(PRESSURE_VARIATIONS));
            reading.setVolume(0f);
        }
        reading.setTemperature(getRandomValueWithinRange(TEMP_VARIATIONS));
        reading.setExtBodyForce(getRandomValueWithinRange(X_BODY_FORCE_VARIATIONS));

        return reading;
    }

    private static float[] getRange(String s) {
        String[] vals = s.split("-");
        float min = vals[0].isEmpty() ? 0f : Float.parseFloat(vals[0]);
        //if no max is specified, make it twice the min value
        float max = vals[1].isEmpty() ? 2 * min : Float.parseFloat(vals[1]);
        return new float[]{min, max};
    }

    /**
     *
     * @param range
     * @return
     */
    private static float getRandomValueWithinRange(float[] range) {
        return range[0] + ((range[1] - range[0]) / RAND.nextInt(10));
    }

    /**
     *
     * @param range
     * @return
     */
    private static float getRandomValueBelowRange(float[] range) {
        return range[0] - ((range[1] - range[0]) / RAND.nextInt(10));
    }

    /**
     *
     * @param range
     * @return
     */
    private static float getRandomValueAboveRange(float[] range) {
        return range[1] + ((range[1] - range[0]) / RAND.nextInt(10));
    }
}
