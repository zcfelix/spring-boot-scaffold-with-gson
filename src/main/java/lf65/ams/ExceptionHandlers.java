package lf65.ams;

import com.google.gson.JsonSyntaxException;
import lf65.ams.domain.Error;
import lf65.ams.rest.ErrorCode;
import lf65.ams.rest.exceptions.AmsInternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.Collections.singletonList;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = AmsInternalException.class)
    public ResponseEntity shopifyInternalExceptionHandler(AmsInternalException e) {
        return ResponseEntity.status(e.httpStatus()).body(e.getErrors());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableExceptionHandler() {
        final Error error = Error.fromErrorCode(ErrorCode.UNREADABLE_REQUEST);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(singletonList(error));
    }

    @ExceptionHandler(value = JsonSyntaxException.class)
    public ResponseEntity jsonSyntaxExceptionHandle(JsonSyntaxException e) {
        e.getCause();
        final Error error = Error.fromErrorCode(ErrorCode.JSON_SYNTAX_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(singletonList(error));
    }

}
