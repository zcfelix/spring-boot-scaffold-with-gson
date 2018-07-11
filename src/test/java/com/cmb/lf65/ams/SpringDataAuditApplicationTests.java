package com.cmb.lf65.ams;

import com.cmb.lf65.ams.domain.user.User;
import com.cmb.lf65.ams.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static com.cmb.lf65.ams.domain.user.Sex.FEMALE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataAuditApplicationTests {

    @Autowired
    private UserRepository repository;

    private User user;

    @Before
    public void setUp() {
        user = repository.save(new User("k", 33, FEMALE, "k@tw.com"));

        assertThat(user.getCreatedAt(), is(notNullValue()));
        assertThat(user.getCreatedBy(), is(notNullValue()));
        assertThat(user.getUpdatedAt(), is(notNullValue()));
        assertThat(user.getUpdatedBy(), is(notNullValue()));
    }

    @Test
    public void should_update_user_correctly() throws InterruptedException {
        final Date originalCreatedAt = user.getCreatedAt();
        final Date originalUpdatedAt = user.getUpdatedAt();

        Thread.sleep(1000);
        repository.save(user.setName("newName"));

        repository.findById(1L).ifPresent(updatedUser -> {
            assertThat(updatedUser.getName(), is("newName"));
            assertThat(updatedUser.getCreatedAt().compareTo(originalCreatedAt), is(0));
            assertThat(updatedUser.getUpdatedAt().compareTo(originalUpdatedAt), is(1));
        });

    }

    @After
    public void tearDown() {
        repository.delete(user);
    }
}