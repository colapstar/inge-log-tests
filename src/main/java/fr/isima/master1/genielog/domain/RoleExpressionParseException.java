package fr.isima.master1.genielog.domain;

public class RoleExpressionParseException
        extends RuntimeException {


    public RoleExpressionParseException(String message) {
        super(message);
    }

    public RoleExpressionParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
