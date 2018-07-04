package com.cmb.lf65.ams.application.service;

import com.cmb.lf65.ams.domain.Error;

import java.util.*;

import static com.cmb.lf65.ams.domain.Error.fromErrorCode;
import static com.cmb.lf65.ams.rest.ErrorCode.FIELD_VALUE_ERROR;
import static com.cmb.lf65.ams.rest.ErrorCode.NON_EXIST_FIELD;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public final class Validation {
    private Validation() {
    }

    public static List<Error> validate(final Map<String, Object> json, final Validator... validators) {
        return Arrays
                .stream(validators)
                .map(validator -> validator.validate(json))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    public static Validator required(final String... fields) {
        return json -> Arrays.stream(fields)
                .map(f -> json.containsKey(f) ? Collections.<Error>emptyList() : singletonList((fromErrorCode(NON_EXIST_FIELD, f))))
                .flatMap(Collection::stream)
                .collect(toList());
    }

    public static Validator min(final String field, final int minValue) {
        return json -> {
            if (!json.containsKey(field)) {
                return singletonList(fromErrorCode(NON_EXIST_FIELD, field));
            }
            final Integer value = Double.valueOf(json.get(field).toString()).intValue();
            return value > minValue ? emptyList() : singletonList(fromErrorCode(FIELD_VALUE_ERROR, field, value.toString()));
        };
    }

}
