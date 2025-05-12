package com.aesopwow.subsubclipclop.global.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
}