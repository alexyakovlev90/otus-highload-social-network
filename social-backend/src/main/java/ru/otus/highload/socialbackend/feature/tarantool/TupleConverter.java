package ru.otus.highload.socialbackend.feature.tarantool;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TupleConverter {

    public List<Object> toTuple(UserTarantoolDto user) {
        return Arrays.asList(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName());
    }

    public List<UserTarantoolDto> fromTuples(List<List<?>> users) {
        return users.stream()
                .map(this::fromTuple)
                .collect(Collectors.toList());
    }

    public UserTarantoolDto fromTuple(List<?> user) {
        int size = user.size();

        return new UserTarantoolDto()
                .setId(size > 0 ? Long.parseLong(user.get(0).toString()) : null)
                .setLogin(size > 1 ? user.get(1).toString() : null)
                .setFirstName(size > 2 ? user.get(2).toString() : null)
                .setLastName(size > 3 ? user.get(3).toString() : null);


    }
}
