package com.cmb.lf65.ams.application.service;

import com.cmb.lf65.ams.domain.Error;
import com.cmb.lf65.ams.domain.user.Sex;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidationTest {

    private Map<String, Object> json = new HashMap<>();

    @Before
    public void setUp() {
        json.put("name", "felix");
        json.put("age", -1);
        json.put("sex", Sex.MALE);
        json.put("email", "felix@cmb.com");
    }

    @Test
    public void should_return_error_list_when_required_field_missed() {
        final List<Error> errors = Validation.validate(json, Validation.required("required"));
        assertThat(errors.size(), is(1));
        assertThat(errors.get(0).getStatus(), is("400"));
        assertThat(errors.get(0).getTitle(), is("必选字段required不存在"));
    }

    @Test
    public void should_return_error_list_when_multiple_required_fields_missed() {
        final List<Error> errors = Validation.validate(json, Validation.required("required1", "required"));
        assertThat(errors.size(), is(2));
        assertThat(errors.get(0).getStatus(), is("400"));
        assertThat(errors.get(1).getStatus(), is("400"));
    }

    @Test
    public void should_return_error_list_when_age_is_invalid() {
        final List<Error> errors = Validation.validate(json, Validation.min("age", 0));
        assertThat(errors.size(), is(1));
        assertThat(errors.get(0).getStatus(), is("400"));
        assertThat(errors.get(0).getTitle(), is("字段age的取值-1不合法"));
    }
}