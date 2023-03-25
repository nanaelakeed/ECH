package com.bfcai.ECH.type;

public enum GenderType {
    MALE(1),
    FEMALE(2);

    private int code;

    GenderType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
