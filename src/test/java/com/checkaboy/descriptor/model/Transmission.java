package com.checkaboy.descriptor.model;

/**
 * @author Taras Shaptala
 */
public class Transmission {

    private int countSteps;
    private ETransmissionType transmissionType;

    public int getCountSteps() {
        return countSteps;
    }

    public void setCountSteps(int countSteps) {
        this.countSteps = countSteps;
    }

    public ETransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(ETransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

}
