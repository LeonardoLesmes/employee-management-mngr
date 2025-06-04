package com.employee_management_mngr.computer.domain;

public enum ComputerStatus {
    AVAILABLE("AVAILABLE"), ASSIGNED("ASSIGNED"), IN_PROCESS("IN_PROCESS");

    private final String value;

    ComputerStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
