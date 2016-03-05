package org.burnhams.optimiser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfiguration implements Configuration {

    private final Properties properties;

    public PropertiesConfiguration() throws IOException {
        properties = new Properties();
        InputStream resource = getClass().getResourceAsStream("/configuration.properties");
        try {
            properties.load(resource);
        } finally {
            resource.close();
        }
    }

    public int getThreads() {
        return getInteger("threads");
    }

    private int getInteger(String key) {
        return Integer.valueOf(properties.getProperty(key));
    }

    private int[] getIntegers(String key) {
        String[] strings = properties.getProperty(key).split(",");
        int[] result = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Integer.valueOf(strings[i].trim());
        }
        return result;
    }

    private Double getDouble(String key) {
        return Double.valueOf(properties.getProperty(key));
    }

    @Override
    public double getStartTemperature() {
        return getDouble("start.temperature");
    }

    @Override
    public double getEndTemperature() {
        return getDouble("end.temperature");
    }

    @Override
    public long getMaxIterations() {
        return Long.valueOf(properties.getProperty("max.iterations"));
    }

    @Override
    public boolean isAllowExtraTimeForImprovement() {
        return false;
    }

    public int getHillClimbChoices() {
        return getInteger("hillclimb.choices");
    }

    @Override
    public int getMaxNonImprovingMoves() {
        return getInteger("maxNonImprovingMoves");
    }

}
