package com.courier.tracking.util;

import com.courier.tracking.constant.Constants;
import com.courier.tracking.model.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtil {
    public static Response createSuccessResponse(){
        return Response.builder()
                .errorCode(Constants.ResponseStatus.SUCCESS_ERROR_CODE)
                .errorDescription(Constants.ResponseStatus.SUCCESS_ERROR_DESCRIPTION)
                .status(Constants.ResponseStatus.SUCCESS_STATUS)
                .requestPath("")
                .build();
    }

    public static Response createErrorResponse(RuntimeException e,String requestPath){
        return Response.builder()
                .errorCode(Constants.ResponseStatus.FAIL_ERROR_CODE)
                .errorDescription(e.getMessage())
                .status(Constants.ResponseStatus.FAIL_STATUS)
                .requestPath(requestPath)
                .build();
    }
}
