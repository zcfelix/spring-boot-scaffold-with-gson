package lf65.ams.application.service;

import lf65.ams.domain.Error;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface Validator {
    List<Error> validate(Map<String, Object> json);

}
