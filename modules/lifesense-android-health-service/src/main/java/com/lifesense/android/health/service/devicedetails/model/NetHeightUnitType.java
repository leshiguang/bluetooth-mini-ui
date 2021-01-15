package com.lifesense.android.health.service.devicedetails.model;

public enum  NetHeightUnitType {
    CM(1),
    IN(2);
    private int command;

    NetHeightUnitType(int command) {
        this.command = command;
    }

    public int getNetUnitTypeCommand() {
        return command;
    }
}
