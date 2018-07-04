package com.cmb.lf65.ams.rest.api;

import com.cmb.lf65.ams.application.service.Validation;
import com.cmb.lf65.ams.domain.Error;
import com.cmb.lf65.ams.domain.user.User;
import com.cmb.lf65.ams.domain.user.UserRepository;
import com.cmb.lf65.ams.infrastructure.Util;
import com.cmb.lf65.ams.rest.AmsPageResource;
import com.cmb.lf65.ams.rest.AmsResource;
import com.cmb.lf65.ams.rest.ErrorCode;
import com.cmb.lf65.ams.rest.exceptions.BadRequestException;
import com.cmb.lf65.ams.rest.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.cmb.lf65.ams.application.service.Converter.toDomain;
import static com.cmb.lf65.ams.domain.Error.fromErrorCode;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UsersApi {

    private final UserRepository repository;

    @Autowired
    public UsersApi(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createUser(@RequestBody Map<String, Object> userMap) {

        final List<Error> errors = Validation.validate(userMap,
                Validation.required("name", "sex", "email"),
                Validation.min("age", 0));

        if (Util.notEmpty(errors)) {
            throw new BadRequestException(errors);
        }

        final User user = toDomain(userMap, User.class);
        final User saved = repository.save(user);
        final URI link = linkTo(UsersApi.class).slash(saved.getId()).toUri();

        return ResponseEntity.created(link).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity listUsers(Pageable pageable) {
        Page<User> users = repository.findAll(pageable);

        for (final User user : users.getContent()) {
            Link selfLink = linkTo(methodOn(UsersApi.class).showUser(user.getId())).withSelfRel();
            user.add(selfLink);
        }

        AmsPageResource<User> page = new AmsPageResource<>(users, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity showUser(@PathVariable("id") Long id) {

        Link link = linkTo(methodOn(UsersApi.class).showUser(id)).withSelfRel();

        return repository.findById(id)
                .map(u -> ResponseEntity.ok(new AmsResource<>(u.add(link))))
                .orElseThrow(() -> new NotFoundException(fromErrorCode(ErrorCode.RESOURCE_NOT_EXIST, "用户")));
    }

}
