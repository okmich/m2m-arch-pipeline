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
public final class DataGenerator {

    private static DataGenerator SELF;
    private static float PRESSURE_VARIATIONS;
    private static float TEMP_VARIATIONS;
    private static float VOL_VARIATIONS;
    private static float FLOW_VELOCITY_VARIATIONS;
    private static float X_BODY_FORCE_VARIATIONS;

    private static final Random RAND = new Random();

    private DataGenerator() {
    }

    public static void initilize() {
        VOL_VARIATIONS = valueAsFloat(VAR_VOLUME);
        FLOW_VELOCITY_VARIATIONS = valueAsFloat(VAR_FLOW_VELOCITY);
        PRESSURE_VARIATIONS = valueAsFloat(VAR_PRESSURE);
        X_BODY_FORCE_VARIATIONS = valueAsFloat(VAR_EXTERNAL_BODY_FORCE);
        TEMP_VARIATIONS = valueAsFloat(VAR_TEMPERATURE);
    }

    public static Reading generate(String dataFlow, String simMode) {
        Reading reading = Reading.newFromString(dataFlow, value(DEVICE_ID));
        float stddev = 0;
        //check the mode
        //we will use the mode to control various ways of simulating different actions in the pipeline story
        if (simMode == null || simMode.trim().isEmpty() || simMode.equalsIgnoreCase("normal")) {
            //generate data within the range of acceptable variance for each field
            reading.setExtBodyForce(getNextRandomReading(reading.getExtBodyForce(), X_BODY_FORCE_VARIATIONS, stddev));
            reading.setFlowVelocity(getNextRandomReading(reading.getFlowVelocity(), FLOW_VELOCITY_VARIATIONS, stddev));
            reading.setPressure(getNextRandomReading(reading.getPressure(), PRESSURE_VARIATIONS, stddev));
            reading.setTemperature(getNextRandomReading(reading.getTemperature(), TEMP_VARIATIONS, stddev));
            reading.setVolume(getNextRandomReading(reading.getVolume(), VOL_VARIATIONS, stddev));
        } else if (simMode.equalsIgnoreCase("turbulence")) {
            stddev = 1;
        } else if (simMode.equalsIgnoreCase("leakage")) {
            stddev = 1;

        } else if (simMode.equalsIgnoreCase("disconnection")) {
            //no need to check
            return null;
        } else { //steady
            stddev = 1;

        }

        return reading;
    }

    /**
     *
     *
     * @param reading
     * @param variance
     * @param stddev
     * @return
     */
    private static float getNextRandomReading(float reading, float variance, float stddev) {
        float deincr = (variance * RAND.nextFloat()) + (variance * stddev);
        if (RAND.nextBoolean()) {
            return reading + deincr;
        } else {
            return reading - deincr;
        }
    }
}
