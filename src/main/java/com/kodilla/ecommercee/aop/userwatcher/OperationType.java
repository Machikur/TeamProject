package com.kodilla.ecommercee.aop.userwatcher;


public enum OperationType {
    UPDATE("Update"), DELETE("Delete"), CREATE("Create");

    private String desc;

    OperationType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
