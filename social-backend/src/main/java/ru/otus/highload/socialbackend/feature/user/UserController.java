package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.rest.response.ListResponse;
import ru.otus.highload.socialbackend.rest.response.Response;

import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User userDto) throws URISyntaxException {
//        log.debug("REST request to save Factory : {}", factoryDTO);
//        if (factoryDTO.getId() != null) {
//            throw new BadRequestAlertException("A new factory cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        FactoryDTO result = factoryService.save(factoryDTO);
//        return ResponseEntity.created(new URI("/api/factories/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
        return null;
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User userDto) throws URISyntaxException {
//        log.debug("REST request to update Factory : {}", factoryDTO);
//        if (factoryDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        FactoryDTO result = factoryService.save(factoryDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, factoryDTO.getId().toString()))
//            .body(result);
        return null;
    }

    @GetMapping
    public ListResponse<User> getAllUsers() {
        return new ListResponse<>(userService.getAll());
    }

    @GetMapping("/{id}")
    public Response<User> getUser(@PathVariable Long id) {
        return new Response<>(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return null;
    }
}
