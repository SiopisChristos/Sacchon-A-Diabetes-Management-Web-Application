package com.pfizer.sacchon.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RepresentationResponse<T> {
    private int status;
    private String description;
    private T data;
}