package com.cmb.lf65.ams.rest.exceptions;

import com.cmb.lf65.ams.domain.Error;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends AmsInternalException {

    public BadRequestException(List<Error> errors) {
        super(errors);
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
