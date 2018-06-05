package lf65.ams.rest.exceptions;

import lf65.ams.domain.Error;
import org.springframework.http.HttpStatus;

import static java.util.Arrays.asList;

public class NotFoundException extends AmsInternalException {

    public NotFoundException(Error... errors) {
        super(asList(errors));
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
