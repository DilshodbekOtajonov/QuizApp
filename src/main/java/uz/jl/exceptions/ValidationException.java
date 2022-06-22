package uz.jl.exceptions;

import java.util.Date;
import java.sql.Timestamp;

/**
 * @author "Otajonov Dilshodbek
 * @since 6/22/22 7:31 AM (Wednesday)
 * jira/IntelliJ IDEA
 */
public class ValidationException extends RuntimeException {
    private Timestamp timestamp;

    public ValidationException(String message) {
        super(message);
        this.timestamp = new Timestamp(new Date().getTime());
    }
}
