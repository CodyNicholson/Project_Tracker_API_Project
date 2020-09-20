package com.projecttrackerapi.models;

import lombok.Data;

import java.util.Date;

@Data
public class GenericResponseModel {
    private Date timestamp;

    private int statusCode;
    private Object data;
    public GenericResponseModel(int statusCode, Object data) {
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.data = data;
    }
}