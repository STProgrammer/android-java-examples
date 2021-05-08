package com.wfamedia.worker_treads_threadpool1;

/**
 * Abstrakt klasse med to indre statiske klasser;
 * Success og Error
 * Brukes til å representere svar fra server, eller ev. feilmelding.
 */
public abstract class ServerResult<T> {
    private ServerResult() {
    }

    public static final class Success<T> extends ServerResult<T> {
        public T response;

        public Success(T data) {
            this.response = data;
        }

        public T getAlbumsResponse() {
            return response;
        }
    }

    public static final class Error<T> extends ServerResult<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }
    }
}
