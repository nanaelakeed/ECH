package com.bfcai.ECH.type;

public enum StatusCode {
    SUCCESS(200L),

    INTERNAL_SERVER_ERROR(400L),

    ERROR(4000L),

    NOT_FOUND(404L),

    UN_AUTHORIZED(6000L),

    BAD_CREDENTAILS(7000L),

    BAD_REQUEST(8000L),

    ALREADY_EXISTS(9000L),

    SECURITY_ISSUE(1000L),

    CONFLICT(4090L);

    public final Long serverCode;

    StatusCode(Long serverCode) {this.serverCode = serverCode;}
}
