package com.SpringProject.Blogging.Application.Exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Long fieldValueLong;
    private String fieldValueStr;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValueLong) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValueLong));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValueLong = fieldValueLong;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValueStr) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValueStr));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValueStr = fieldValueStr;
    }
}

