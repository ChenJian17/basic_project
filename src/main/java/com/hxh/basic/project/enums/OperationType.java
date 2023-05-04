package com.hxh.basic.project.enums;

/**
 * @Author: ChenJian
 * @Date: 2021/5/24 15:53
 */
public enum OperationType {
    /**
     * 操作类型
     */
    UNKNOWN("unknown"),
    DELETE("delete"),
    SELECT("select"),
    UPDATE("update"),
    INSERT("insert"),
    SINGLESEND("signlesend"),
    NULTISEND("multisend"),
    SENDPULL("sendpull"),
    SENDPULLWITH("sendpullwithphone");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    OperationType(String s) {
        this.value = s;
    }
}
