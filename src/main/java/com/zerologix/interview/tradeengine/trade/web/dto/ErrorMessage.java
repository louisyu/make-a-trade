package com.zerologix.interview.tradeengine.trade.web.dto;

/**
 * ErrorMessage represents a response of all API when error occurs.
 */
public class ErrorMessage {
    private final String errorMessage;

    public ErrorMessage() {
        errorMessage = "Internal Server Error.";
    }

    public ErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
