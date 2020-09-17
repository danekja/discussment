package org.danekja.discussment.ws.exception;

/**
 * Thrown when remote Discussment webservice is unavailable at the moment.
 */
public class HttpServiceUnavailableException extends HttpServiceException {
    public HttpServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
