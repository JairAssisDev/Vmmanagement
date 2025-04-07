package com.example.vmmanagement.virtualmachine.domain.model;

public enum StatusVm {
    RUNNING("RUNNING"),PAUSED("PAUSED"),STOP("STOP");
    public final String level;
    StatusVm(String level) {
        this.level = level;
    }
}
