package com.matheustorres.dscatalog.controllers.exceptions;

public record FieldMessage(
        String fieldName,
        String message) {

}
