package ru.otus.highload.socialbackend.feature.tarantool;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.tarantool.Iterator;
import org.tarantool.TarantoolClient;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.feature.user.UserInfoItemDto;
import ru.otus.highload.socialbackend.feature.user.UserService;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarantoolService {

    private static final String SPACE_NAME = "user_space";

    private final TarantoolClient tarantoolClient;
    private final TupleConverter tupleConverter;
    private final DriverManagerDataSource driverManagerDataSource;
    private final UserToUserTarantoolConverter userToUserTarantoolConverter;

    private NamedParameterJdbcTemplate template;

    @PostConstruct
    public void init() {
        template = new NamedParameterJdbcTemplate(driverManagerDataSource);

    }

    public Optional<UserTarantoolDto> findUserById(Long id) {
        List<?> user = tarantoolClient.syncOps().select(SPACE_NAME, "primary", Collections.singletonList(id), 0, 1, Iterator.EQ);
        return user.stream()
                .map(q -> (List<?>) q)
                .map(tupleConverter::fromTuple)
                .findFirst();
    }

    public List<UserTarantoolDto> searchUser(String prefix) {
        String prefixName = UserService.capitalize(prefix);
        List<?> user = tarantoolClient.syncOps().call("search", prefixName, 100);
        if (user.isEmpty()) {
            return Collections.emptyList();
        }
        List<?> users = (List<?>) user.get(0);
        return users.stream()
                .map(q -> (List<?>) q)
                .map(tupleConverter::fromTuple)
                .collect(Collectors.toList());
    }

    public List<?> insertOne(User user) {
        UserTarantoolDto userTarantoolDto = userToUserTarantoolConverter.convert(user);
        List<Object> tuple = tupleConverter.toTuple(userTarantoolDto);
        return tarantoolClient.syncOps().insert(SPACE_NAME, tuple);
    }

    public List<?> insertMany(List<User> users) {
        return users.stream()
                .map(userToUserTarantoolConverter::convert)
                .map(tupleConverter::toTuple)
                .map(tuple -> tarantoolClient.syncOps().insert(SPACE_NAME, tuple))
                .collect(Collectors.toList());
    }

//    public List<UserInfoItemDto> searchUser(String firstName, String lastName) {
////        String lastNameLike;
////        if (lastName == null) {
////            lastNameLike = "%";
////        } else {
////            lastNameLike = capitalize(lastName) + "$";
////        }
////        String firstNameLike = capitalize(firstName) +  "%";
////        return userSlaveRepository.getByFirstNameAndLastName(firstNameLike, lastNameLike).stream()
////                .map(userInfoItemDtoConverter::convert)
////                .collect(Collectors.toList());
////    }


}
