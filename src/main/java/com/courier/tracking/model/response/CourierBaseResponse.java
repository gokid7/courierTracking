package com.courier.tracking.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierBaseResponse<T> {

    @JsonProperty("response")
    private Response response;
    @JsonProperty("result")
    private T result;
    @JsonProperty("success")
    private boolean success;
}
