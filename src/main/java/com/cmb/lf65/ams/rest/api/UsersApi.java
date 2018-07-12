package com.cmb.lf65.ams.rest.api;

import com.cmb.lf65.ams.domain.Error;
import com.cmb.lf65.ams.domain.user.User;
import com.cmb.lf65.ams.domain.user.UserRepository;
import com.cmb.lf65.ams.infrastructure.Util;
import com.cmb.lf65.ams.rest.AmsPageResource;
import com.cmb.lf65.ams.rest.AmsResource;
import com.cmb.lf65.ams.rest.AmsResources;
import com.cmb.lf65.ams.rest.ErrorCode;
import com.cmb.lf65.ams.rest.exceptions.BadRequestException;
import com.cmb.lf65.ams.rest.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.cmb.lf65.ams.application.service.Converter.toDomain;
import static com.cmb.lf65.ams.application.service.Validation.*;
import static com.cmb.lf65.ams.domain.Error.fromErrorCode;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
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

        final List<Error> errors = validate(userMap,
                required("name", "sex", "email", "age"),
                min("age", 0));

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

        AmsPageResource<User> page = new AmsPageResource<>(users, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/all/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity listAllUsers() {
        final List<User> all = repository.findAll();
        final List<AmsResource<User>> resourceList = all.stream().map(u -> {
            final Link link = linkTo(methodOn(UsersApi.class).showUser(u.getId())).withSelfRel();
            return new AmsResource<>(u, link);
        }).collect(toList());

        final AmsResources<User> resources = new AmsResources<>(resourceList, asList(linkTo(methodOn(UsersApi.class).listAllUsers()).withSelfRel()));

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity showUser(@PathVariable("id") Long id) {

        Link link = linkTo(methodOn(UsersApi.class).showUser(id)).withSelfRel();

        return repository.findById(id)
                .map(u -> ResponseEntity.ok(new AmsResource<>(u, link)))
                .orElseThrow(() -> new NotFoundException(fromErrorCode(ErrorCode.RESOURCE_NOT_EXIST, "用户")));
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity updateUser(@PathVariable("id") Long id,
                                     @RequestBody Map<String, Object> updateMap) {
        final List<Error> errors = validate(updateMap, min("age", 0));
        if (!CollectionUtils.isEmpty(errors)) {
            throw new BadRequestException(errors);
        }

        return repository.findById(id)
                .map(u -> {
                    updateMap.forEach((k, v) -> {
                        final Field field = ReflectionUtils.findField(User.class, k);
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, u, v);
                    });
                    repository.save(u);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new NotFoundException(fromErrorCode(ErrorCode.RESOURCE_NOT_EXIST, "用户")));
    }
}
