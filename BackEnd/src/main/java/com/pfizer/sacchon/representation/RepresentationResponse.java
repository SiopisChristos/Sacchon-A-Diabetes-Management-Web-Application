package com.pfizer.sacchon.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RepresentationResponse<T> {
    private int status;
    private String description;x
    private T data;
}
