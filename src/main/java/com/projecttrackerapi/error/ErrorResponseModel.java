package com.projecttrackerapi.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponseModel {
    private Date date;
    private String restErrorMessage;
    private String detailedErrorMessage;
}
