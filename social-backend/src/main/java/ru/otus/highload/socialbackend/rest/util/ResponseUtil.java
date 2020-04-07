package ru.otus.highload.socialbackend.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public class ResponseUtil {

    public static <T> ResponseEntity<T> wrapOrNotFound(Optional<T> optional) {
        return optional.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
