package ru.otus.highload.socialbackend.feature.clickhouse;

import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClickHouseServer {

    private final KittenhouseApiClient kittenhouseApiClient;

    public void insertUser(User user) {
        UserClickHouseDto userClickHouseDto = getUserClickHouseDto(user);

        String table = "user(id,login,first_name,last_name,age,sex)";
        kittenhouseApiClient.insert(userClickHouseDto.toString(), table);
    }

    private UserClickHouseDto getUserClickHouseDto(User user) {
        return new UserClickHouseDto()
                .setId(user.getId())
                .setLogin(user.getLogin())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge())
                .setSex(user.getSex());
    }

    public void insertMany(List<User> users) {
        String usersStr = users.stream()
                .map(this::getUserClickHouseDto)
                .map(UserClickHouseDto::toString)
                .collect(Collectors.joining(","));
        String table = "user(id,login,first_name,last_name,age,sex)";
        kittenhouseApiClient.insert(usersStr, table);
    }

}
