package lf65.ams.rest.api;

import lf65.ams.domain.Error;
import lf65.ams.domain.user.User;
import lf65.ams.domain.user.UserRepository;
import lf65.ams.rest.AmsResource;
import lf65.ams.rest.AmsResources;
import lf65.ams.rest.exceptions.BadRequestException;
import lf65.ams.rest.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static lf65.ams.application.service.Converter.toDomain;
import static lf65.ams.application.service.Validation.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UsersApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersApi.class);

    @Autowired
    private UserRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createUser(@RequestBody Map<String, Object> userMap) {

        final List<Error> errors = validate(userMap,
                required("name", "sex", "email"),
                min("age", 0));

        if (errors.size() != 0) {
            throw new BadRequestException(errors);
        }

        final User user = toDomain(userMap, User.class);
        final User saved = repository.save(user);
        final URI link = linkTo(UsersApi.class).slash(saved.getId()).toUri();

        return ResponseEntity.created(link).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity listUsers() {
        final List<User> all = repository.findAll();

        for (final User user : all) {
            Link selfLink = linkTo(methodOn(UsersApi.class).showUser(user.getId())).withSelfRel();
            user.add(selfLink);
        }

        Link link = linkTo(methodOn(UsersApi.class).listUsers()).withSelfRel();
        AmsResources<User> result = new AmsResources<>(all, link);

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity showUser(@PathVariable("id") Long id) {

        Link link = linkTo(methodOn(UsersApi.class).showUser(id)).withSelfRel();

        return repository.findById(id)
                .map(u -> ResponseEntity.ok(new AmsResource<>(u.add(link))))
                .orElseThrow(() -> new NotFoundException(Error.builder().withStatus("404").withTitle("用户不存在").build()));
    }

}
