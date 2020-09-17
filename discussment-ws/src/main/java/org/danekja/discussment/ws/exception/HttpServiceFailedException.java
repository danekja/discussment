package org.danekja.discussment.ws.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when remote Discussment webservice responds with server error.
 */
public class HttpServiceFailedException extends HttpServiceException {
    private final HttpStatus statusCode;
    private final String statusText;

    public HttpServiceFailedException(HttpStatus statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    @Override
    public String toString() {
        return "ServiceFailedException{" +
                "statusCode=" + statusCode +
                ", statusText='" + statusText + '\'' +
                '}';
    }
}
