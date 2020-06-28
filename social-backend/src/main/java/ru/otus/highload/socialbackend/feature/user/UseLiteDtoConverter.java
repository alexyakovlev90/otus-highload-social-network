package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.highload.socialbackend.repository.master.UserMasterRepository;

@Component
@RequiredArgsConstructor
public class UseLiteDtoConverter implements Converter<Long, UserLiteDto> {

    private final UserMasterRepository userMasterRepository;

    @Override
    public UserLiteDto convert(Long userId) {
        return userMasterRepository.findById(userId)
                .map(user -> new UserLiteDto()
                        .setId(userId)
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setLogin(user.getLogin()))
                .orElse(null);
    }
}
