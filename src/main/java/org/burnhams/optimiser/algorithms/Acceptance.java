package org.burnhams.optimiser.algorithms;

import org.apache.log4j.Logger;

public class Acceptance {

    private static Logger logger = Logger.getLogger(Acceptance.class);


    public boolean isAccepted(double startingTemperature, double temperature, double currentCost, double maxCost, double neighbourCost) {
        double d = ((currentCost - neighbourCost) / maxCost) * startingTemperature;
        double acceptance = Math.exp(d / temperature);
        double p = Math.random();
        //logger.debug("Start temp: "+startingTemperature+", temp: "+temperature+", currentCost: "+currentCost+", maxCost: "+maxCost+", neighbourCost: "+neighbourCost+", d: "+d+", acceptance: "+acceptance+", p: "+p);
        return p < acceptance;
    }

}
