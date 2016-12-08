package ru.kpfu.itis.exception;

/**
 * Created by Daniel Shchepetov on 08.12.2016.
 */
public class ServerException extends Exception {

    private int statusCode = 200;

    public ServerException() {
        super();
    }

    public ServerException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServerException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

}