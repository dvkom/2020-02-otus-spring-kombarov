package ru.dvkombarov.app.exceptions;

/**
 * Ошибка в операции ДАО
 */
public class DaoOperationException extends RuntimeException {
    public DaoOperationException() {
    }

    public DaoOperationException(String message) {
        super(message);
    }

    public DaoOperationException(Throwable cause) {
        super(cause);
    }
}
