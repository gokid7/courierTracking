package com.courier.tracking.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response {
    private String errorCode;
    private String errorDescription;
    private String status;
    private boolean screenShow;
    private String requestPath;
}
