package com.projecttrackerapi.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class GenericResponseDto {
    private Date timestamp;
    private int statusCode;
    private Object data;
    public GenericResponseDto(int statusCode, Object data) {
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.data = data;
    }
}