package lf65.ams.rest.exceptions;

import lf65.ams.domain.Error;
import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class AmsInternalException extends RuntimeException {
    private List<Error> errors;

    public AmsInternalException(List<Error> errors) {
        super();
        this.errors = errors;
    }

    public abstract HttpStatus httpStatus();

    public boolean is4xxFailure() {
        return httpStatus().is4xxClientError();
    }

    public boolean is5xxFailure() {
        return httpStatus().is5xxServerError();
    }

    public List<Error> getErrors() {
        return errors;
    }
}
