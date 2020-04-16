package ru.otus.highload.socialbackend.feature.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.highload.socialbackend.domain.User;

@Component
public class UserInfoItemDtoConverter implements Converter<User, UserInfoItemDto> {

    @Override
    public UserInfoItemDto convert(User user) {
        return new UserInfoItemDto()
                .setId(user.getId())
                .setLogin(user.getLogin())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAge(user.getAge())
                .setSex(user.getSex())
                .setInterest(user.getInterest())
                .setCity(user.getCity())
                .setRegisterDate(user.getRegisterDate());
    }
}
