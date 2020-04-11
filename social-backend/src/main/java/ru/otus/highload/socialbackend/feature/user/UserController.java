package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.domain.User;

import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws URISyntaxException {
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
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws URISyntaxException {
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
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return null;
    }
}
