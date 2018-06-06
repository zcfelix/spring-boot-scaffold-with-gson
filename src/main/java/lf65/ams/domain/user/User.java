package lf65.ams.domain.user;

import lf65.ams.domain.AmsResourceSupport;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends AmsResourceSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String email;

    @SuppressWarnings("unused")
    private User() {
    }

    public User(String name, Integer age, Sex sex, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}
