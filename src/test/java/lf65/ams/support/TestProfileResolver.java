package lf65.ams.support;

import org.springframework.test.context.ActiveProfilesResolver;

public class TestProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> testClass) {
        final String activeProfiles = System.getProperty("spring.profiles.active");
        return new String[]{activeProfiles != null ? activeProfiles : "dev"};
    }
}
