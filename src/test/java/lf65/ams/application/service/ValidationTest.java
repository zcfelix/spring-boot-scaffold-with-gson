package lf65.ams.application.service;

import lf65.ams.domain.Error;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lf65.ams.application.service.Validation.min;
import static lf65.ams.application.service.Validation.required;
import static lf65.ams.domain.user.Sex.MALE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidationTest {

    private Map<String, Object> json = new HashMap<>();

    @Before
    public void setUp() {
        json.put("name", "felix");
        json.put("age", -1);
        json.put("sex", MALE);
        json.put("email", "felix@cmb.com");
    }

    @Test
    public void should_return_error_list_when_required_field_missed() {
        final List<Error> errors = Validation.validate(json, required("required"));
        assertThat(errors.size(), is(1));
        assertThat(errors.get(0).getStatus(), is("400"));
        assertThat(errors.get(0).getTitle(), is("必选字段required不存在"));
    }

    @Test
    public void should_return_error_list_when_multiple_required_fields_missed() {
        final List<Error> errors = Validation.validate(json, required("required1", "required"));
        assertThat(errors.size(), is(2));
        assertThat(errors.get(0).getStatus(), is("400"));
        assertThat(errors.get(1).getStatus(), is("400"));
    }

    @Test
    public void should_return_error_list_when_age_is_invalid() {
        final List<Error> errors = Validation.validate(json, min("age", 0));
        assertThat(errors.size(), is(1));
        assertThat(errors.get(0).getStatus(), is("400"));
        assertThat(errors.get(0).getTitle(), is("字段age的取值-1不合法"));
    }
}