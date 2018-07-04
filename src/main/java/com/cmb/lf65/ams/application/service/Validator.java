package com.cmb.lf65.ams.application.service;

import com.cmb.lf65.ams.domain.Error;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface Validator {
    List<Error> validate(Map<String, Object> json);

}
