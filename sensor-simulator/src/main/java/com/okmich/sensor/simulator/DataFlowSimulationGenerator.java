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

    public static float[] PRESSURE_VARIATIONS;
    public static float[] TEMP_VARIATIONS;
    public static float[] VOL_VARIATIONS;
    public static float[] FLOW_VELOCITY_VARIATIONS;
    public static float[] X_BODY_FORCE_VARIATIONS;

    private static final Random RAND = new Random();

    private DataFlowSimulationGenerator() {
    }

    public static void initialize() {
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
            reading.setTimestamp(System.currentTimeMillis());
            return reading;
        }
        //check the mode
        //we will use the mode to control various ways of simulating different actions in the pipeline story
        if (simMode == null || simMode.trim().isEmpty() || simMode.equalsIgnoreCase("normal")) {
            //generate data within the range of acceptable variance for each field
            reading.setFlowVelocity(alter(reading.getFlowVelocity(), 0.01f));
            reading.setPressure(alter(reading.getPressure(), 0.01f));
            reading.setVolume(alter(reading.getVolume(), 0.00001f));
            reading.setExtBodyForce(alter(reading.getExtBodyForce(), 0.0001f));
            reading.setTemperature(alter(reading.getTemperature(), 0.01f));
        } else if (simMode.equalsIgnoreCase("turbulence")) {
            reading.setFlowVelocity(getRandomValueAboveRange(FLOW_VELOCITY_VARIATIONS));
            reading.setPressure(getRandomValueAboveRange(PRESSURE_VARIATIONS));
            reading.setVolume(getRandomValueAboveRange(VOL_VARIATIONS));
            reading.setExtBodyForce(getRandomValueAboveRange(X_BODY_FORCE_VARIATIONS));
        } else if (simMode.equalsIgnoreCase("leakage")) {
            reading.setFlowVelocity(getRandomValueBelowRange(FLOW_VELOCITY_VARIATIONS));
            reading.setPressure(getRandomValueBelowRange(PRESSURE_VARIATIONS));
            reading.setVolume(getRandomValueBelowRange(VOL_VARIATIONS));
            reading.setExtBodyForce(getRandomValueWithinRange(X_BODY_FORCE_VARIATIONS));
        } else { //stop flow
            //no need tocheck
            reading.setFlowVelocity(0f);
            reading.setPressure(0f);
            reading.setVolume(0f);
            reading.setExtBodyForce(getRandomValueWithinRange(X_BODY_FORCE_VARIATIONS));
        }
        reading.setTemperature(getRandomValueWithinRange(TEMP_VARIATIONS));
        reading.setTimestamp(System.currentTimeMillis());

        return reading;
    }

    public static Reading generateNoFlowReading() {
        Reading reading = new Reading();

        reading.setDevId(value(DEVICE_ID));
        reading.setTimestamp(System.currentTimeMillis());
        reading.setExtBodyForce(getRandomValueWithinRange(X_BODY_FORCE_VARIATIONS));
        reading.setFlowVelocity(0f);
        reading.setVolume(0f);
        reading.setPressure(0f);
        reading.setTemperature(getRandomValueWithinRange(TEMP_VARIATIONS));

        return reading;
    }

    private static float[] getRange(String s) {
        String[] vals = s.split("-");
        float min = vals[0].isEmpty() ? 0f : Float.parseFloat(vals[0]);
        //if no max is specified, make it twice the min value
        float max = vals.length <= 1 || vals[1].isEmpty() ? 2 * min : Float.parseFloat(vals[1]);
        return new float[]{min, max};
    }

    /**
     *
     * @param range
     * @return
     */
    private static float getRandomValueWithinRange(float[] range) {

        return range[0] + ((range[1] - range[0]) / randomIntDivisor());
    }

    /**
     *
     * @param range
     * @return
     */
    private static float getRandomValueBelowRange(float[] range) {
        return range[0] - ((range[1] - range[0]) / randomIntDivisor());
    }

    /**
     *
     * @param range
     * @return
     */
    private static float getRandomValueAboveRange(float[] range) {
        return range[1] + ((range[1] - range[0]) / randomIntDivisor());
    }

    private static float alter(float value, float e) {
        if (RAND.nextBoolean()) {
            return value + e;
        } else {
            return value - e;
        }
    }

    private static float randomIntDivisor() {
        int div = RAND.nextInt(10);
        if (div == 0) {
            return 3;
        } else {
            return div;
        }
    }
}
